/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.db.DBUtils;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.result.Result;
import com.wode.common.stereotype.Token;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.InvoiceApply;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Product;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierAddress;
import com.wode.factory.model.SupplierDuration;
import com.wode.factory.model.SupplierExpress;
import com.wode.factory.model.SupplierLog;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.GroupBuyService;
import com.wode.factory.supplier.facade.SuborderFacade;
import com.wode.factory.supplier.service.InventoryService;
import com.wode.factory.supplier.service.InvoiceApplyService;
import com.wode.factory.supplier.service.OrdersService;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.RefundorderService;
import com.wode.factory.supplier.service.ReturnorderService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SuborderService;
import com.wode.factory.supplier.service.SupplierAddressService;
import com.wode.factory.supplier.service.SupplierDurationService;
import com.wode.factory.supplier.service.SupplierExpressService;
import com.wode.factory.supplier.service.SupplierLogService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.service.UserService;
import com.wode.factory.supplier.util.AppPushUtil;
import com.wode.factory.supplier.util.Constant;
import com.wode.factory.supplier.util.ExpressUtils;
import com.wode.factory.supplier.util.UserInterceptor;
import com.wode.factory.vo.GroupBuyVo;

@Controller
@RequestMapping("suborder")
public class SuborderController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	private SupplierDurationService supplierDurationService;
	
	@Autowired
	@Qualifier("suborderService")
	private SuborderService suborderService;
	
	@Autowired
	private SuborderFacade suborderFacade;
	
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	@Autowired
	@Qualifier("supplierAddressService")
	private SupplierAddressService supplierAddressService;
	@Autowired
	@Qualifier("ordersService")
	private OrdersService ordersService;
	
	@Autowired
	@Qualifier("inventoryService")
	private InventoryService inventoryService;
	
	@Autowired
	@Qualifier("productService-supplier")
	private ProductService productService;

	@Autowired
	@Qualifier("supplierLogService")
	private SupplierLogService supplierLogService;

	@Autowired
	@Qualifier("returnorderService")
	private ReturnorderService returnorderService;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	
	@Autowired
	private RefundorderService refundorderService;

	@Autowired
	@Qualifier("expressComService")
	private ExpressUtils expressComService;
	@Autowired
	private SupplierExpressService supplierExpressService;
	@Autowired
	private DBUtils dbUtils;
	
	@Autowired
	private InvoiceApplyService invoiceApplyService;
	@Autowired
	private GroupBuyService groupBuyService;
	
	public SuborderController() {
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	
	/**
	 *商品list
	 **/
	@RequestMapping(value="gotoSuborderlist",method=RequestMethod.GET)
	@Token(remove=true)
	public ModelAndView gotoProductlist(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return suborderlist(request,response);
	}
	
	/**
	 * 商品list
	 **/
	@RequestMapping(value="findSuborderlistPage",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView findProductlistPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return suborderlist(request,response);
	}
	/**
	 * 商品list
	 **/
	public ModelAndView suborderlist(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			
			String pages = request.getParameter("pages");
			String sizes = request.getParameter("sizes");
			Integer page=1;
			Integer size=10;
			mv.setViewName("product/suborder/suborderlist");
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
	
			String name = request.getParameter("name");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String status = request.getParameter("status");
			String subOrderId = request.getParameter("subOrderId");
			String expressType=request.getParameter("expressType");
			String expressNo = request.getParameter("expressNo");
			String selfDelivery = request.getParameter("selfDelivery");
			
			String invoiceStatus = request.getParameter("invoiceStatus");//发票状态
			
			
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("supplierId", supplier.getId());
			map.put("name", name);
			map.put("subOrderId", subOrderId);
			map.put("expressType", expressType);
			map.put("expressNo", expressNo);
			if(!StringUtils.isEmpty(starttime)){
				map.put("starttime",starttime+" 00:00:00");
			}
			if(!StringUtils.isEmpty(endtime)){
				map.put("endtime",endtime+" 23:59:59");
			}
			if(StringUtils.isEmpty(status)){
				status = "1";
			}
			map.put("status",new Integer(status));
			map.put("subOrderId",subOrderId);
			if(!StringUtils.isEmpty(expressType)){
				map.put("expressType", expressType);
			}
			
			if(!StringUtils.isEmpty(invoiceStatus)){//发票状态
				map.put("invoiceStatus", invoiceStatus);
			}
			if(!StringUtils.isEmpty(selfDelivery)){
				map.put("selfDelivery", selfDelivery);
			}else {
				selfDelivery = "0";
				map.put("selfDelivery", 0);
			}
			mv.addObject("invoiceStatus",invoiceStatus);
			
			mv.addObject("pages",page);
			mv.addObject("sizes",size);
			mv.addObject("name",name);
			mv.addObject("starttime",starttime);
			mv.addObject("endtime",endtime);
			mv.addObject("status",status);
			mv.addObject("subOrderId",subOrderId);
			mv.addObject("expressType",expressType);
			mv.addObject("expressNo",expressNo);
			mv.addObject("selfDelivery",selfDelivery);
			
			// 获取所有物流公司信息
		    Map<String, ExpressCompany> map_e = expressComService.getExpressCompanys();
			mv.addObject("allCompInfoList", map_e.values());
			
			Integer total = suborderService.findSuborderlistPageCount(map);
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
				List<Suborder> list = suborderService.findSuborderlistPage(map);
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
					            valuesNew +="<div class='p2'>条码 : "+si.getProductCode()+"</div>";
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
			mv.addObject("result",result);
		}
		return mv;
	}

	
	/**
	 * to发货
	 **/
	@RequestMapping(value="toSendOut",method=RequestMethod.GET)
	@Token(remove=true)
	public ModelAndView toSendOut(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			mv.setViewName("product/suborder/sendout");
			String subOrderId = request.getParameter("subOrderId");
			String gotoType = request.getParameter("gotoType");
			String type = request.getParameter("type");
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("subOrderId",subOrderId);
			map.put("supplierId",supplier.getId());
			
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
				            valuesNew +="<div class='p2'>条码 : "+si.getProductCode()+"</div>";
					        si.setItemValues(valuesNew);
						}
					}
				}
			}
			Suborder suborder = suborderlist.get(0);
			/*Map<String,Object> returnedMap = new HashMap<String,Object>();
			returnedMap.put("supplierId", userModel.getSupplierId());
			returnedMap.put("returned", 1);
			List<SupplierAddress> returnedlist = supplierAddressService.fetchSupplierAddress(returnedMap);
			SupplierAddress supplierAddressReturned = null;
			if(returnedlist!=null&&returnedlist.size()>0){
				supplierAddressReturned = returnedlist.get(0);
			}*/
			Map<String,Object> sendMap = new HashMap<String,Object>();
			sendMap.put("supplierId", userModel.getSupplierId());
			sendMap.put("send", 1);
			List<SupplierAddress> sendlist = supplierAddressService.fetchSupplierAddress(sendMap);
			SupplierAddress supplierAddressSend = null;
			if(sendlist!=null&&sendlist.size()>0){
				supplierAddressSend = sendlist.get(0);
			}
			
			// 获取所有物流公司信息
			Map<String, ExpressCompany> map_e = expressComService.getExpressCompanys();
			// 获取常用物流公司信息
			List<SupplierExpress> listexpree = supplierExpressService.getBySupplierId(userModel.getSupplierId());

			mv.addObject("CompInfoList", listexpree);
			mv.addObject("allCompInfoList", map_e.values());	
			mv.addObject("supplierAddressSend", supplierAddressSend);
			//mv.addObject("", supplierAddressReturned);
			mv.addObject("suborder", suborder);
			mv.addObject("gotoType",gotoType);
			mv.addObject("type",type);
			////商品实体类
			
			//查询发票申请
			InvoiceApply invoiceApply = new InvoiceApply();
			invoiceApply.setSuborderid(suborder.getSubOrderId());
			List<InvoiceApply> list = invoiceApplyService.selectByModel(invoiceApply);
			if(list != null && !list.isEmpty()){
				mv.addObject("invoiceApply",list.get(0));
			}
		}
		return mv;
	}
	
	/**
	 * to发货
	 **/
	@RequestMapping(value="toSendOutAll",method=RequestMethod.GET)
	@Token(remove=true)
	public ModelAndView toSendOutAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			mv.setViewName("product/suborder/sendoutall");
			String gotoType = request.getParameter("gotoType");
			String type = request.getParameter("type");
			List<String> ids = new ArrayList<String>();
			String idsTemp = request.getParameter("ids");
			String[] subOrderIds = idsTemp.split(",");
			for(int i=0;i<subOrderIds.length;i++){
				ids.add(subOrderIds[i]);
			}
			
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("subOrderIds",ids);
			map.put("supplierId",supplier.getId());
			
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
				            valuesNew +="<div class='p2'>条码 : "+si.getProductCode()+"</div>";
					        si.setItemValues(valuesNew);
						}
					}
				}
			}
			/*Map<String,Object> returnedMap = new HashMap<String,Object>();
			returnedMap.put("supplierId", userModel.getSupplierId());
			returnedMap.put("returned", 1);
			List<SupplierAddress> returnedlist = supplierAddressService.fetchSupplierAddress(returnedMap);
			SupplierAddress supplierAddressReturned = null;
			if(returnedlist!=null&&returnedlist.size()>0){
				supplierAddressReturned = returnedlist.get(0);
			}*/
			Map<String,Object> sendMap = new HashMap<String,Object>();
			sendMap.put("supplierId", userModel.getSupplierId());
			sendMap.put("send", 1);
			List<SupplierAddress> sendlist = supplierAddressService.fetchSupplierAddress(sendMap);
			SupplierAddress supplierAddressSend = null;
			if(sendlist!=null&&sendlist.size()>0){
				supplierAddressSend = sendlist.get(0);
			}
			
			// 获取所有物流公司信息
			Map<String, ExpressCompany> map_e = expressComService.getExpressCompanys();
			// 获取常用物流公司信息
			List<SupplierExpress> listexpree = supplierExpressService.getBySupplierId(userModel.getSupplierId());
			
			mv.addObject("CompInfoList", listexpree);
			mv.addObject("allCompInfoList", map_e.values());
			mv.addObject("supplierAddressSend", supplierAddressSend);
			//mv.addObject("supplierAddressReturned", supplierAddressReturned);
			mv.addObject("suborderlist", suborderlist);
			mv.addObject("gotoType",gotoType);
			mv.addObject("type",type);
			////商品实体类
		}
		return mv;
	}
	
	/**
	 * 发送货物
	 **/
	@RequestMapping(value="sendOut",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView sendOut(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			String subOrderId = request.getParameter("subOrderId");
			String returnedAddress =  request.getParameter("returnedAddress");
			String sendAddress =  request.getParameter("sendAddress");
			String expressType =  request.getParameter("expressType");
			String expressNo =  request.getParameter("expressNo");
			String gotoType = request.getParameter("gotoType");
			String type = request.getParameter("type");
			Suborder suborder =suborderService.getById(subOrderId);
			if(3==suborder.getStatus()|| 5==suborder.getStatus()){
				result.setMessage("订单状态已改变,请刷新查看该订单最新状态.");
				mv.addObject("result", result);
				mv.setViewName("redirect:/suborder/gotoSelllist.html");
				logger.error(suborder.getSubOrderId()+"订单状态已改变,请刷新查看该订单最新状态." );
				return mv;
			}
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
			suborder.setUpdateBy(userModel.getUserName());//更新者名称
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
					/*Inventory inve = inventoryService.getById(si.getSkuId());
					if(inve!=null){
						Integer locknum = inve.getLockQuantity() - si.getNumber();
						inve.setLockQuantity(locknum);
						inventoryService.saveOrUpdate(inve);
					}*/
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
			
			if(gotoType!=null&&gotoType.equals("suborderlist")){
				mv.setViewName("redirect:/suborder/gotoSuborderlist.html"); 
			}else{
				mv.setViewName("redirect:/suborder/gotoSelllist.html?type="+type); 
			}
			
			// 团订单
			if("1".equals(suborder.getOrderType())) {
				GroupBuyVo vo = groupBuyService.getById(suborder.getRelationId());
				if(vo!=null) {
					vo.setOrderStatus(2);	// 已发货
					vo.setOrderSendTime(now);
					vo.setExpressType(expressType);
					vo.setExpressNo(expressNo);
					
					groupBuyService.update(vo);
					
					AppPushUtil.pushGroupBuyMsg(vo.getId(), 2);
				}
			}
		}
		return mv;
	}

	/**
	 * 发送货物
	 **/
	@RequestMapping(value="sendOutAll",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView sendOutAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			String expressType =  request.getParameter("expressType");//物流公司
			String sendAddress =request.getParameter("sendAddress");//
			String returnedAddress =request.getParameter("returnedAddress");//
			String[] suborder_result = request.getParameterValues("suborder_result");
			String gotoType = request.getParameter("gotoType");
			String type = request.getParameter("type");
			
			if(suborder_result!=null){
				for(int i=0;i<suborder_result.length;i++){
					String[] results = suborder_result[i].split("\\|_\\|",-1);
					String subOrderId = results[0];
					String expressNo =  results[1];
					Suborder suborder =suborderService.getById(subOrderId);
					if(3==suborder.getStatus()|| 5==suborder.getStatus()){
						result.setMessage("订单"+suborder.getSubOrderId()+"状态已改变,请刷新查看该订单最新状态.");
						mv.setViewName("redirect:/suborder/gotoSelllist.html");
						return mv;
					}
					suborder.setStatus(2);//发货
					if(suborder.getStockUp()!=null && suborder.getStockUp()==1){//修改订单备货状态
						suborder.setStockUp(0);
					}
					suborder.setSendAddress(sendAddress);
					suborder.setReturnedAddress(returnedAddress);
					suborder.setExpressNo(expressNo);
					suborder.setExpressType(expressType);
					Date now = new Date();
					suborder.setUpdateTime(now);//更新时间
					suborder.setUpdateBy(userModel.getUserName());//更新者名称
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
					

					// 团订单
					if("1".equals(suborder.getOrderType())) {
						GroupBuyVo vo = groupBuyService.getById(suborder.getRelationId());
						if(vo!=null) {
							vo.setOrderStatus(2);	// 已发货
							vo.setOrderSendTime(now);
							vo.setExpressType(expressType);
							vo.setExpressNo(expressNo);
							
							groupBuyService.update(vo);
							
							AppPushUtil.pushGroupBuyMsg(vo.getId(), 2);
						}
					}
				}
			}
			if(gotoType!=null&&gotoType.equals("suborderlist")){
				mv.setViewName("redirect:/suborder/gotoSuborderlist.html"); 
			}else{
				mv.setViewName("redirect:/suborder/gotoSelllist.html?type="+type); 
			}
		}
		return mv;
	}
	
	/**
	 * 显示所有的快递公司信息
	 **/
	@RequestMapping(value="toSupplier_express",method=RequestMethod.GET)
	@Token(remove=true)
	public ModelAndView toSupplier_express(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		String blank =request.getParameter("blank");
		
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			mv.setViewName("product/suborder/Supplier_express");
			// 获取所有物流公司信息
		    Map<String, ExpressCompany> map_e = expressComService.getExpressCompanys();
			mv.addObject("allCompInfoList", map_e.values());
			List<SupplierExpress> listexpree = supplierExpressService.getBySupplierId(userModel.getSupplierId());
			String supplierExpressids="";
			for (SupplierExpress supplierExpress : listexpree) {
				 Long id=supplierExpress.getExpressId();
				 supplierExpressids+=id+",";
			}
			mv.addObject("supplierExpressids", supplierExpressids);
			mv.addObject("blank", blank);
			////商品实体类
		}
		return mv;
	}
	/**
	 * 保存常用快递公司信息
	 **/
	@RequestMapping(value="Supplier_express",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView Supplier_express(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();		
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			SupplierExpress supplierExpress =new SupplierExpress();
			Long supplierId=userModel.getSupplierId();
			//删除已有的快递公司
			supplierExpressService.deletebySupplierId(supplierId);
			String [] ids=request.getParameterValues("id");
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
				supplierExpress.setSupplierId(supplierId);
				supplierExpressService.insert(supplierExpress);
				
			}
			String blank =request.getParameter("blank");
			if("1".equals(blank)){
				mv.setViewName("redirect:/suborder/toSupplier_express.html");
				mv.addObject("blank", blank);
			}else{
				mv.setViewName("redirect:/suborder/gotoSuborderlist.html");
			}
		}
		
		return mv;
	}

	/**
	 *商品list
	 **/
	@RequestMapping(value="gotoSelllist",method=RequestMethod.GET)
	@Token(remove=true)
	public ModelAndView gotoSelllist(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return selllist(request,response);
	}
	
	/**
	 * 商品list
	 **/
	@RequestMapping(value="findSelllistPage")
	@Token(remove=true)
	public ModelAndView findSelllistPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return selllist(request,response);
	}
	
	/**
	 * 商品list
	 **/
	public ModelAndView selllist(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			
			String pages = request.getParameter("pages");
			String sizes = request.getParameter("sizes");
			Integer page=1;
			Integer size=10;
			mv.setViewName("product/suborder/selllist");
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
			String stockUp = request.getParameter("stockUp");
			String commentStatus=request.getParameter("commentStatus");
			String afterserviceStatus=request.getParameter("afterserviceStatus");
			String subOrderId=request.getParameter("subOrderId");
			if(subOrderId!=null&&!subOrderId.trim().equals("")&&subOrderId.indexOf('-')>-1){
				subOrderId = subOrderId.substring(0, subOrderId.indexOf('-'));
			}
			String invoiceStatus = request.getParameter("invoiceStatus");//发票状态
			String selfDelivery = request.getParameter("selfDelivery");//发票状态
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("supplierId", supplier.getId());
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
			if(!StringUtils.isEmpty(stockUp)){
				map.put("stockUp", new Integer(stockUp));
				//map.put("status", 1);//老数据处理设置status为待发货
			}
			if(!StringUtils.isEmpty(commentStatus)){
				map.put("commentStatus",new Integer(commentStatus));
			}
			if(!StringUtils.isEmpty(afterserviceStatus)){
				map.put("afterserviceStatus",afterserviceStatus);
			}
			if(!StringUtils.isEmpty(invoiceStatus)){
				map.put("invoiceStatus",invoiceStatus);
			}
			if(!StringUtils.isEmpty(selfDelivery)){
				map.put("selfDelivery",selfDelivery);
			}else {
				selfDelivery ="0";
				map.put("selfDelivery",0);
			}
			mv.addObject("invoiceStatus",invoiceStatus);
			
			mv.addObject("pages",page);
			mv.addObject("sizes",size);
			
			mv.addObject("productName",productName);
			mv.addObject("starttime",starttime);
			mv.addObject("endtime",endtime);
			mv.addObject("status",status);
			mv.addObject("stockUp", stockUp);
			mv.addObject("buyerName",buyerName);
			mv.addObject("subOrderId",subOrderId);
			mv.addObject("commentStatus",commentStatus);
			mv.addObject("afterserviceStatus",afterserviceStatus);
			
			if(StringUtils.isEmpty(type)){
				type ="1";
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
			
			mv.addObject("type",type);
			mv.addObject("selfDelivery",selfDelivery);

			Integer total = suborderService.findSelllistPageCount(map);
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
								if(!values.contains("<div")) {
									String valuesNew = "";
									Map valuesMap = JsonUtil.getMap4Json(values);
									 Iterator<Map.Entry<Integer, String>> iterator = valuesMap.entrySet().iterator();  
							        while (iterator.hasNext()) {  
							            Map.Entry<Integer, String> entry = iterator.next();  
							            valuesNew +="<div class='p2'>"+entry.getKey()+" : "+entry.getValue()+"</div>";
							        }  
						            valuesNew +="<div class='p2'>条码 : "+si.getProductCode()+"</div>";
							        si.setItemValues(valuesNew);
								}
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
			mv.addObject("result",result);
		}
		return mv;
	}

	@RequestMapping(value="updateFreight",method=RequestMethod.POST)
	@Token(remove=true)
	@ResponseBody
	public ActResult changeFreight(HttpServletRequest request, String suborder, Double freight){
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			return ActResult.fail("用户未登录!");
		} else {
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
	}
	/**
	 * 点击备货ajax修改SubOrder的stockUp状态
	 */
	@RequestMapping(value="ajaxUpdateSuborderById",method=RequestMethod.GET)
	@Token(remove=true)
	public ModelAndView ajaxUpdateSuborderById(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			String subOrderId =  request.getParameter("subOrderId");
			String stockUp =  request.getParameter("stockUp");
			Suborder suborder= suborderService.getById(subOrderId);
			if (suborder!=null) {
				suborder.setStockUp(new Integer(stockUp));
			}
			suborderService.update(suborder);
			result.setErrorCode("0");
		}
		mv.addObject("result", result);
		return mv;
	}
	
	/**
	 * ajax修改SubOrder
	 **/
	@RequestMapping(value="ajaxUpdateSuborder",method=RequestMethod.GET)
	@Token(remove=true)
	public ModelAndView ajaxUpdateSuborder(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			
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
		}
		mv.addObject("result", result);
		return mv;
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
	 * to订单详情
	 **/
	@RequestMapping(value="toOrderDetail",method=RequestMethod.GET)
	public ModelAndView toOrderDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			mv.setViewName("product/order/orderdetail");
			String subOrderId = request.getParameter("subOrderId");
	        SupplierDuration sd = supplierDurationService.getBySupplierId(userModel.getSupplierId());
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("subOrderId",subOrderId);
			map.put("supplierId",supplier.getId());
			
			BigDecimal commission = BigDecimal.ZERO;
			BigDecimal noCommission = NumberUtil.toBigDecimal(-1);
			List<Suborder> suborderlist =  suborderService.findSuborderlistPage(map);
			for(int i=0;i<suborderlist.size();i++){
				if(suborderlist.get(i).getSuborderitemlist()!=null&&suborderlist.get(i).getSuborderitemlist().size()>0){
					if(supplier.getId().equals(suborderlist.get(i).getOrders().getUpdateBy())) {
						commission = noCommission;
					}
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
						if(si.getSaleKbn() == 2){
							si.setCommissionRatio(0f);
						}
						if(commission.compareTo(noCommission) != 0) {
							commission=commission.add(si.getRealPay().multiply(BigDecimal.valueOf(si.getCommissionRatio()==null?0:si.getCommissionRatio())).divide(BigDecimal.valueOf(100),sd.getScale(),sd.getRoundMode()));
						}
					}
				}
			}
			Suborder suborder = null;
			//Returnorder returnorder = new Returnorder();
			//Refundorder refundorder = null;
			Boolean flag = false;
			Boolean isAftermarketOrder = false;
			BigDecimal totalBenefitTicket =new BigDecimal("0");
			if(suborderlist.size()>0){
				suborder = suborderlist.get(0);
			    for (Suborderitem suborderitem : suborder.getSuborderitemlist()) {
			    	if ((suborderitem.getBenefitType() !=null) && (suborderitem.getBenefitType()==3)) {//换领
			    		flag=true;
			    		totalBenefitTicket = totalBenefitTicket.add(suborderitem.getBenefitAmount());
					}
				}
			    if(suborder.getStatus()== 3 || suborder.getStatus()== 5 
						 || suborder.getStatus()== 11  || suborder.getStatus()== 12 
						 || suborder.getStatus()== -11  || suborder.getStatus()== -12 
						 || suborder.getStatus()== 13  || suborder.getStatus()== 15 
						 || suborder.getStatus()== 14  || suborder.getStatus()== 16 ){
			    	isAftermarketOrder = true;
			    }
				/*if(suborder.getStatus()== 3 || suborder.getStatus()== 5 
						 || suborder.getStatus()== 11  || suborder.getStatus()== 12 
						 || suborder.getStatus()== -11  || suborder.getStatus()== -12 ){
					mv.setViewName("product/order/newrefundapply");
					if(suborder.getRefundOrderId() != null) {
						refundorder = refundorderService.getRefundorById(suborder.getRefundOrderId());
						
					}
					if(suborder.getReturnOrderId() != null) {
						returnorder = returnorderService.returnOrderById(suborder.getReturnOrderId());
					}
				}*/
				
				// 获取用户昵称
				UserFactory user = userService.getById(suborder.getOrders().getUserId());
				if(user != null) {
					mv.addObject("user_nickName", user.getNickName());
				}
				
				String noto = suborder.getNoto();
				if (StringUtils.isNotEmpty(noto)&& noto!=null) {
					mv.addObject("note", noto);
				}else{
					mv.addObject("note", suborder.getOrders().getNote());
				}
			}
			Map<String,Object> returnedMap = new HashMap<String,Object>();
			returnedMap.put("supplierId", userModel.getSupplierId());
			returnedMap.put("returned", 1);
			List<SupplierAddress> returnedlist = supplierAddressService.fetchSupplierAddress(returnedMap);
			SupplierAddress supplierAddressReturned = null;
			if(returnedlist!=null&&returnedlist.size()>0){
				supplierAddressReturned = returnedlist.get(0);
			}
			Map<String,Object> sendMap = new HashMap<String,Object>();
			sendMap.put("supplierId", userModel.getSupplierId());
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
						mv.addObject("compInfo", ci);
						mv.addObject("expressCom", ci.getPinYin());
					    mv.addObject("searchId", userModel.getId());
				    }
				}
			}
			mv.addObject("supplierAddressSend", supplierAddressSend);
			mv.addObject("supplierAddressReturned", supplierAddressReturned);
			mv.addObject("suborder", suborder);
			mv.addObject("isAftermarketOrder", isAftermarketOrder);
			mv.addObject("flag", flag);
			mv.addObject("totalBenefitTicket", totalBenefitTicket);
			//mv.addObject("returnorder", returnorder);
			//mv.addObject("refundorder", refundorder);
			mv.addObject("commission", commission);
			////商品实体类
		}
		return mv;
	}
	
	/**
	 * ajax修改SubOrder
	 **/
	@RequestMapping(value="ajaxUpdateOrder")
	@Token(remove=true)
	public ModelAndView ajaxUpdateOrder(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			
			String subOrderId =  request.getParameter("subOrderId");
			String name =  request.getParameter("name");
			String mobile =  request.getParameter("mobile");
			String address =  request.getParameter("address");
			Suborder suborder= suborderService.getById(subOrderId);
			Orders orders = ordersService.getById(suborder.getOrderId());
			orders.setName(name);
			orders.setMobile(mobile);
			orders.setAddress(address);
			ordersService.saveOrUpdate(orders);
			result.setErrorCode("0");
		}
		mv.addObject("result", result);
		return mv;
	}


	@RequestMapping(value = "exportExcel")
	@ResponseBody
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		
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
		map.put("supplierId", userModel.getSupplierId());
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

		if(StringUtils.isEmpty(type)){
			type ="1";
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

		map.put("startnum", 0);
		map.put("size",1000);
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("订单一览"); 
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        
        /**
         * 设置样式 start
         * */
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        /**设置样式  end
         * */
        List<String> headers= new ArrayList<String>();
        headers.add("订单编号");
        headers.add("成交时间");
        headers.add("商品");
        headers.add("规格");
        headers.add("条码");
        headers.add("单价");
        headers.add("数量");
        headers.add("售后");
        headers.add("买家姓名");
        headers.add("买家电话");
        headers.add("买家地址 省");
        headers.add("市");
        headers.add("区");
        headers.add("详细");
        headers.add("买家留言");
        headers.add("交易状态");
        headers.add("实收款");
        headers.add("含快递");
        headers.add("对账单状态");
        headers.add("对账单");
                
        /**
         * 
         * 设置订单详情表头 start
         * */
        HSSFRow row = sheet.createRow((int) 0); 
        for (int i = 0; i < headers.size(); i++) {
        	HSSFCell cell = row.createCell(i);
            //设置值
            cell.setCellValue(headers.get(i));  
            //设置样式
            cell.setCellStyle(style);
		}
        /** 设置订单详情表头 end
         * */
        int currentRow = 0;
        
		List<Suborder> list = suborderService.findSelllistPage(map);

		for(int i=0;i<list.size();i++){
			if(list.get(i).getSuborderitemlist()!=null&&list.get(i).getSuborderitemlist().size()>0){
				int pi=-1;
				for(Suborderitem si :list.get(i).getSuborderitemlist()){
					if(si.getItemValues()!=null&&!si.getItemValues().equals("")){
						String values = si.getItemValues();
						String valuesNew = "";
						Map valuesMap = JsonUtil.getMap4Json(values);
						 Iterator<Map.Entry<Integer, String>> iterator = valuesMap.entrySet().iterator();  
				        while (iterator.hasNext()) {  
				            Map.Entry<Integer, String> entry = iterator.next();  
				            valuesNew +=entry.getKey()+" : "+entry.getValue();
				        }  
				        si.setItemValues(valuesNew);
				        

						currentRow++;
						int col=0;
			            row = sheet.createRow(currentRow); 
			            //headers.add("订单编号");
			            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(list.get(i).getSubOrderId());
			            //headers.add("成交时间");
			            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(TimeUtil.dateToStr(list.get(i).getCreateTime(),"yyyy-MM-dd"));
			            //headers.add("商品");
			            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(si.getProductName());
			            //headers.add("规格");
			            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(si.getItemValues());
			            //headers.add("条码");
			            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(si.getProductCode());
			            //headers.add("单价");
			            row.createCell(col++).setCellValue(si.getPrice().setScale(2, BigDecimal.ROUND_DOWN) + "");
			            //headers.add("数量");
			            row.createCell(col++).setCellValue(si.getNumber() + "");
			            //headers.add("售后");
			            if(list.get(i).getStatus()==3 || list.get(i).getStatus()==5 ) {
				            row.createCell(col++).setCellValue("退款申请中");
			            } else if(list.get(i).getStatus()==11 || list.get(i).getStatus()==12 ) {
				            row.createCell(col++).setCellValue("已退款");			            	
			            } else {
				            row.createCell(col++).setCellValue("");
			            }
			            //headers.add("联系人");
			            row.createCell(col++).setCellValue(list.get(i).getOrders().getName());
			            //headers.add("买家电话");
			            row.createCell(col++).setCellValue(list.get(i).getOrders().getMobile());
			            //headers.add("买家地址");
			            String addr = list.get(i).getOrders().getAddress();
			            String[] addrs = null;
			            if(addr != null) {
			            	addrs=addr.split(" ");
			            	
			            	if(addrs.length==1) {
			            		addrs=fromatAddr(addr);
			            	}
			            }
			            if(addrs !=null && addrs.length>0) {
			            	row.createCell(col++).setCellValue(addrs[0]);
			            } else {
			            	row.createCell(col++).setCellValue(addr);
			            }
			            if(addrs !=null && addrs.length>1) {
			            	row.createCell(col++).setCellValue(addrs[1]);
			            } else {
			            	row.createCell(col++).setCellValue("");
			            }
			            if(addrs !=null && addrs.length>2) {
			            	row.createCell(col++).setCellValue(addrs[2]);
			            } else {
			            	row.createCell(col++).setCellValue("");
			            }
			            if(addrs !=null && addrs.length>3) {
			            	row.createCell(col++).setCellValue(addrs[3]);
			            } else {
			            	row.createCell(col++).setCellValue("");
			            }
			            //headers.add("买家留言");
		            	row.createCell(col++).setCellValue(list.get(i).getOrders().getNote());
			            //headers.add("交易状态");
			            if(list.get(i).getStatus()==0 ) {
				            row.createCell(col++).setCellValue("待付款");
			            } else if(list.get(i).getStatus()==1 ) {
				            row.createCell(col++).setCellValue("待发货");		
			            } else if(list.get(i).getStatus()==2 ) {
				            row.createCell(col++).setCellValue("待收货");		
			            } else if(list.get(i).getStatus()==3 || list.get(i).getStatus()==5 ) {
				            row.createCell(col++).setCellValue("退款申请中");	
			            } else if(list.get(i).getStatus()==11 || list.get(i).getStatus()==12 ) {
				            row.createCell(col++).setCellValue("已退款");	
			            } else if(list.get(i).getStatus()==4 ) {
			            	if(list.get(i).getCommentStatus() == null || list.get(i).getCommentStatus()==0) {
					            row.createCell(col++).setCellValue("待评价");
			            	} else {
					            row.createCell(col++).setCellValue("已评价");
			            	}
			            } else if(list.get(i).getStatus()==-1 ) {
				            row.createCell(col++).setCellValue("已关闭");	
			            } else if(list.get(i).getStatus()==-11 || list.get(i).getStatus()==-12 ) {
				            row.createCell(col++).setCellValue("退款失败");	
			            } else {
				            row.createCell(col++).setCellValue("");
			            }
			            //headers.add("实收款");
			            //headers.add("快递");
			            if(pi!=i){
			            	row.createCell(col++).setCellValue(list.get(i).getRealPrice()==null?"":list.get(i).getRealPrice().setScale(2, BigDecimal.ROUND_DOWN) + "");
			            	row.createCell(col++).setCellValue(list.get(i).getTotalShipping()==null?"":list.get(i).getTotalShipping().setScale(2, BigDecimal.ROUND_DOWN) + "");
			            	pi=i;
			            } else {
				            row.createCell(col++).setCellValue("");
				            row.createCell(col++).setCellValue("");
			            }
			            //headers.add("对账单状态");
			            if("2".equals(list.get(i).getCloseFlg())) {
				            row.createCell(col++).setCellValue("已出账");			            	
			            } else {
				            row.createCell(col++).setCellValue("");
			            }
			            //headers.add("对账单");
			            if("2".equals(list.get(i).getCloseFlg())) {
				            row.createCell(col++).setCellValue(list.get(i).getBillId());			            	
			            } else {
				            row.createCell(col++).setCellValue("");
			            }
					}
				}
			}
		}
				
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ getFileNameForSave(request));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		
		try {
			wb.write(response.getOutputStream());
			wb.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	private String[] fromatAddr(String addr) {
	
		//省
		String pro = "";
		if(addr.startsWith("北京市")) {
			pro= "北京市";
		} else if(addr.startsWith("天津市")) {
			pro= "天津市";
		} else if(addr.startsWith("上海市")) {
			pro= "上海市";
		} else if(addr.startsWith("重庆市")) {
			pro= "重庆市";
		} else {
			int p = addr.indexOf("自治区");
			if(p>-1) {
				pro = addr.substring(0, p+3);
			} else {
				pro = addr.substring(0, addr.indexOf("省")+1);
			}
		}
		addr = addr.substring(pro.length());
		
		//市
		String city = "";
		if(addr.startsWith("县")) {
			city="县";
		} else if(addr.startsWith("市辖区")) {
			city= "市辖区";
		} else {
			city = addr.substring(0, addr.indexOf("市")+1);
		}
		addr = addr.substring(city.length());
		
		//县、区
		String dept = "";
		int p = addr.indexOf("县");
		if(p>-1) {
			dept = addr.substring(0, p+1);
		} else {
			dept = addr.substring(0, addr.indexOf("区")+1);
		}
		addr = addr.substring(dept.length());
		
		return (pro+" "+city+" "+dept+" "+addr).split(" ");
	}

	private String getFileNameForSave(HttpServletRequest request) throws UnsupportedEncodingException {

		String userAgent = request.getHeader("user-agent");
		String filename = "订单一览"+ TimeUtil.dateToStr(new Date(),"_yyyyMMdd") +".xls";
		String new_filename = java.net.URLEncoder.encode(filename, "UTF-8");
		// 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
		String rtn = "filename=\"" + new_filename + "\"";
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			// IE浏览器，只能采用URLEncoder编码
			if (userAgent.indexOf("msie") != -1) {
				rtn = "filename=\"" + new_filename + "\"";
			}
			// Opera浏览器只能采用filename*
			else if (userAgent.indexOf("opera") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
			// Safari浏览器，只能采用ISO编码的中文输出
			else if (userAgent.indexOf("safari") != -1) {
				rtn = "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
			}
			// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
			else if (userAgent.indexOf("applewebkit") != -1) {
				new_filename = MimeUtility.encodeText(filename, "UTF8", "B");
				rtn = "filename=\"" + new_filename + "\"";
			}
			// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
			else if (userAgent.indexOf("mozilla") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
		}
		return rtn;
	}
	/**
	 * to订单详情
	 **/
	@RequestMapping(value="toAftermarketOrderDetail",method=RequestMethod.GET)
	public ModelAndView toAftermarketOrderDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			mv.setViewName("product/order/newrefundapply");
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			String subOrderId = request.getParameter("subOrderId");
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("subOrderId",subOrderId);
			map.put("supplierId",supplier.getId());
			
			List<Suborder> suborderlist =  suborderService.findSuborderlistPage(map);
			Suborder suborder = null;
			Boolean isReturnOrder = false;//标示是否为退货退款单
			Boolean isAftermarketOrder = false;//标示是否为售后
			for(int i=0;i<suborderlist.size();i++){
				suborder = suborderlist.get(i);
				if(suborder.getStatus()== 3 || suborder.getStatus()== 5 
						 || suborder.getStatus()== 11  || suborder.getStatus()== 12 
						 || suborder.getStatus()== -11  || suborder.getStatus()== -12 ||suborder.getStatus()>12 ){
					isAftermarketOrder =true;
				}else{//跳转订单列表页
					mv.setViewName("redirect:/suborder/gotoSelllist.html");
					logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
				}
				if(isAftermarketOrder && suborder.getSuborderitemlist()!=null && suborder.getSuborderitemlist().size()>0){
					for(Suborderitem si :suborder.getSuborderitemlist()){
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
			Returnorder returnorder = new Returnorder();
			Refundorder refundorder = null;
			Boolean flag = false;
			BigDecimal totalBenefitTicket =new BigDecimal("0");
			if(suborderlist.size()>0){
				suborder = suborderlist.get(0);
			    for (Suborderitem suborderitem : suborder.getSuborderitemlist()) {
			    	if ((suborderitem.getBenefitType() !=null) && (suborderitem.getBenefitType()==3)) {//换领
			    		flag=true;
			    		totalBenefitTicket = totalBenefitTicket.add(suborderitem.getBenefitAmount());
					}
				}
				if(isAftermarketOrder){//是售后订单
					
					if(suborder.getRefundOrderId() != null) {
						refundorder = refundorderService.getRefundorById(suborder.getRefundOrderId());
					}
					if(suborder.getReturnOrderId() != null) {
						returnorder = returnorderService.returnOrderById(suborder.getReturnOrderId());
						if(returnorder!=null){
							isReturnOrder =true;
							if(null==returnorder.getStatus()){
								if(suborder.getStatus()== 3 ){
									returnorder.setStatus(0);
								}else if(suborder.getStatus()==11){
									returnorder.setStatus(5);
								}else if(suborder.getStatus()==-11){
									returnorder.setStatus(1);
								}
							}
						}
					}
				}
				String noto = suborder.getNoto();
				if (StringUtils.isNotEmpty(noto)&& noto!=null) {
					mv.addObject("note", noto);
				}else{
					mv.addObject("note", suborder.getOrders().getNote());
				}
			}
			Map<String,Object> returnedMap = new HashMap<String,Object>();
			returnedMap.put("supplierId", userModel.getSupplierId());
			returnedMap.put("returned", 1);
			List<SupplierAddress> returnedlist = supplierAddressService.fetchSupplierAddress(returnedMap);
			SupplierAddress supplierAddressReturned = null;
			if(returnedlist!=null&&returnedlist.size()>0){
				supplierAddressReturned = returnedlist.get(0);
			}
			// 获取物流信息
			if(isReturnOrder && returnorder.getExpressType() != null && !("").equals(returnorder.getExpressType())) {
			    if(!StringUtils.isEmpty(returnorder.getExpressType())){
				    ExpressCompany ci = expressComService.getExpressComById(returnorder.getExpressType());
				    if(ci!=null) {
						mv.addObject("compInfo", ci);
						mv.addObject("expressCom", ci.getPinYin());
					    mv.addObject("searchId", returnorder.getUserId());
				    }
				}
			}
			mv.addObject("supplierAddressReturned", supplierAddressReturned);
			mv.addObject("suborder", suborder);
			mv.addObject("flag", flag);
			mv.addObject("isReturnOrder", isReturnOrder);
			mv.addObject("totalBenefitTicket", totalBenefitTicket);
			mv.addObject("returnorder", returnorder);
			mv.addObject("refundorder", refundorder);
		}
		return mv;
	}
}

