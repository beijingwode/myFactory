package com.wode.factory.supplier.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.result.Result;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Product;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.SupplierAddress;
import com.wode.factory.model.SupplierExpress;
import com.wode.factory.model.SupplierLog;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.facade.OrderRefundFacade;
import com.wode.factory.supplier.facade.SuborderFacade;
import com.wode.factory.supplier.query.OrderTypeCountVO;
import com.wode.factory.supplier.service.OrdersService;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.RefundorderService;
import com.wode.factory.supplier.service.ReturnorderService;
import com.wode.factory.supplier.service.SuborderService;
import com.wode.factory.supplier.service.SupplierAddressService;
import com.wode.factory.supplier.service.SupplierExpressService;
import com.wode.factory.supplier.service.SupplierLogService;
import com.wode.factory.supplier.service.UserService;
import com.wode.factory.supplier.util.AppPushUtil;
import com.wode.factory.supplier.util.Constant;
import com.wode.factory.supplier.util.ExpressUtils;

/**
 * 2015-6-17
 *
 * @author 谷子夜
 */
@Controller
@RequestMapping("/app/suborder")
@ResponseBody
public class AppSuborderController extends BaseController {
	@Autowired
	private UserService userService;

	@Resource
	private RedisUtil redis;
	@Autowired
	@Qualifier("suborderService")
	private SuborderService suborderService;
	@Autowired
	@Qualifier("supplierLogService")
	private SupplierLogService supplierLogService;
	@Autowired
	@Qualifier("returnorderService")
	private ReturnorderService returnorderService;
	
	@Autowired
	private RefundorderService refundorderService;
	
	@Autowired
	@Qualifier("expressComService")
	private ExpressUtils expressComService;
	@Autowired
	@Qualifier("supplierAddressService")
	private SupplierAddressService supplierAddressService;
	@Autowired
	@Qualifier("productService-supplier")
	private ProductService productService;
	@Autowired
	@Qualifier("ordersService")
	private OrdersService ordersService;
	@Autowired
	@Qualifier("orderRefundFacade")
	private OrderRefundFacade orderRefundFacade;
	
