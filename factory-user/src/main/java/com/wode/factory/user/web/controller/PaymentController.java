package com.wode.factory.user.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wode.common.db.DBUtils;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ExchangeOrders;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Payment;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.AlipayService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxPayService;
import com.wode.factory.user.facade.ExchangeOrdersFacade;
import com.wode.factory.user.facade.OrdersFacade;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.service.ExchangeOrdersService;
import com.wode.factory.user.service.ExchangeSuborderService;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.PaymentService;
import com.wode.factory.user.service.RecommendProductService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.IPUtils;
import com.wode.factory.user.vo.SubOrderVo;
import com.wode.factory.user.web.controller.unionpay.UnionPayConfig;

/**
 * 支付
 *
 * @author zhengxiongwu
 */
@Controller
@RequestMapping("/payment")
public class PaymentController extends BaseController {

	@Autowired
	private OrdersFacade ordersFacade;
	@Autowired
	private ExchangeOrdersFacade exchangeOrdersFacade;
	
	@Autowired
	private DBUtils dbUtils;

	@Qualifier("ordersService")
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private ExchangeOrdersService exchangeOrdersService;

	@Qualifier("suborderService")
	@Autowired
	private SuborderService suborderService;
	@Autowired
	private ExchangeSuborderService exchangeSuborderService;



	@Qualifier("paymentService")
	@Autowired
	private PaymentService paymentService;

	@Autowired
	UserBalanceService userBalanceService;

	@Qualifier("recommendProductService")
	@Autowired
	private RecommendProductService recommendService;

	static WxPayService wxPay = ServiceFactory.getWxPayService(Constant.OUTSIDE_SERVICE_URL);
	static AlipayService alipay = ServiceFactory.getAlipayService(Constant.OUTSIDE_SERVICE_URL);

