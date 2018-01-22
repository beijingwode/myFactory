/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.constant.UserConstant;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.result.Result;
import com.wode.common.stereotype.Token;
import com.wode.factory.model.SaleDurationParam;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierDuration;
import com.wode.factory.supplier.query.SupplierDurationQuery;
import com.wode.factory.supplier.service.SaleDurationParamService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierDurationService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.util.UserInterceptor;

import cn.org.rapid_framework.page.Page;

@Controller
@RequestMapping("supplierDuration")
public class SupplierDurationController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("supplierDurationService")
	private SupplierDurationService supplierDurationService;
	
	@Autowired
	@Qualifier("saleDurationParamService")
	private SaleDurationParamService saleDurationParamService;
	
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	
	private final String LIST_ACTION = "redirect:/supplierDuration/list.html";
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public SupplierDurationController() {
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
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,SupplierDurationQuery query) {
		Page page = this.supplierDurationService.findPage(query);
		
		ModelAndView result = new ModelAndView("product/supplierDuration/list");
		result.addAllObjects(toModelMap(page, query));
		return result;
	}
	
	/** 
	 * 查看对象
	 **/
	@RequestMapping(value="show",method=RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		SupplierDuration supplierDuration = (SupplierDuration)supplierDurationService.getById(id);
		return new ModelAndView("product/supplierDuration/show","supplierDuration",supplierDuration);
	}
	
	/** 
	 * 进入新增页面
	 **/
	@RequestMapping(value="create",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView create(HttpServletRequest request,HttpServletResponse response,SupplierDuration supplierDuration) throws Exception {
		return new ModelAndView("product/supplierDuration/create","supplierDuration",supplierDuration);
	}
	
	/** 
	 * 保存新增对象
	 **/
	@RequestMapping(value="save",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response,SupplierDuration supplierDuration) throws Exception {
		supplierDurationService.save(supplierDuration);		
		return new ModelAndView(LIST_ACTION);
	}
	
	/**
	 * 进入更新页面
	 **/
	@RequestMapping(value="edit",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		SupplierDuration supplierDuration = (SupplierDuration)supplierDurationService.getById(id);
		return new ModelAndView("product/supplierDuration/edit","supplierDuration",supplierDuration);
	}
	
	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView update(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		
		SupplierDuration supplierDuration = (SupplierDuration)supplierDurationService.getById(id);
		bind(request,supplierDuration);
		supplierDurationService.update(supplierDuration);		
		return new ModelAndView(LIST_ACTION);
	}
	
	/**
	 *删除对象
	 **/
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) {
		//删除一个时删掉下面的
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		supplierDurationService.removeById(id);
		return new ModelAndView(LIST_ACTION);
		
/*		//删除多个
		String[] items = request.getParameterValues("items");
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			
			java.lang.Long id = new java.lang.Long((String)params.get("id"));
			
			supplierDurationService.removeById(id);
		}
		return new ModelAndView(LIST_ACTION);*/
	}
	
	
	
	
	
	
	/** 
	 * 查看对象
	 **/
	@RequestMapping(value="toDurationSet")
	public ModelAndView toDurationSet(HttpServletRequest request,HttpServletResponse response) throws Exception {
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
			if(supplier.getStatus()==null||supplier.getStatus()<2){
				result.setErrorCode("10000");
				mv.setViewName("redirect:/product/toNotthroughaudit.html");
				logger.error("商户入驻信息还未通过审核   errorcode：10000" );
			}else{
				mv.setViewName("product/salebill/durationSet");
				SupplierDuration supplierDuration = (SupplierDuration)supplierDurationService.getBySupplierId(supplier.getId());
				mv.addObject("supplierDuration", supplierDuration);
				String startTime = null;
				if(supplierDuration!=null){
					startTime = format.format(supplierDuration.getStartTime());
				}else{
					startTime = "";
				}
				
				
				mv.addObject("startTime", startTime);
				List<SaleDurationParam> saleDurationParamlist = saleDurationParamService.findAll();
				mv.addObject("saleDurationParamlist", saleDurationParamlist);
			}
		}
		
		return mv;
	}
	
	/** 
	 * 查看对象
	 **/
	@RequestMapping(value="durationSet")
	public ModelAndView durationSet(HttpServletRequest request,HttpServletResponse response) throws Exception {
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
			mv.setViewName("redirect:/supplierDuration/toDurationSet.html");
			String idTemp = request.getParameter("id");
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			Long id =0l;
			if(idTemp!=null&&!idTemp.equals("")){
				id = new Long(idTemp);
			}
			String saleDurationKey = request.getParameter("saleDurationKey");
			String startTime =request.getParameter("startTime");
			SupplierDuration supplierDuration = (SupplierDuration)supplierDurationService.getBySupplierId(supplier.getId());
			if(supplierDuration==null){
				supplierDuration = new SupplierDuration();
			}
			supplierDuration.setSaleDurationKey(saleDurationKey);
			supplierDuration.setStartTime(format.parse(startTime+" 00:00:00"));
			supplierDuration.setSupplierId(supplier.getId());
			supplierDuration.setCreateTime(new Date());
			supplierDuration.setCreateUserid(userModel.getId());
			supplierDurationService.saveOrUpdate(supplierDuration);
			mv.addObject("supplierDuration", supplierDuration);
		}
		
		return mv;
	}
	
}