	@Autowired
	private SupplierExpressService supplierExpressService;
	@Autowired
	private DBUtils dbUtils;
	@Autowired
	private SuborderFacade suborderFacade;
	/**
	 * 订单list
	 **/
	@RequestMapping(value = "selllist.user", method = RequestMethod.GET)
	@NoCheckLogin
	public ActResult<String> selllist(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Result result = new Result();
		ModelMap mv = new ModelMap();
		//在session中获取userModel

		UserFactory userFactory = userService.getById(loginUser.getId());
		
		String pages = request.getParameter("pages");
		String sizes = request.getParameter("sizes");
		Integer page=1;
		Integer size=10;
		if(pages==null||pages.equals("")){
			pages = "1";
		}
		page = new Integer(pages);
		
		if(sizes == null || sizes.equals("")){
			sizes="10";
		}
		
		size= new Integer(sizes);

		if(size>100){
			size=100;
		}

		String type=request.getParameter("type");
		String productName=request.getParameter("productName");
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		String buyerName=request.getParameter("buyerName");
		String status=request.getParameter("status");
		String commentStatus=request.getParameter("commentStatus");
		String afterserviceStatus=request.getParameter("afterserviceStatus");
		String subOrderId=request.getParameter("subOrderId");
		if(subOrderId!=null&&!subOrderId.trim().equals("")&&subOrderId.indexOf('-')>-1){
			subOrderId = subOrderId.substring(0, subOrderId.indexOf('-'));
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("supplierId", userFactory.getSupplierId());
		map.put("productName", productName);
		map.put("subOrderId", subOrderId);
		map.put("buyerName", buyerName);
		map.put("subOrderId",subOrderId);
		
		if(!StringUtils.isEmpty(starttime)){
			map.put("starttime",starttime+" 00:00:00");
		}
		if(!StringUtils.isEmpty(endtime)){
			map.put("endtime",endtime+" 23:59:59");
		}
		if(!StringUtils.isEmpty(status)){
			map.put("status",new Integer(status));
		}
		if(!StringUtils.isEmpty(commentStatus)){
			map.put("commentStatus",new Integer(commentStatus));
		}
		if(!StringUtils.isEmpty(afterserviceStatus)){
			map.put("afterserviceStatus",afterserviceStatus);
		}
		
		mv.addAttribute("pages",page);
		mv.addAttribute("sizes",size);
		
		mv.addAttribute("productName",productName);
		mv.addAttribute("starttime",starttime);
		mv.addAttribute("endtime",endtime);
		mv.addAttribute("status",status);
		mv.addAttribute("buyerName",buyerName);
		mv.addAttribute("subOrderId",subOrderId);
		mv.addAttribute("commentStatus",commentStatus);
		mv.addAttribute("afterserviceStatus",afterserviceStatus);
		
		if(StringUtils.isEmpty(type)){
			type ="";
		}
		
		if(type.equals("1")){
			map.put("starttimeTemp",TimeUtil.getBeforDate(30*3)+" 00:00:00");
		}else if(type.equals("2")){
			map.put("status",0);
		}else if(type.equals("3")){
			map.put("status",1);
		}else if(type.equals("4")){
			map.put("status",2);
		}else if(type.equals("5")){
			map.put("status",311);
		}else if(type.equals("6")){
			map.put("status",4);
		}else if(type.equals("7")){
			map.put("status",-1);
		}else if(type.equals("8")){
			map.put("endtimeTemp",TimeUtil.getBeforDate(30*3)+" 23:59:59");
		}
		
		mv.addAttribute("type",type);
		Integer total=0;
		Integer userType = userFactory.getType();
		if (userType==2 || userType==3) {//商家
			 total = suborderService.findSelllistPageCount(map);
		}
		Integer startnum=(page-1)*size;
		if(total>0){
			if(total<startnum){
				startnum=total-size;
			}
			if(startnum<0){
				startnum = 0;
			}
			map.put("startnum", startnum);
			map.put("size",size);
			List<Suborder> list = suborderService.findSelllistPage(map);
			for(int i=0;i<list.size();i++){
				if(list.get(i).getSuborderitemlist()!=null&&list.get(i).getSuborderitemlist().size()>0){
					for(Suborderitem si :list.get(i).getSuborderitemlist()){
						if(si.getItemValues()!=null&&!si.getItemValues().equals("")){
							String values = si.getItemValues();
							String valuesNew = "";
							Map valuesMap = JsonUtil.getMap4Json(values);
							 Iterator<Map.Entry<Integer, String>> iterator = valuesMap.entrySet().iterator();  
					        while (iterator.hasNext()) {  
					            Map.Entry<Integer, String> entry = iterator.next();  
					            valuesNew +="<div class='p2'>"+entry.getKey()+" : "+entry.getValue()+"</div>";
					        }  
					        si.setItemValues(valuesNew);
						}
					}
				}
			}
			result.setPage(page);
			result.setSize(size);
			result.setTotal(total);
			result.setErrorCode("0");
			result.setMsgBody(list);
		}else{
			result.setErrorCode("1000");
		}
		List<OrderTypeCountVO> cntLs= new ArrayList<OrderTypeCountVO>();
		if (userType==2 || userType==3) {
			 cntLs = suborderService.getOrderCount(userFactory.getSupplierId(), null, null);
		}
		// （0、未支付（待支付），1、已支付（待发货），2、已发货（待收货），3、退单申请中，4、已收货（待评价），10、买家已评价，11、已退货完毕，-1、已取消）
		// 待付款订单个数
		int nonPaymentCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), 0, null);
		// 待发货订单个数
		int unfilledCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), 1, null);
		// 已发货订单个数
		int notReceivingCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), 2, null);	
		//退款中
		int refundCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), 5, null);
		//已完成
		int completeCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), 5, null);
		//已完成
		int closeCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), 5, null);

		for (OrderTypeCountVO orderTypeCountVO : cntLs) {
			if(orderTypeCountVO.getStatus()==null) continue;
			
			switch(orderTypeCountVO.getStatus()) {
			case 0:
				nonPaymentCount=orderTypeCountVO.getCnt();
				break;
			case 1:
				unfilledCount=orderTypeCountVO.getCnt();
				break;
			case 2:
				notReceivingCount=orderTypeCountVO.getCnt();
				break;
			case 3:
				refundCount+=orderTypeCountVO.getCnt();
				break;
			case 4:
				completeCount=orderTypeCountVO.getCnt();
				break;
			case 5:
				refundCount+=orderTypeCountVO.getCnt();
				break;
			case -1:
				closeCount=orderTypeCountVO.getCnt();
				break;
			}
		}
				
		mv.addAttribute("result",result);
		
		mv.addAttribute("nonPaymentCount",nonPaymentCount);
		mv.addAttribute("unfilledCount",unfilledCount);
		mv.addAttribute("notReceivingCount",notReceivingCount);
		mv.addAttribute("refundCount",refundCount);
		mv.addAttribute("completeCount",completeCount);
		mv.addAttribute("closeCount",closeCount);

		return ActResult.success(mv);
	}


	
	/**
	 * to订单详情
	 **/
	@RequestMapping(value = "toOrderDetail.user", method = RequestMethod.GET)
	@NoCheckLogin
	public ActResult<String> toOrderDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap mv = new ModelMap();

		UserFactory userFactory = userService.getById(loginUser.getId());
		String subOrderId = request.getParameter("subOrderId");
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("subOrderId",subOrderId);
		map.put("supplierId",userFactory.getSupplierId());
		
		List<Suborder> suborderlist =  suborderService.findSuborderlistPage(map);
		for(int i=0;i<suborderlist.size();i++){
			if(suborderlist.get(i).getSuborderitemlist()!=null&&suborderlist.get(i).getSuborderitemlist().size()>0){
				for(Suborderitem si :suborderlist.get(i).getSuborderitemlist()){
					if(si.getItemValues()!=null&&!si.getItemValues().equals("")){
						String values = si.getItemValues();
						String valuesNew = "";
						Map valuesMap = JsonUtil.getMap4Json(values);
						 Iterator<Map.Entry<Integer, String>> iterator = valuesMap.entrySet().iterator();  
				        while (iterator.hasNext()) {  
				            Map.Entry<Integer, String> entry = iterator.next();  
				            valuesNew +="<div class='p2'>"+entry.getKey()+" : "+entry.getValue()+"</div>";
				        }  
				        si.setItemValues(valuesNew);
					}
				}
			}
		}
		Suborder suborder = null;
		Returnorder returnorder = new Returnorder();
		Refundorder refundorder = null;
		if(suborderlist.size()>0){
			suborder = suborderlist.get(0);
			if(suborder.getStatus()== 3 || suborder.getStatus()== 5 
					 || suborder.getStatus()== 11  || suborder.getStatus()== 12 
					 || suborder.getStatus()== -11  || suborder.getStatus()== -12 ){
				if(suborder.getRefundOrderId() != null) {
					refundorder = refundorderService.getRefundorById(suborder.getRefundOrderId());
				}
				if(suborder.getReturnOrderId() != null) {
					returnorder = returnorderService.returnOrderById(suborder.getReturnOrderId());
				}
			}
			
			// 获取用户昵称
			UserFactory user = userService.getById(suborder.getOrders().getUserId());
			if(user != null) {
				mv.addAttribute("user_nickName", user.getNickName());
			}
			mv.addAttribute("note", suborder.getOrders().getNote());
		}
		Map<String,Object> returnedMap = new HashMap<String,Object>();
		returnedMap.put("supplierId", userFactory.getSupplierId());
		returnedMap.put("returned", 1);
		List<SupplierAddress> returnedlist = supplierAddressService.fetchSupplierAddress(returnedMap);
		SupplierAddress supplierAddressReturned = null;
		if(returnedlist!=null&&returnedlist.size()>0){
			supplierAddressReturned = returnedlist.get(0);
		}
		Map<String,Object> sendMap = new HashMap<String,Object>();
		sendMap.put("supplierId", userFactory.getSupplierId());
		sendMap.put("send", 1);
		List<SupplierAddress> sendlist = supplierAddressService.fetchSupplierAddress(sendMap);
		SupplierAddress supplierAddressSend = null;
		if(sendlist!=null&&sendlist.size()>0){
			supplierAddressSend = sendlist.get(0);
		}
		// 获取物流信息
		if(suborder != null
				&& suborder.getExpressType() != null
				&& !("").equals(suborder.getExpressType())) {
			
		    if(!StringUtils.isEmpty(suborder.getExpressType())){
			    ExpressCompany ci = expressComService.getExpressComById(suborder.getExpressType());
			    if(ci!=null) {
					mv.addAttribute("compInfo", ci);
					mv.addAttribute("expressCom", ci.getPinYin());
				    mv.addAttribute("searchId", userFactory.getId());
			     }
			}
		}
		mv.addAttribute("shippingAddressSend", supplierAddressSend);
		mv.addAttribute("shippingAddressReturned", supplierAddressReturned);
		mv.addAttribute("suborder", suborder);
		mv.addAttribute("returnorder", returnorder);
		mv.addAttribute("refundorder", refundorder);
		////商品实体类
		
		return ActResult.success(mv);
	}
	

	
	/**
	 * to发货
	 **/
	@RequestMapping(value="toSendOut.user",method=RequestMethod.GET)
	@NoCheckLogin
	public ActResult<String> toSendOut(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap mv = new ModelMap();
		UserFactory userFactory = userService.getById(loginUser.getId());
		
		String subOrderId = request.getParameter("subOrderId");
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("subOrderId",subOrderId);
		map.put("supplierId",userFactory.getSupplierId());
		
		List<Suborder> suborderlist =  suborderService.findSuborderlistPage(map);
		for(int i=0;i<suborderlist.size();i++){
			if(suborderlist.get(i).getSuborderitemlist()!=null&&suborderlist.get(i).getSuborderitemlist().size()>0){
				for(Suborderitem si :suborderlist.get(i).getSuborderitemlist()){
					if(si.getItemValues()!=null&&!si.getItemValues().equals("")){
						String values = si.getItemValues();
						String valuesNew = "";
						Map valuesMap = JsonUtil.getMap4Json(values);
						 Iterator<Map.Entry<Integer, String>> iterator = valuesMap.entrySet().iterator();  
				        while (iterator.hasNext()) {  
				            Map.Entry<Integer, String> entry = iterator.next();  
				            valuesNew +="<div class='p2'>"+entry.getKey()+" : "+entry.getValue()+"</div>";
				        }  
				        si.setItemValues(valuesNew);
					}
				}
			}
		}
		Suborder suborder = suborderlist.get(0);
		Map<String,Object> returnedMap = new HashMap<String,Object>();
		returnedMap.put("supplierId", userFactory.getSupplierId());
		returnedMap.put("returned", 1);
		List<SupplierAddress> returnedlist = supplierAddressService.fetchSupplierAddress(returnedMap);
		SupplierAddress supplierAddressReturned = null;
		if(returnedlist!=null&&returnedlist.size()>0){
			supplierAddressReturned = returnedlist.get(0);
		}
		Map<String,Object> sendMap = new HashMap<String,Object>();
		sendMap.put("supplierId", userFactory.getSupplierId());
		sendMap.put("send", 1);
		List<SupplierAddress> sendlist = supplierAddressService.fetchSupplierAddress(sendMap);
		SupplierAddress supplierAddressSend = null;
		if(sendlist!=null&&sendlist.size()>0){
			supplierAddressSend = sendlist.get(0);
		}
		// 获取所有物流公司信息
		// 获取所有物流公司信息
	    Map<String, ExpressCompany> map_e = expressComService.getExpressCompanys();
	    // 获取常用物流公司信息
	 	List<SupplierExpress> commonExpressList = supplierExpressService.getBySupplierId(loginUser.getSupplierId());
		mv.addAttribute("allCompInfoList", map_e.values());
		mv.addAttribute("commonExpressList", commonExpressList);
		mv.addAttribute("shippingAddressSend", supplierAddressSend);
		mv.addAttribute("shippingAddressReturned", supplierAddressReturned);
		mv.addAttribute("suborder", suborder);
		////商品实体类

		return ActResult.success(mv);
	}
	
	/**
	 * 发送货物
	 **/
	@RequestMapping(value="sendOut.user",method=RequestMethod.POST)
	@NoCheckLogin
	public ActResult<String> sendOut(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap mv = new ModelMap();
		//在session中获取userModel
		UserFactory userFactory = userService.getById(loginUser.getId());
		
		String subOrderId = request.getParameter("subOrderId");
		String returnedAddress =  request.getParameter("returnedAddress");
		String sendAddress =  request.getParameter("sendAddress");
		String expressType =  request.getParameter("expressType");
		String expressNo =  request.getParameter("expressNo");
		Suborder suborder =suborderService.getById(subOrderId);
		suborder.setStatus(2);//发货
		if(suborder.getStockUp()!=null && suborder.getStockUp()==1){//修改订单备货状态
			suborder.setStockUp(0);
		}
		suborder.setReturnedAddress(returnedAddress);
		suborder.setSendAddress(sendAddress);
		suborder.setExpressNo(expressNo);
		suborder.setExpressType(expressType);
		Date now = new Date();
		suborder.setUpdateTime(now);//更新时间
		suborder.setUpdateBy(userFactory.getUserName());//更新者名称
		//发货时间更新错误   之前是taketime 应为 sendtime
		suborder.setSendTime(now);
		//业务逻辑为发货后更新最迟15天的最后收获时间  添加更新字段
		suborder.setLasttakeTime(TimeUtil.addDay(now, 15));
		suborderService.saveOrUpdate(suborder);
		String poductTitle = null;
		int num = 0;
		if(suborder.getSuborderitemlist()!=null&&suborder.getSuborderitemlist().size()>0){
			for(Suborderitem si :suborder.getSuborderitemlist()){
				num += si.getNumber();
				Product p = productService.getById(si.getProductId());
				if(p!=null){
					p.setAllnum(p.getAllnum()-si.getNumber());
					productService.saveOrUpdate(p);
													
					if(poductTitle==null) {
						poductTitle=p.getFullName();
					} else {
						if(!poductTitle.endsWith("...")){
							poductTitle += "...";
						}
					}
				}
			}
		}

		Orders o= ordersService.getById(suborder.getOrderId());
	    String msg = "";
	    String name = "";
	    if(o.isSelf()) {
			msg = "您购买的商品："+poductTitle+"已发货。请您确认是否已收到货物";
			name = "买家自提";
	    } else if("14660000000000000".equals(expressType)) {
			msg = "您购买的商品："+poductTitle+"商家已确认。卡券密码："+expressNo+"，请在使用时出示，并注意妥善保管,订单编号("+subOrderId+")";
			name = "电子卡券";
	    } else {
		    ExpressCompany com = expressComService.getExpressCompanys().get(expressType);
			msg = "您购买的商品："+poductTitle+"已经发货。快递公司："+com.getName()+"，运单号："+expressNo+"，请注意查收,订单编号("+subOrderId+")";
			name = com.getName();
	    }
	    AppPushUtil.pushMsg(o.getUserId(), "订单已发货", msg);
		
		//订单发出微信通知
		AppPushUtil.pushWxOrderSend(o.getUserId(), subOrderId, poductTitle, num+"", name, expressNo);
		
		Result result = new Result();
		result.setErrorCode("0");
		mv.addAttribute("result", result);
		return ActResult.success(mv);
	}

	/**
	 * ajax修改SubOrder
	 **/
	@RequestMapping(value="ajaxUpdateSuborder.user")
	@NoCheckLogin
	public ActResult<String> ajaxUpdateSuborder(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap mv = new ModelMap();
		Result result = new Result();
		
		String subOrderId =  request.getParameter("subOrderId");
		String status =  request.getParameter("status");//状态：-1：关闭
		String closeReason = request.getParameter("closeReason");//关闭状态
		String delivertime =  request.getParameter("delivertime");//延长时间：天
		Suborder suborder= suborderService.getById(subOrderId);
		if(!StringUtils.isEmpty(status)){
			suborder.setStatus(new Integer(status));
			suborder.setCloseReason(closeReason);
		}
		if(!StringUtils.isEmpty(delivertime)){
			suborder.setLasttakeTime(new Date(suborder.getLasttakeTime().getTime()+new Integer(delivertime)*24*3600*1000));
		}
		if(suborder.getStatus() == -1) {
			if(this.cancelOrder(suborder, closeReason)) {
				result.setErrorCode("0");
			} else {
				result.setErrorCode("10000");
			}
		} else {
			suborderService.saveOrUpdate(suborder);
			result.setErrorCode("0");
		}
	
		mv.addAttribute("result", result);
		return ActResult.success(mv);
	}
	

	private boolean cancelOrder(Suborder suborder,String closeReason){
		Map paramMap = new HashMap();
		Orders o= ordersService.getById(suborder.getOrderId());
		paramMap.put("userId", o.getUserId());
		paramMap.put("subOrderId", suborder.getSubOrderId());
		paramMap.put("closeReason", closeReason);

		try {
			String response = HttpClientUtil.sendHttpRequest("post", Constant.CREATHTML_API_URL.replace("creatHtml","member/autoCancelOrder"), paramMap);
			ActResult as = JsonUtil.getObject(response, ActResult.class);
			return as.isSuccess(); 
		} catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * ajax修改subOrder的stockUp
	 */
	@RequestMapping(value="ajaxUpdateStockUp.user")
	@NoCheckLogin
	public ActResult<String> ajaxUpdateStockUp(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelMap mv = new ModelMap();
		Result result = new Result();
		
		String subOrderId = request.getParameter("subOrderId");
		String stockUp = request.getParameter("stockUp");
		Suborder suborder = suborderService.getById(subOrderId);
		if (suborder!=null) {
			suborder.setStockUp(new Integer(stockUp));
		}
		suborderService.update(suborder);
		
		result.setErrorCode("0");
		mv.addAttribute("result", result);
		return ActResult.success(mv);
	}

	/**
	 * 确认退款
	 **/
	@RequestMapping(value = "returncheck.user")
	@NoCheckLogin
	public ActResult<String> returncheck(HttpServletRequest request, HttpServletResponse response) {
		UserFactory userFactory = userService.getById(loginUser.getId());
		String returnOrderId = request.getParameter("returnOrderId");
		String subOrderId = request.getParameter("subOrderId");
		String refundOrderId = request.getParameter("refundOrderId");
		String userId = request.getParameter("userId");
		ActResult<String> ar = new ActResult<String>();

		boolean checkResult = false;
		//退款单
		Refundorder refundorder = refundorderService.getById(Long.parseLong(refundOrderId));
		//退货单
		Returnorder returnorder = null;

		//调用退款接口
		//if(!StringUtils.isNullOrEmpty(refundorder)&&refundorder.getStatus()==2){
		//暂时忽视退款 申请中状态， 2015-11-26 gaoyj
		if (!StringUtils.isNullOrEmpty(refundorder) && refundorder.getStatus() == 1) {
			if (!StringUtils.isNullOrEmpty(returnOrderId)) {
				returnorder = returnorderService.getById(refundorder.getReturnOrderId());

				//if(!StringUtils.isNullOrEmpty(returnorder) && returnorder.getStatus()==1){
				//暂时忽视退货成功状态， 2015-11-26 gaoyj
				if (!StringUtils.isNullOrEmpty(returnorder) && returnorder.getStatus() == 0) {
					if (!StringUtils.isNullOrEmpty(userId) && StringUtils.isEquals(returnorder.getUserId(), userId)) {
						checkResult = true;
					} else {
						ar.setSuccess(false);
						ar.setMsg("退货单中UserId与传入参数userId不匹配");
					}
				} else {
					ar.setSuccess(false);
					ar.setMsg("returnOrderId：" + refundorder.getReturnOrderId() + " 在退款表中不存在匹配数据");
				}
			} else {
				checkResult = true;
			}
		} else {
			ar.setSuccess(false);
			ar.setMsg("refundOrderId：" + refundorder.getReturnOrderId() + " 在退款表中不存在匹配数据");
		}

		//检查Ok
		if (checkResult) {
			Suborder suborder = suborderService.getById(subOrderId);
			if (suborder == null) {
				ar.setSuccess(false);
				ar.setMsg("subOrderId：" + refundorder.getReturnOrderId() + " 在订单表中不存在匹配数据");

			} else {
				try {
					Long customerId = refundorder.getUserId() != null ? refundorder.getUserId() : returnorder.getUserId();
					ar = orderRefundFacade.refundToUser(refundorder, returnorder, suborder, customerId, userFactory.getUserName(), null);
				} catch (Exception e) {
					ar.setSuccess(false);
					ar.setMsg("系统异常");
				}
			}
		}
		
		return ar;
	}


	@RequestMapping(value = "ajaxRefuse.user")
	@NoCheckLogin
	public ActResult<String> ajaxRefuse(HttpServletRequest request, HttpServletResponse response) {
		UserFactory userFactory = userService.getById(loginUser.getId());
		String refundOrderId = request.getParameter("refundOrderId");
		String refuseNote = request.getParameter("refuseNote");
		String subOrderId = request.getParameter("subOrderId");
		ActResult<String> ar = new ActResult<String>();

		//退款单
		Refundorder refundorder = refundorderService.getById(Long.parseLong(refundOrderId));
		//退货单
		Returnorder returnorder = null;
		if (!StringUtils.isNullOrEmpty(refundorder.getReturnOrderId())) {
			returnorder = returnorderService.getById(refundorder.getReturnOrderId());
		}
		ar = orderRefundFacade.refuseApply(refundorder, returnorder, suborderService.getById(subOrderId), refuseNote, userFactory.getUserName());
		return ar;
	}

	@RequestMapping(value="updateFreight.user",method=RequestMethod.POST)
	@NoCheckLogin
	public ActResult changeFreight(HttpServletRequest request, String suborder, Double freight){
		UserFactory userModel = userService.getById(loginUser.getId());

		//更改运费处理
		ActResult rt= suborderFacade.changeFreight(userModel.getSupplierId(), suborder, freight, userModel.getUserName());

		if(rt.isSuccess()){					
			SupplierLog supplierLog = new SupplierLog();
			supplierLog.setUserId(userModel.getId());
			supplierLog.setUsername(userModel.getUserName());
			supplierLog.setTime(new Date());
			supplierLog.setAct("订单"+suborder+"运费由"+rt.getData()+"修改为"+freight);
			supplierLog.setResult("修改成功");
			supplierLogService.save(supplierLog);
			return ActResult.success(null);
		} else {
			return rt;
		}
	}
	@RequestMapping(value="page")
	@NoCheckLogin
	public ModelAndView page(HttpServletRequest request,ModelAndView model,String status){
		if (!StringUtils.isNullOrEmpty(status)) {
			model.addObject("status", status);
			model.setViewName("order_list");
		}else{
			model.setViewName("transaction_management");
		}
		return model;
	}
	/**
	 * 微信订单详情
	 * @param request
	 * @param model
	 * @param status
	 * @return
	 */
	@RequestMapping(value="orderDetail")
	@NoCheckLogin
	public ModelAndView orderDetail(HttpServletRequest request,ModelAndView model,String subOrderId,Integer flag){
		model.addObject("subOrderId", subOrderId);
		if(flag!=null) {
			if (1==flag) {//订单详情
				//model.addObject("status", status);
				model.setViewName("oder_detail");
			}else if(2==flag){//发货页面
				model.setViewName("send_good");
			}
		}
		return model;
	}
	/**
	 * 获取所有快递公司信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toSupplierExpress.user")
	@NoCheckLogin
	public ActResult<Object> toSupplierExpress(HttpServletRequest request,HttpServletResponse response) {
		ModelMap mv = new ModelMap();
		// 获取所有物流公司信息
		Map<String, ExpressCompany> map_e = expressComService.getExpressCompanys();
		List<SupplierExpress> listexpree = supplierExpressService.getBySupplierId(loginUser.getSupplierId());
		String supplierExpressids="";
		for (SupplierExpress supplierExpress : listexpree) {
			 Long id=supplierExpress.getExpressId();
			 supplierExpressids+=id+",";
		}
		mv.addAttribute("supplierExpressids", supplierExpressids);
		mv.addAttribute("allCompInfoList", map_e.values());
		return ActResult.success(mv);
	}
	
	/**
	 * 设置常用快递公司
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="ajaxUpdateSupplierExpress.user",method=RequestMethod.POST)
	@NoCheckLogin
	public ActResult<Object> supplierExpress(HttpServletRequest request,HttpServletResponse response,String expressIds) {
		if (StringUtils.isNullOrEmpty(expressIds)) {
			return ActResult.fail("请选择快递公司！");
		}
		SupplierExpress supplierExpress =new SupplierExpress();
		//删除已有的快递公司
		supplierExpressService.deletebySupplierId(loginUser.getSupplierId());
		String[] ids = expressIds.split(",");
		if (ids!=null && ids.length>0) {
			for(int i = 0; i < ids.length; i++){
				supplierExpress.setExpressId(new Long(ids[i]));
				Map<String, ExpressCompany> map_e = expressComService.getExpressCompanys();
				for(String id : map_e.keySet()){
					if(id.equals(ids[i])){
						ExpressCompany express=map_e.get(id);
						supplierExpress.setName(express.getName());
					}
				}
				supplierExpress.setId(dbUtils.CreateID());
				supplierExpress.setSupplierId(loginUser.getSupplierId());
				supplierExpressService.insert(supplierExpress);
				
			}
			return ActResult.successSetMsg("保存成功！");
		}else{
			return ActResult.fail("请选择快递公司！");
		}
	}
}