	//服务器异步通知页面路径
	public final static String notifyUrl=Constant.ALIPAY_NOTIFY_URL;	
	public final static String notifyUrl1=notifyUrl + 1;	
	public final static String returnUrl=Constant.ALIPAY_RETURN_URL;	
	public final static String returnUrl1=returnUrl+1;	
	
	
	@RequestMapping(value = {"/pay"})
	public String pay(ModelMap model, HttpServletRequest request, HttpServletResponse response, Long orderId, String subOrderId,String payType) {
		Orders order = null;
		UserFactory user = getUser(request,response);
		//判断账户平台余额
		UserBalance userBalance = userBalanceService.findMoneyByUser(user.getId());
		int r = userBalance.getBalance().compareTo(BigDecimal.ZERO);
		if (r == 1) {
			model.addAttribute("userBalance", userBalance);
		}
		model.addAttribute("payType", payType);

		if("5".equals(payType)) {
			/////////////////////////////////////////////////////////////////////////////////////////////
			//  换领订单
			if (orderId != null) {
				ExchangeOrders eorder = exchangeOrdersService.getById(orderId);
				if (null == eorder || !eorder.getUserId().equals(user.getId())) {
					return "redirect:/index.html";
				} else {
					ExchangeSuborder que = new ExchangeSuborder();
					que.setOrderId(orderId);
					List<ExchangeSuborder> subOrders = exchangeSuborderService.selectByModel(que);
					for (ExchangeSuborder exchangeSuborder : subOrders) {
						exchangeSuborderService.selectItems4Set(exchangeSuborder);
					}
					model.addAttribute("order", eorder);
					model.addAttribute("realPrice", eorder.getRealPrice());
					model.addAttribute("subOrders", subOrders);
				}
			}
			if (subOrderId != null) {
				ExchangeSuborder subOrder = this.exchangeSuborderService.getById(subOrderId);
				if (null == subOrder) {
					return "redirect:/index.html";
				}
				exchangeSuborderService.selectItems4Set(subOrder);
				model.addAttribute("subOrder", subOrder);
			}
			/////////////////////////////////////////////////////////////////////////////////////////////
			
		} else {
			/////////////////////////////////////////////////////////////////////////////////////////////
			//  普通订单
			if (orderId != null) {
				order = ordersService.findById(user.getId(), orderId);
				if (null == order) {
					return "redirect:/index.html";
				}
				List<SubOrderVo> subOrders = suborderService.findSubOrdersByOrderId(user.getId(), orderId);
				BigDecimal cashPay = BigDecimal.ZERO;
				for (SubOrderVo subOrderVo : subOrders) {
					if(subOrderVo.getCashPay() != null) {
						cashPay = cashPay.add(subOrderVo.getCashPay());
					}
				}
				model.addAttribute("order", order);
				model.addAttribute("realPrice", order.getRealPrice().subtract(cashPay));
				model.addAttribute("subOrders", subOrders);
			}
			if (subOrderId != null) {
				SuborderQuery query = new SuborderQuery();
				query.setUserId(user.getId());
				query.setSubOrderId(subOrderId);
				SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);
				if (null == subOrder) {
					return "redirect:/index.html";
				}
				model.addAttribute("subOrder", subOrder);
			}
			/////////////////////////////////////////////////////////////////////////////////////////////
		}
		return "payment";
	}


	@SuppressWarnings("rawtypes")
	@RequestMapping(value = {"/toPay"})
	public String toPay(String type,ModelMap model, HttpServletRequest request, HttpServletResponse response, Long orderId, String subOrderId) {
		UserFactory user = getUser(request,response);
		String way = request.getParameter("zhifu");
		Payment payment = new Payment();
		String productName = "";
		if("5".equals(type)) {
			/////////////////////////////////////////////////////////////////////////////////////////////
			//  换领订单
			/////////////////////////////////////////////////////////////////////////////////////////////
			if (!StringUtils.isEmpty(subOrderId)) {
				ExchangeSuborder subOrder = this.exchangeSuborderService.getById(subOrderId);
				if(0 != subOrder.getStatus()){
					return "redirect:/index.html";
				}
				productName=subOrder.getProductName();
				payment.setSubOrderId(subOrder.getSubOrderId());
				payment.setTotalFee(subOrder.getCashPay()==null?subOrder.getRealPrice():subOrder.getRealPrice().subtract(subOrder.getCashPay()));
				payment.setPayType(5);		//5:团购订单支付
				payment.setOrderType(Integer.parseInt(type));
			} else if (orderId != null) {
				ExchangeOrders eorder = exchangeOrdersService.getById(orderId);
				payment.setOrderId(eorder.getOrderId());

				boolean needPay = false;
				ExchangeSuborder que = new ExchangeSuborder();
				que.setOrderId(orderId);
				List<ExchangeSuborder> subOrders = exchangeSuborderService.selectByModel(que);
				productName=subOrders.get(0).getProductName();
				BigDecimal cashPay = BigDecimal.ZERO;
				for (ExchangeSuborder groupSuborder : subOrders) {
					if(groupSuborder.getCashPay() != null){
						cashPay = cashPay.add(groupSuborder.getCashPay());
					}
					if(groupSuborder.getStatus() == 0){
						needPay=true;
					}
				}
				if(!needPay) {
					return "redirect:/index.html";
				}
				payment.setTotalFee(eorder.getRealPrice().subtract(cashPay));
				payment.setPayType(5);//4:团购订单支付
				payment.setOrderType(Integer.parseInt(type));
			}
			
		} else if("1".equals(type)) {
			/////////////////////////////////////////////////////////////////////////////////////////////
			//  团购订单
			/////////////////////////////////////////////////////////////////////////////////////////////
			if (!StringUtils.isEmpty(subOrderId)) {
				//团购支付
				GroupSuborder groupSuborder = this.suborderService.findGroupSuborderObjbyId(subOrderId);
				if(0 != groupSuborder.getStatus()){
					return "redirect:/index.html";
				}
				productName=groupSuborder.getProductName();
				payment.setSubOrderId(groupSuborder.getSubOrderId());
				payment.setTotalFee(groupSuborder.getCashPay()==null?groupSuborder.getRealPrice():groupSuborder.getRealPrice().subtract(groupSuborder.getCashPay()));
				payment.setPayType(4);		//4:团购订单支付
				payment.setOrderType(Integer.parseInt(type));
				
			} else if (orderId != null) {
				GroupOrders groupOrders = ordersService.findGroupOrdersById(orderId);
				payment.setOrderId(groupOrders.getOrderId());

				boolean needPay = false;
				List<GroupSuborder> GroupSuborderList = suborderService.findGroupSuborderbyId(orderId);
				productName=GroupSuborderList.get(0).getProductName();
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
					return "redirect:/index.html";
				}
				payment.setTotalFee(groupOrders.getRealPrice().subtract(cashPay));
				payment.setPayType(4);//4:团购订单支付
				payment.setOrderType(Integer.parseInt(type));
			}
			
		} else {

			Orders order = null;
			SubOrderVo suborder = null;

			if (!StringUtils.isEmpty(subOrderId)) {
				SuborderQuery query = new SuborderQuery();
				query.setUserId(user.getId());
				query.setSubOrderId(subOrderId);
				suborder = this.suborderService.findOrderDetailById(query);
				if (null == suborder) {
					return "redirect:/index.html";
				}else if(suborder.getStatus()!=0){//已关闭
					return "redirect:/index.html";
				}
				productName=suborder.getProductName();
				payment.setSubOrderId(suborder.getSubOrderId());
				payment.setTotalFee(suborder.getCashPay()==null?suborder.getRealPrice():suborder.getRealPrice().subtract(suborder.getCashPay()));
				payment.setPayType(1);		//1:订单支付
				payment.setOrderType(0);
			} else {

				order = ordersService.findById(user.getId(), orderId);
				if (null == order) {
					return "redirect:/index.html";
				}
				payment.setOrderId(order.getOrderId());
				List<SubOrderVo> subOrders = suborderService.findSubOrdersByOrderId(user.getId(), orderId);
				productName=subOrders.get(0).getProductName();
				BigDecimal cashPay = BigDecimal.ZERO;
				boolean needPay = false;
				for (SubOrderVo subOrderVo : subOrders) {
					if(subOrderVo.getCashPay() != null) {
						cashPay = cashPay.add(subOrderVo.getCashPay());
					}
					if(subOrderVo.getStatus()==0) {
						needPay=true;
					}
				}
				
				if(!needPay) {
					return "redirect:/index.html";
				}
				payment.setTotalFee(order.getRealPrice().subtract(cashPay));
				payment.setPayType(1);		//1:订单支付
				payment.setOrderType(0);
			}
		}

		payment.setOutTradeNo(dbUtils.CreateID() + "");
		payment.setStatus(0);
		payment.setCreateTime(new Date());
		payment.setWay(way);
		if("wxpay".equals(way)){
			// 设置公众账号ID/应用APPID
			payment.setAppId(wxPay.getAppId("WEB")); 
		} else if("zhifubao".equals(way)) {
			// 设置公众账号ID/应用APPID
			payment.setAppId(AlipayService.APP_ID_OLD); 			
		}
		paymentService.save(payment);
		if (way.equals("zhifubao")) {
			String payFrom = aliPay(payment,productName,request.getServerName());
			model.addAttribute("payFrom", payFrom);
			return "pay";
		} else if (way.equals("pingtaiyue")) {
			UserBalance userBalance = userBalanceService.findMoneyByUser(user.getId());
			BigDecimal money = userBalance.getBalance().subtract(payment.getTotalFee());

			//记录使用平台余额流水
			ActResult balanceFlowResult = ordersService.balanceFlow(orderId, subOrderId,payment.getTotalFee(), user);
			if (!balanceFlowResult.isSuccess()) {

				if("5".equals(type)) {
					/////////////////////////////////////////////////////////////////////////////////////////////
					//  换领订单
					/////////////////////////////////////////////////////////////////////////////////////////////
					if (!StringUtils.isEmpty(subOrderId)) {
						exchangeOrdersFacade.cancel(user, subOrderId, balanceFlowResult.getMsg(),-1,false);
					} else {
						exchangeOrdersFacade.cancelOrder(user, orderId, balanceFlowResult.getMsg(),false);
					}
					
				} else {
					if (!StringUtils.isEmpty(subOrderId)) {
						ordersFacade.cancel(user, subOrderId, balanceFlowResult.getMsg(),false);
						
					} else {
						ordersFacade.cancelOrder(user, orderId, balanceFlowResult.getMsg(),false);
						userBalanceService.balanceToPay(money, user.getId());
						payment.setStatus(1);
						paymentService.saveOrUpdate(payment);
					}
				}
			} else {

				payment.setTradeNo(balanceFlowResult.getData().toString());
				payment.setStatus(2);//支付成功已回调
				if("5".equals(type)) {
					exchangeOrdersFacade.updateOrderToPay(payment);
				} else {
					ordersFacade.updateOrderToPay(payment);
				}
				if (null != orderId)
					return "redirect:/member/paySuccess?orderId="+orderId+"&payType="+type;
				else if(null != subOrderId)
					return "redirect:/member/paySuccess?subOrderId="+subOrderId+"&payType="+type;
			}
			return "";
		} else if(way.equals("wxpay")) {
			try {
				String orderNo="";
				if(StringUtils.isEmpty(payment.getSubOrderId())) {
					model.addAttribute("keyType", "orderId");
					orderNo = payment.getOrderId()+"";
					model.addAttribute("orderNo", payment.getOrderId());
				} else {
					model.addAttribute("keyType", "subOrderId");
					orderNo = payment.getSubOrderId().substring(0,payment.getSubOrderId().indexOf("-"));
					model.addAttribute("orderNo", payment.getSubOrderId());
				}
				Map<String, String> responseMap = wxPay.unifiedOrderForWeb(IPUtils.getClientAddress(request),
						orderNo,
						productName,
						payment.getOutTradeNo(), payment.getTotalFee(), Constant.WXPAY_NOTIFY_URL);
				
				model.addAttribute("payQR", responseMap.get(WxPayService.CODE_URL));
				model.addAttribute("payment", payment);
				//微信预订单号
				payment.setTradeNo(responseMap.get(WxPayService.WEB_PREPAY_ID));
				paymentService.update(payment);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "wxpay";
			
		}else if(way.equals("unionpay")){
			Map<String, String> data = new HashMap<String, String>();
			// 版本号
			data.put("version", "5.0.0");
			// 字符集编码 默认"UTF-8"
			data.put("encoding", "UTF-8");
			// 签名方法 01 RSA
			data.put("signMethod", "01");
			// 交易类型 01-消费
			data.put("txnType", "01");
			// 交易子类型 01:自助消费 02:订购 03:分期付款
			data.put("txnSubType", "01");
			// 业务类型
			data.put("bizType", "000201");
			// 渠道类型，07-PC，08-手机
			data.put("channelType", "07");
			// 前台通知地址 ，控件接入方式无作用
			data.put("frontUrl", UnionPayConfig.REDIRECT_URL);
			// 后台通知地址
			data.put("backUrl", UnionPayConfig.NOTIFY_URL);
			// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
			data.put("accessType", "0");
			// 商户号码，请改成自己的商户号
			data.put("merId", UnionPayConfig.PARTNER);
			// 商户订单号，8-40位数字字母
			data.put("orderId", String.valueOf(payment.getOutTradeNo()));
			// 订单发送时间，取系统时间
			data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			// 交易金额，单位分
			data.put("txnAmt", payment.getTotalFee().multiply(new BigDecimal(100)).setScale(0).toString());
			// 交易币种
			data.put("currencyCode", "156");
			Map<String, String> submitFromData = UnionPayConfig.signData(data);
			model.addAttribute("payment", submitFromData);
			model.addAttribute("requestUrl", UnionPayConfig.REQUEST_URL);
			return "unionpay";
		} else {
			return "";
		}
	}


	/**
	 */
	private String aliPay(Payment payment,String productName,String serverName) {
		//String result = "";
		//订单名称
		String subject = "我的网订单";
		//订单描述
		String body = productName;
		//商品展示地址
		//String show_url = "";
		//需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html
		String returnUrl = returnUrl1;
		if(serverName.equals("ace.wd-w.com")) {
			returnUrl=returnUrl.replace("www.wd-w.com", "ace.wd-w.com").replace("test.wd-w.com", "ace.wd-w.com").replace("/wd-w.com", "/ace.wd-w.com");
		}
		return alipay.createDirectPay(payment.getOutTradeNo(), subject, payment.getTotalFee(), body, "", notifyUrl1, returnUrl);
		//return alipay.webPay(payment.getOutTradeNo(), subject, payment.getTotalFee(), body, notifyUrl, returnUrl);
	}

}
