package com.wode.api.web.controller;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.api.util.IPUtils;
import com.wode.api.util.LoginUserManage;
import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.ExchangeOrders;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Payment;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserTicketHis;
import com.wode.factory.model.UserWeixin;
import com.wode.factory.outside.service.AlipayService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxPayService;
import com.wode.factory.user.facade.OrdersFacade;
import com.wode.factory.user.model.BargainFlowVo;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.service.BargainFlowVoService;
import com.wode.factory.user.service.EntParamCodeService;
import com.wode.factory.user.service.ExchangeOrdersService;
import com.wode.factory.user.service.ExchangeSuborderService;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.PaymentService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserExchangeTicketService;
import com.wode.factory.user.service.UserTicketHisService;
import com.wode.factory.user.service.UserWeixinService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.vo.SubOrderVo;

@Controller
@RequestMapping("/pay")
@SuppressWarnings("unchecked")
public class PaymentController extends BaseController{

	@Autowired
	private OrdersFacade ordersFacade;
	@Autowired
	private SuborderService suborderService;
	@Autowired
	private OrdersService orderService;
	@Autowired
	private UserBalanceService userBalanceService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private UserWeixinService userWeixinService;
	@Autowired
	private BargainFlowVoService bargainFlowVoService;
	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
	private UserTicketHisService userTicketHisService;
	@Autowired
	private UserExchangeTicketService userExchangeTicketService;
	@Autowired
	private ExchangeOrdersService exchangeOrdersService;
	@Autowired
	private ExchangeSuborderService exchangeSuborderService;
	@Autowired
	private DBUtils dbUtils;
	@Autowired
	private RedisUtil redisUtil;
	
	//服务器异步通知页面路径
	public final static String notifyUrl=Constant.ALIPAY_NOTIFY_URL;
	public final static String notifyUrl1=notifyUrl+"1";
	
	static WxPayService wxPay = ServiceFactory.getWxPayService(Constant.OUTSIDE_SERVICE_URL);
	static AlipayService alipay = ServiceFactory.getAlipayService(Constant.OUTSIDE_SERVICE_URL);
	
