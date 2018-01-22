/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.util.List;

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
import com.wode.common.stereotype.Token;
import com.wode.factory.model.ProductAttribute;
import com.wode.factory.supplier.service.ProductAttributeService;

@Controller
@RequestMapping("productAttribute")
public class ProductAttributeController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("productAttributeService")
	private ProductAttributeService productAttributeService;
	
	private final String LIST_ACTION = "redirect:/productAttribute/list.html";
	
	public ProductAttributeController() {
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	
	
	/** 
	 * 查看对象
	 **/
	@RequestMapping(value="show",method=RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		ProductAttribute productAttribute = (ProductAttribute)productAttributeService.getById(id);
		return new ModelAndView("product/productAttribute/show","productAttribute",productAttribute);
	}
	
	/** 
	 * 进入新增页面
	 **/
	@RequestMapping(value="create",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView create(HttpServletRequest request,HttpServletResponse response,ProductAttribute productAttribute) throws Exception {
		return new ModelAndView("product/productAttribute/create","productAttribute",productAttribute);
	}
	
	/** 
	 * 保存新增对象
	 **/
	@RequestMapping(value="save",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response,ProductAttribute productAttribute) throws Exception {
		productAttributeService.save(productAttribute);		
		return new ModelAndView(LIST_ACTION);
	}
	
	/**
	 * 进入更新页面
	 **/
	@RequestMapping(value="edit",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		ProductAttribute productAttribute = (ProductAttribute)productAttributeService.getById(id);
		return new ModelAndView("product/productAttribute/edit","productAttribute",productAttribute);
	}
	
	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView update(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		
		ProductAttribute productAttribute = (ProductAttribute)productAttributeService.getById(id);
		bind(request,productAttribute);
		productAttributeService.update(productAttribute);
		return new ModelAndView(LIST_ACTION);
	}
	
	/**
	 *删除对象
	 **/
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) {
		//删除一个时删掉下面的
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		productAttributeService.removeById(id);
		return new ModelAndView(LIST_ACTION);
	}
	
	
	/**
	 * 根据类型id获取该类型所有的属性及其属性值
	 */
	@RequestMapping(value="getAttributelistByTypeid")
	public ModelAndView getAttributelistByTypeid(HttpServletRequest request,HttpServletResponse response) {
		String typeid=request.getParameter("typeid");
		List<ProductAttribute> list = productAttributeService.findAll();
		
		ModelAndView result = new ModelAndView("product/productAttribute/list");
		return result;
	}
}

