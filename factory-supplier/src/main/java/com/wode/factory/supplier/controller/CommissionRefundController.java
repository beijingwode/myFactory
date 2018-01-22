/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.result.Result;
import com.wode.common.stereotype.Token;
import com.wode.factory.model.CommissionRefund;
import com.wode.factory.model.CommissionRefundDetail;
import com.wode.factory.model.Supplier;
import com.wode.factory.supplier.query.CommissionRefundQuery;
import com.wode.factory.supplier.service.CommissionRefundDetailService;
import com.wode.factory.supplier.service.CommissionRefundService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.util.UserInterceptor;

import cn.org.rapid_framework.page.Page;

@Controller
@RequestMapping("commissionRefund")
public class CommissionRefundController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("commissionRefundService")
	private CommissionRefundService commissionRefundService;
	
	@Autowired
	@Qualifier("commissionRefundDetailService")
	private CommissionRefundDetailService commissionRefundDetailService;
	
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	private final String LIST_ACTION = "redirect:/commissionRefund/list.html";

	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	public CommissionRefundController() {
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	/** 
	 * 执行搜索 
	 **/
	@RequestMapping(value="list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,CommissionRefundQuery query) {
		Page page = this.commissionRefundService.findPage(query);
		
		ModelAndView result = new ModelAndView("product/commissionRefund/list");
		result.addAllObjects(toModelMap(page, query));
		return result;
	}
	
	/** 
	 * 查看对象
	 **/
	@RequestMapping(value="show",method=RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		CommissionRefund commissionRefund = (CommissionRefund)commissionRefundService.getById(id);
		return new ModelAndView("product/commissionRefund/show","commissionRefund",commissionRefund);
	}
	
	/** 
	 * 进入新增页面
	 **/
	@RequestMapping(value="create",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView create(HttpServletRequest request,HttpServletResponse response,CommissionRefund commissionRefund) throws Exception {
		return new ModelAndView("product/commissionRefund/create","commissionRefund",commissionRefund);
	}
	
	/** 
	 * 保存新增对象
	 **/
	@RequestMapping(value="save",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response,CommissionRefund commissionRefund) throws Exception {
		commissionRefundService.save(commissionRefund);		
		return new ModelAndView(LIST_ACTION);
	}
	
	/**
	 * 进入更新页面
	 **/
	@RequestMapping(value="edit",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		CommissionRefund commissionRefund = (CommissionRefund)commissionRefundService.getById(id);
		return new ModelAndView("product/commissionRefund/edit","commissionRefund",commissionRefund);
	}
	
	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView update(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		
		CommissionRefund commissionRefund = (CommissionRefund)commissionRefundService.getById(id);
		bind(request,commissionRefund);
		commissionRefundService.update(commissionRefund);		
		return new ModelAndView(LIST_ACTION);
	}
	
	/**
	 *删除对象
	 **/
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) {
		//删除一个时删掉下面的
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		commissionRefundService.removeById(id);
		return new ModelAndView(LIST_ACTION);
		
/*		//删除多个
		String[] items = request.getParameterValues("items");
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			
			java.lang.Long id = new java.lang.Long((String)params.get("id"));
			
			commissionRefundService.removeById(id);
		}
		return new ModelAndView(LIST_ACTION);*/
	}
	
	
	/**
	 *对账单list
	 **/
	@RequestMapping(value="gotoCommissionRefundList",method=RequestMethod.GET)
	@Token(remove=true)
	public ModelAndView gotoProductlist(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return commissionRefundList(request,response);
	}
	
	/**
	 * 对账单list
	 **/
	@RequestMapping(value="findCommissionRefundList",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView findProductlistPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return commissionRefundList(request,response);
	}
	
	
	/**
	 * 对账单list
	 **/
	public ModelAndView commissionRefundList(HttpServletRequest request,HttpServletResponse response) throws Exception {
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
			mv.setViewName("product/salebill/commissionrefundlist");
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
	
			
			Map map = new HashMap();

			mv.addObject("pages",page);
			mv.addObject("sizes",size);
			map.put("supplierId",supplier.getId());
			Integer total = commissionRefundService.findlistPageCount(map);
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
				map.put("sortColumns", "create_time");
				List<CommissionRefund> list = commissionRefundService.findlistPage(map);
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
	 * 对账单list
	 **/
	@RequestMapping(value="toCommissionRefundView",method=RequestMethod.GET)
	public ModelAndView toCommissionRefundView(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel =UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
		    String commissionRefundId = request.getParameter("commissionRefundId");
			mv.setViewName("product/salebill/commissionrefundview");
			Map map =new HashMap();
			map.put("commissionRefundId", new Long(commissionRefundId));

//			String pages = request.getParameter("pages");
//			String sizes = request.getParameter("sizes");
//			Integer page=1;
//			Integer size=10;
//			
//			if(pages==null||pages.equals("")){
//				pages = "1";
//			}
//			page = new Integer(pages);
//			
//			if(sizes == null || sizes.equals("")){
//				sizes="10";
//			}
//			
//			size= new Integer(sizes);
//	
//			if(size>100){
//				size=100;
//			}
	
			String supplierId = request.getParameter("supplierId");
			
			
//			mv.addObject("pages",page);
//			mv.addObject("sizes",size);
			
//			Integer total = commissionRefundDetailService.findlistPageCount(map);
////			Integer startnum=(page-1)*size;
//			if(total>0){
//				if(total<startnum){
//					startnum=total-size;
//				}
//				if(startnum<0){
//					startnum = 0;
//				}
//				map.put("startnum", startnum);
//				map.put("size",size);
				map.put("sortColumns", "create_time");
				List<CommissionRefundDetail> list = commissionRefundDetailService.findlistPage(map);
//				result.setPage(page);
//				result.setSize(size);
//				result.setTotal(total);
				result.setErrorCode("0");
				result.setMsgBody(list);
//			}else{
//				result.setErrorCode("1000");
//			}
			mv.addObject("result",result);
		}
		
		return mv;
	}

	
}

