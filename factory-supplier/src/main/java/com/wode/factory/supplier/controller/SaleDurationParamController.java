/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.util.Hashtable;

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

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.stereotype.Token;
import com.wode.factory.model.SaleDurationParam;
import com.wode.factory.supplier.query.SaleDurationParamQuery;
import com.wode.factory.supplier.service.SaleDurationParamService;

@Controller
@RequestMapping("saleDurationParam")
public class SaleDurationParamController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("saleDurationParamService")
	private SaleDurationParamService saleDurationParamService;
	
	private final String LIST_ACTION = "redirect:/saleDurationParam/list.html";
	
	public SaleDurationParamController() {
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
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,SaleDurationParamQuery query) {
		Page page = this.saleDurationParamService.findPage(query);
		
		ModelAndView result = new ModelAndView("product/saleDurationParam/list");
		result.addAllObjects(toModelMap(page, query));
		return result;
	}
	
	/** 
	 * 查看对象
	 **/
	@RequestMapping(value="show",method=RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		SaleDurationParam saleDurationParam = (SaleDurationParam)saleDurationParamService.getById(id);
		return new ModelAndView("product/saleDurationParam/show","saleDurationParam",saleDurationParam);
	}
	
	/** 
	 * 进入新增页面
	 **/
	@RequestMapping(value="create",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView create(HttpServletRequest request,HttpServletResponse response,SaleDurationParam saleDurationParam) throws Exception {
		return new ModelAndView("product/saleDurationParam/create","saleDurationParam",saleDurationParam);
	}
	
	/** 
	 * 保存新增对象
	 **/
	@RequestMapping(value="save",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response,SaleDurationParam saleDurationParam) throws Exception {
		saleDurationParamService.save(saleDurationParam);		
		return new ModelAndView(LIST_ACTION);
	}
	
	/**
	 * 进入更新页面
	 **/
	@RequestMapping(value="edit",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		SaleDurationParam saleDurationParam = (SaleDurationParam)saleDurationParamService.getById(id);
		return new ModelAndView("product/saleDurationParam/edit","saleDurationParam",saleDurationParam);
	}
	
	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView update(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		
		SaleDurationParam saleDurationParam = (SaleDurationParam)saleDurationParamService.getById(id);
		bind(request,saleDurationParam);
		saleDurationParamService.update(saleDurationParam);		
		return new ModelAndView(LIST_ACTION);
	}
	
	/**
	 *删除对象
	 **/
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) {
		//删除一个时删掉下面的
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		saleDurationParamService.removeById(id);
		return new ModelAndView(LIST_ACTION);
		
/*		//删除多个
		String[] items = request.getParameterValues("items");
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			
			java.lang.Integer id = new java.lang.Integer((String)params.get("id"));
			
			saleDurationParamService.removeById(id);
		}
		return new ModelAndView(LIST_ACTION);*/
	}
	
}