	/**
	 * 订单支付准备接口	 
	 * @param orderId
	 * @param subOrderId
	 * @return
	 */
	@RequestMapping(value = { "/ready.user" })
	@ResponseBody
	public ActResult<Object> creat(String type,Long orderId,String subOrderId,String way,String version,
			String wxOpen,String openId,BigDecimal amount, HttpServletRequest request) {
		
		if(!"1".equals(wxOpen)){
			wxOpen="app";
		}
		UserFactory userFactory = LoginUserManage.getUser();
		if(!"wxpay".equals(way)) {
			way="zhifubao";
		}
		Payment payment = new Payment();
		payment.setCreateTime(new Date());
		payment.setOutTradeNo(dbUtils.CreateID() + "");
		payment.setStatus(0);
		payment.setWay(way);
		String productName = "";
		if("wxpay".equals(way)){
			// 设置公众账号ID/应用APPID
			payment.setAppId(wxPay.getAppId(wxOpen)); 
		} else if("zhifubao".equals(way)) {
			// 设置公众账号ID/应用APPID
			if("2".equals(version)) {
				payment.setAppId(AlipayService.APP_ID_NEW); 
			} else {
				payment.setAppId(AlipayService.APP_ID_OLD); 
			}
		}
		boolean isOrder = true;
		if("1".equals(type) || "4".equals(type)) {
			//type=1,4 为团购订单
			if (!StringUtils.isEmpty(subOrderId)) {
				//团购支付
				GroupSuborder groupSuborder = this.suborderService.findGroupSuborderObjbyId(subOrderId);
				if(0 != groupSuborder.getStatus()){
					return ActResult.fail("团订单状态不正确，请刷新后重试"); 
				}
				productName = groupSuborder.getProductName();
				payment.setSubOrderId(subOrderId);
				payment.setTotalFee(groupSuborder.getCashPay()==null?groupSuborder.getRealPrice():groupSuborder.getRealPrice().subtract(groupSuborder.getCashPay()));
				payment.setPayType(4);		//4:团购订单支付
				payment.setOrderType(Integer.parseInt(type));
			} else if(!StringUtils.isEmpty(orderId)) {
				// 订单支付
				boolean needPay = false;
				if(StringUtils.isEmpty(orderId)){
					return ActResult.fail("参数错误，订单不存在");
				}
				GroupOrders groupOrders = orderService.findGroupOrdersById(orderId);
				if(groupOrders==null) return ActResult.fail("参数错误，订单不存在");
				List<GroupSuborder> GroupSuborderList = suborderService.findGroupSuborderbyId(orderId);
				productName = GroupSuborderList.get(0).getProductName();
				BigDecimal cashPay = BigDecimal.ZERO;
				for (GroupSuborder groupSuborder : GroupSuborderList) {
					if(groupSuborder.getCashPay() != null){
						cashPay = cashPay.add(groupSuborder.getCashPay());
					}
					if(groupSuborder.getStatus() == 0){
						needPay=true;
					}
				}
				if(!needPay) {
					return ActResult.fail("没有需要支付订单，请刷新后重试");
				}
				payment.setTotalFee(groupOrders.getRealPrice().subtract(cashPay));
				payment.setPayType(4);		//4:团订单支付
				payment.setOrderType(Integer.parseInt(type));
				payment.setOrderId(orderId);
			} 
		} else if("5".equals(type)){
			// 换领单
			// 普通订单 及充值
			if (!StringUtils.isEmpty(subOrderId)) {

				ExchangeSuborder suborder = this.exchangeSuborderService.getById(subOrderId);
				if(suborder!=null && 0 != suborder.getStatus()){
					return ActResult.fail("订单状态不正确，请刷新后重试"); 
				}
				productName = suborder.getProductName();
				payment.setSubOrderId(subOrderId);
				payment.setTotalFee(suborder.getCashPay()==null?suborder.getRealPrice():suborder.getRealPrice().subtract(suborder.getCashPay()));
				payment.setPayType(5);		//1:订单支付
				payment.setOrderType(Integer.parseInt(type));
			} else if(!StringUtils.isEmpty(orderId)) {
				// 订单支付
				boolean needPay = false;
				if(StringUtils.isEmpty(orderId)){
					return ActResult.fail("参数错误，订单不存在");
				}

				ExchangeOrders or = exchangeOrdersService.getById(orderId);
				if(or==null){
					return ActResult.fail("参数错误，订单不存在");
				}
				ExchangeSuborder que = new ExchangeSuborder();
				que.setOrderId(orderId);
				List<ExchangeSuborder> subOrders = exchangeSuborderService.selectByModel(que);
				productName = subOrders.get(0).getProductName();
//				for (ExchangeSuborder exchangeSuborder : subOrders) {
//					exchangeSuborderService.selectItems4Set(exchangeSuborder);
//				}
				BigDecimal cashPay = BigDecimal.ZERO;
				for (ExchangeSuborder subOrderVo : subOrders) {
					if(subOrderVo.getCashPay() != null) {
						cashPay = cashPay.add(subOrderVo.getCashPay());
					}
					if(subOrderVo.getStatus()==0) {
						needPay=true;
					}
				}
				
				if(!needPay) {
					return ActResult.fail("没有需要支付订单，请刷新后重试");
				}
				payment.setTotalFee(or.getRealPrice().subtract(cashPay));
				payment.setPayType(5);		//1:订单支付
				payment.setOrderId(orderId);
				payment.setOrderType(Integer.parseInt(type));
			}
		} else {
			// 普通订单 及充值
			if (!StringUtils.isEmpty(subOrderId)) {
				// 子单支付
				SuborderQuery query = new SuborderQuery();
				query.setUserId(userFactory.getId());
				query.setSubOrderId(subOrderId);
				SubOrderVo suborder = this.suborderService.findOrderDetailById(query);
				if(0 != suborder.getStatus()){
					return ActResult.fail("订单状态不正确，请刷新后重试"); 
				}
				productName = suborder.getProductName();
				payment.setSubOrderId(subOrderId);
				payment.setTotalFee(suborder.getCashPay()==null?suborder.getRealPrice():suborder.getRealPrice().subtract(suborder.getCashPay()));
				payment.setPayType(1);		//1:订单支付
				payment.setOrderType(0);
			} else if(!StringUtils.isEmpty(orderId)) {
				// 订单支付
				boolean needPay = false;
				if(StringUtils.isEmpty(orderId)){
					return ActResult.fail("参数错误，订单不存在");
				}

				Orders or = orderService.findById(loginUser.getId(), orderId);
				if(or==null){
					return ActResult.fail("参数错误，订单不存在");
				}
				List<SubOrderVo> subOrders = suborderService.findSubOrdersByOrderId(userFactory.getId(), orderId);
				productName = subOrders.get(0).getProductName();
				BigDecimal cashPay = BigDecimal.ZERO;
				for (SubOrderVo subOrderVo : subOrders) {
					if(subOrderVo.getCashPay() != null) {
						cashPay = cashPay.add(subOrderVo.getCashPay());
					}
					if(subOrderVo.getStatus()==0) {
						needPay=true;
					}
				}
				
				if(!needPay) {
					return ActResult.fail("没有需要支付订单，请刷新后重试");
				}
				payment.setTotalFee(or.getRealPrice().subtract(cashPay));
				payment.setPayType(1);		//1:订单支付
				payment.setOrderId(orderId);
				payment.setOrderType(0);
			} else {
				// 充值
				if(amount == null || amount.compareTo(BigDecimal.ZERO)<=0) {
					return ActResult.fail("充值金额输入错误");
				}

				payment.setTotalFee(amount);
				payment.setOrderId(userFactory.getId());
				payment.setPayType(2);		//2:现金券充值
				payment.setOrderType(-1);
				payment.setExp1(userFactory.getNickName());
				isOrder = false;
			}
		}
		
		Payment result = paymentService.save(payment);
		
		if("wxpay".equals(way)){

			try {
				Random random = new Random();
				String nonceStr= MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
						.getBytes());

				//String orderIdP;
				if (payment.getOrderId() != null) {
					//orderIdP = payment.getOrderId().toString();
				} else if (!StringUtils.isEmpty(payment.getSubOrderId())) {
					//orderIdP = payment.getSubOrderId();
				} else {
					throw new Exception("微信支付预订单生成失败,订单号缺失!");
				}

				Map<String, String> responseMap = wxPay.unifiedOrder(IPUtils.getClientAddress(request), productName,
						payment.getOutTradeNo(), payment.getTotalFee(), nonceStr, wxOpen, changeOpenId(openId), isOrder,
						Constant.WXPAY_NOTIFY_URL);
				// 微信预订单号
				if("WEB".equals(wxOpen)){
					payment.setTradeNo(responseMap.get(WxPayService.WEB_PREPAY_ID));
				} else {
					payment.setTradeNo(responseMap.get(WxPayService.APP_OPEN_PREPAY_ID));
				}
				paymentService.update(payment);

				return ActResult.success(responseMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if("zhifubao".equals(way)){
			ActResult<Object> rtn = ActResult.success(result.getOutTradeNo());
			if("2".equals(version)) {
				rtn.setMsg(alipay.appPay(result.getOutTradeNo(), "我的福利订单", result.getTotalFee(), productName, notifyUrl));
				
			} else {
				rtn.setMsg(alipay.oldAppPay(result.getOutTradeNo(), result.getTotalFee(), notifyUrl1));
			}
			return rtn;
		}
		return ActResult.success(result.getOutTradeNo());
	}

	/**
	 * 订单支付准备接口	 
	 * @param orderId
	 * @param subOrderId
	 * @return
	 */
	@RequestMapping(value = { "/wxOpenPay.user" })
	@ResponseBody
	public ActResult<Object> wxPage(String type,Long orderId,String subOrderId,BigDecimal amount,HttpServletRequest request) {
		UserFactory userFactory = LoginUserManage.getUser();
		UserWeixin uw = userWeixinService.getOneModelByUserId(userFactory.getId());
		return this.creat(type,orderId, subOrderId, "wxpay", "WEB", "1",uw.getOpenId(), amount,request);
	}

	@RequestMapping(value = {"/page.user"})
    public ModelAndView page(HttpServletRequest request, ModelAndView model, Long orderId, String subOrderId, String orderType,BigDecimal totalFee,Long pruductId,String backNum,String type) {
		
		model.addObject("orderId", orderId);
		model.addObject("subOrderId", subOrderId);
		model.addObject("orderType",orderType);
		model.addObject("totalFee",totalFee);
		model.addObject("pruductId",pruductId);
		model.addObject("backNum",StringUtils.isEmpty(backNum) ? "0" : backNum);
		model.addObject("type",StringUtils.isEmpty(type) ? "0" : type);
		model.setViewName("pay");
		return model;
	}
	/**
	 * 使用平台余额支付
	 * @param userId
	 * @param orderId
	 * @param subOrderId
	 * @param totalFee
	 * @return
	 */
	@RequestMapping(value = { "/balanceToPay.user" })
	@ResponseBody
	public ActResult<Object> balanceToPay(String type,ModelMap model,Long orderId,String subOrderId,BigDecimal totalFee){
		Orders order = null;
		UserFactory userFactory = LoginUserManage.getUser();

		boolean isGroup = false;//是否团购订单
		if(userFactory != null) {
			Payment payment = new Payment();
			//查询平台余额
			UserBalance userBalance = userBalanceService.findMoneyByUser(userFactory.getId());
			int r=userBalance.getBalance().compareTo(totalFee);
			if(r >= 0 ){
				SubOrderVo suborder = null;
				if("1".equals(type) || "4".equals(type)) {
					//type=1,4 为团购订单
					isGroup =true;

					if (!StringUtils.isEmpty(subOrderId)) {
						
						GroupSuborder groupSuborder = this.suborderService.findGroupSuborderObjbyId(subOrderId);
						//判断是否是团购订单
						if(groupSuborder!=null&&groupSuborder.getStatus()!=0){
							return ActResult.fail("订单状态不正确，请刷新后重试"); 
						}
						payment.setSubOrderId(groupSuborder.getSubOrderId());
						payment.setTotalFee(groupSuborder.getCashPay()==null?groupSuborder.getRealPrice():groupSuborder.getRealPrice().subtract(groupSuborder.getCashPay()));

						orderId = groupSuborder.getOrderId();
					} else {

						boolean topay =false;
						if(StringUtils.isEmpty(orderId)){
							return ActResult.fail("参数错误，订单不存在");
						}
						GroupOrders groupOrders = orderService.findGroupOrdersById(orderId);
						payment.setOrderId(groupOrders.getOrderId());
						List<GroupSuborder> GroupSuborderList = suborderService.findGroupSuborderbyId(orderId);
						BigDecimal cashPay = BigDecimal.ZERO;
						for (GroupSuborder groupSuborder : GroupSuborderList) {
							if(groupSuborder.getCashPay() != null ) {
								cashPay = cashPay.add(groupSuborder.getCashPay());
							}
							if(0==groupSuborder.getStatus()) {
								topay=true;
							}
						}
						
						if(!topay){
							return ActResult.fail("订单状态不正确，请刷新后重试");
						}
						
						payment.setTotalFee(groupOrders.getRealPrice().subtract(cashPay));	
					}
					payment.setPayType(4);		//1:订单支付   4:团购订单支付
					payment.setOrderType(Integer.parseInt(type));
				} else if("5".equals(type)){
					// 换领订单
					if (!StringUtils.isEmpty(subOrderId)) {
						ExchangeSuborder esuborder = this.exchangeSuborderService.getById(subOrderId);
						if(null == esuborder){
							return ActResult.fail("参数错误，订单不存在"); 
						}
						if(0 != esuborder.getStatus()){
							return ActResult.fail("订单状态不正确，请刷新后重试"); 
						}
						payment.setSubOrderId(esuborder.getSubOrderId());
						payment.setTotalFee(esuborder.getCashPay()==null?esuborder.getRealPrice():esuborder.getRealPrice().subtract(esuborder.getCashPay()));

						orderId = esuborder.getOrderId();
					} else {

						boolean topay =false;
						if(StringUtils.isEmpty(orderId)){
							return ActResult.fail("参数错误，订单不存在");
						}

						ExchangeOrders eorder = exchangeOrdersService.getById(orderId);
						if(null == eorder){
							return ActResult.fail("参数错误，订单不存在");
						}
						payment.setOrderId(eorder.getOrderId());

						ExchangeSuborder que = new ExchangeSuborder();
						que.setOrderId(orderId);
						List<ExchangeSuborder> subOrders = exchangeSuborderService.selectByModel(que);
						BigDecimal cashPay = BigDecimal.ZERO;
						for (ExchangeSuborder subOrderVo : subOrders) {
							if(subOrderVo.getCashPay() != null) {
								cashPay = cashPay.add(subOrderVo.getCashPay());
							}
							if(0==subOrderVo.getStatus()) {
								topay=true;
							}
						}
						
						if(!topay){
							return ActResult.fail("订单状态不正确，请刷新后重试");
						}
						payment.setTotalFee(eorder.getRealPrice().subtract(cashPay));				
					}
					payment.setPayType(5);		//1:订单支付   4:团购订单支付
					payment.setOrderType(Integer.parseInt(type));
				} else {
					// 普通订单
					if (!StringUtils.isEmpty(subOrderId)) {
						SuborderQuery query = new SuborderQuery();
						query.setUserId(userFactory.getId());
						query.setSubOrderId(subOrderId);
						suborder = this.suborderService.findOrderDetailById(query);
						if(null == suborder){
							return ActResult.fail("参数错误，订单不存在"); 
						}
						if(0 != suborder.getStatus()){
							return ActResult.fail("订单状态不正确，请刷新后重试"); 
						}
						payment.setSubOrderId(suborder.getSubOrderId());
						payment.setTotalFee(suborder.getCashPay()==null?suborder.getRealPrice():suborder.getRealPrice().subtract(suborder.getCashPay()));

						orderId = suborder.getOrderId();
					} else {

						boolean topay =false;
						if(StringUtils.isEmpty(orderId)){
							return ActResult.fail("参数错误，订单不存在");
						}
						
						order = orderService.findById(userFactory.getId(), orderId);
						if(null == order){
							return ActResult.fail("参数错误，订单不存在");
						}
					
						payment.setOrderId(order.getOrderId());
						List<SubOrderVo> subOrders = suborderService.findSubOrdersByOrderId(userFactory.getId(), orderId);
						BigDecimal cashPay = BigDecimal.ZERO;
						for (SubOrderVo subOrderVo : subOrders) {
							if(subOrderVo.getCashPay() != null) {
								cashPay = cashPay.add(subOrderVo.getCashPay());
							}
							if(0==subOrderVo.getStatus()) {
								topay=true;
							}
						}
						
						if(!topay){
							return ActResult.fail("订单状态不正确，请刷新后重试");
						}

						payment.setTotalFee(order.getRealPrice().subtract(cashPay));				
					}
					payment.setPayType(1);		//1:订单支付   4:团购订单支付
					payment.setOrderType(0);
				}
				
				payment.setOutTradeNo(dbUtils.CreateID() + "");
				payment.setStatus(0);
				payment.setCreateTime(new Date());
				payment.setWay("pingtaiyue");
				paymentService.save(payment);
				
//				BigDecimal money = userBalance.getBalance().subtract(payment.getTotalFee());
//				userBalanceService.balanceToPay(money,user.getUserId());
//				payment.setStatus(1);
//				paymentService.saveOrUpdate(payment);
				
				//记录使用平台余额流水
				ActResult<Object> balanceFlowResult =orderService.balanceFlow(orderId,isGroup?null:subOrderId, payment.getTotalFee(),
						userFactory);

				if(!balanceFlowResult.isSuccess()){
					ordersFacade.cancelOrder(userFactory, orderId, balanceFlowResult.getMsg(),false); 
				} else {
					payment.setTradeNo(balanceFlowResult.getData().toString());
					payment.setStatus(2);//支付成功已回调
					ordersFacade.updateOrderToPay(payment);
					
					return ActResult.success("支付成功");
				}
			}
			return ActResult.fail("余额不足");
		}
		return ActResult.fail("用户未登录");
	}
	
	/**
	 * 订单支付成功调用接口
	 * @param model
	 * @param request
	 * @param outTreadId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = { "/success.user" })
	@ResponseBody
	public ActResult<String> paySuccess(HttpServletRequest request) {
		Map requestParams = request.getParameterMap();
		Map<String,String> params = new HashMap<String,String>();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		String out_trade_no = params.get("out_trade_no");
		Payment payment = paymentService.getById(Long.valueOf(out_trade_no));
		if(payment==null){
			return ActResult.fail("参数错误");
		}
		return paymentService.appPaySuccess(payment, params);
	}
	
	public static class MD5 {
		
		public final static String getMessageDigest(byte[] buffer) {
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			try {
				MessageDigest mdTemp = MessageDigest.getInstance("MD5");
				mdTemp.update(buffer);
				byte[] md = mdTemp.digest();
				int j = md.length;
				char str[] = new char[j * 2];
				int k = 0;
				for (int i = 0; i < j; i++) {
					byte byte0 = md[i];
					str[k++] = hexDigits[byte0 >>> 4 & 0xf];
					str[k++] = hexDigits[byte0 & 0xf];
				}
				return new String(str);
			} catch (Exception e) {
				return null;
			}
		}
	}

	@RequestMapping(value = { "/bargainFlow" })
    public ModelAndView page(Long empId,Long cId,String charge,ModelAndView model,HttpServletRequest request) {
		List<BargainFlowVo> list = null;
		if(cId==3){

			List<UserExchangeTicket> ls = userExchangeTicketService.selectExByUser(empId);
						
			Date now = new Date();
			for (UserExchangeTicket t : ls) {
				if(t.getLimitEnd().before(now)) {
					if(t.getStatus()<2) {
						t.setStatus(3);	// 标记已过期
					}
				}

				if (t.getTicketNote().length()>9) {
					String ticketNote = t.getTicketNote().substring(0, 7);
					ticketNote+="...";
					t.setTicketNote(ticketNote);
				}
			}
			model.addObject("empId", empId);	
			if(ls.isEmpty()) {
				model.setViewName("nobenefits3");
				return model;
			}
			model.addObject("info", ls);
			model.setViewName("exchangeTicket");
			return model;
		}else {
			list = getLogFromQiye(empId, cId);
		}
		model.addObject("localHtml", com.wode.api.util.Constant.pageFont);
		model.addObject("title", getTitle(cId));
		model.addObject("info", list);

		model.addObject("cId", cId);
		model.addObject("charge", charge);
		model.addObject("ub", userBalanceService.findByUserAndType(empId, cId));
		model.setViewName("benefits");
		return model;
	}
	
	/**
	 * 查询现金及内购券流水
	 * @param empId
	 * @param cId
	 * @return
	 */
	private List<BargainFlowVo> getLogFromQiye(Long empId, Long cId) {
		List<BargainFlowVo> list;
		PageInfo<BargainFlowVo> p=bargainFlowVoService.findByQuery(empId, cId, null, 1, 50);
		list = p.getList();
		//根据操作代码取得操作属性
		Map<String,EntParamCode> mapCode = entParamCodeService.getBenefitFlowCode();
		
		for (BargainFlowVo bargainFlowVo : list) {
			String code = bargainFlowVo.getOpCode();
			EntParamCode epc = mapCode.get(code);
			bargainFlowVo.setValue(getFlg(epc.getValue()));
			bargainFlowVo.setIconUrl("icon_" + code + ".png");
		}
		return list;
	}
	
	/**
	 * 查询换领币流水
	 * @param empId
	 * @param ticketId 券的id
	 * @return
	 */
	private List<BargainFlowVo> getPurchasedFlow(Long empId,Long ticketId) {
		List<BargainFlowVo> list = new ArrayList<BargainFlowVo>();
		PageInfo<UserTicketHis>  p =userTicketHisService.findPageListByUserid(empId,ticketId, 1, 50);
		//根据操作代码取得操作属性
		Map<String,EntParamCode> mapCode = entParamCodeService.getBenefitFlowCode();
		
		for (UserTicketHis uth : p.getList()) {
			BargainFlowVo vo = new BargainFlowVo();
			vo.setOpDate(uth.getOpDate());
			vo.setOpCode(uth.getOpCode()); 	
			vo.setNote(uth.getNote());
			EntParamCode epc = mapCode.get(uth.getOpCode());
			vo.setValue(getFlg(epc.getValue()));
			vo.setAmount(uth.getTicket());
			vo.setIconUrl("icon_"+uth.getOpCode()+".png");
			list.add(vo);
			
		}
		return list;
	}
	
	private String getTitle(Long cId) {
		String title = "现金明细";
		if(cId == 1){
			title="内购券明细";
		} else if(cId == 3){
			title="换领币明细";
		}
		return title;
	}

	private String getFlg(String value) {
		String flg = "-";
		if("1".equals(value)) {
			flg="+";
		}
		return flg;
	}

	/**
	 * 换领币流水
	 * @param empId
	 * @param cId
	 * @param ticketId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/exChangeTicketFlow" })
    public ModelAndView page(Long empId,Long cId,Long ticketId,ModelAndView model,HttpServletRequest request) {
		List<BargainFlowVo> list = null;
		list = getPurchasedFlow(empId,ticketId);
		UserExchangeTicket ut = userExchangeTicketService.getById(ticketId);
		if(ut==null) {
			model.setViewName("nobenefits3");
			return model;
		}
		if (ut.getLimitEnd().before(new Date())) {//过了有效期
			model.addObject("qId",2);
		}else{
			model.addObject("qId", 1);
		}
		
		model.addObject("title", getTitle(cId));
		model.addObject("info", list);
		model.addObject("ut", ut);
		model.addObject("cId", cId);
		model.setViewName("benefits");
		return model;
	}
	/**
	 * 微信支付成功页面
	 * @param uid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/pay_success.user")
	public ModelAndView page(String orderId,String suborderId,String fromWay,ModelAndView model,HttpServletRequest request,String type){
		model.addObject("orderId", orderId);
		/*if(!StringUtils.isNullOrEmpty(orderId)) {
			GroupOrders groupOrders = groupOrdersService.getById(Long.valueOf(orderId));
			if(null != groupOrders) {
			    model.addObject("groupOrder", groupOrders);
			    model.addObject("groupBuy", groupBuyService.getGroupBuyById(""+groupOrders.getGroupId()));
			}
		}*/
		model.addObject("suborderId", suborderId);
		model.addObject("fromWay", fromWay);
		model.addObject("type",StringUtils.isEmpty(type) ? "0" : type);
		/*if(type.equals("5")) {
			model.setViewName("exchangeOrder/success_match");
		}else {
			model.setViewName("pay_success");
		}*/
		model.setViewName("pay_success");
		return model;
	}
	
	/**
	 * 测试好无法进行支付测试，做线上转换
	 * @param openId
	 * @return
	 */
	private String changeOpenId(String openId) {
		if(!StringUtils.isEmpty(openId)) {
			System.out.println("-------------PaymentController.changeOpenId(WEIXIN_TEMP_OPENID openId:)----------"+openId);
			String online = redisUtil.getMapData("WEIXIN_TEMP_OPENID", openId);
			if(!StringUtils.isEmpty(online)) {
				return online;
			}
		}
		return openId;
	}
}
