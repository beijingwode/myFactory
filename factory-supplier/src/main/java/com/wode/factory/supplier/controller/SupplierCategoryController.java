/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.util.HashMap;
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
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Supplier;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierCategoryService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.util.UserInterceptor;

@Controller
@RequestMapping("supplierCategory")
public class SupplierCategoryController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("supplierCategoryService")
	private SupplierCategoryService supplierCategoryService;
	
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	
	
	public SupplierCategoryController() {
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	
	/**
	 *删除对象
	 **/
	@RequestMapping(value="deletebymap",method=RequestMethod.GET)
	public ModelAndView deletebymap(HttpServletRequest request,HttpServletResponse response) {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		Result result = new Result();
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			Map<String,Object> delMap = new HashMap<String,Object>();
			delMap.put("supplierId", supplier.getId());
			if(StringUtils.isNullOrEmpty(id)||StringUtils.isNullOrEmpty(type)){
				id = "0";
				type = "3";
			}
			if("1".equals(type)){
				delMap.put("categoryParentParentid", new Long(id));
			}else if("2".equals(type)){
				delMap.put("categoryParentid", new Long(id));
			}else if("3".equals(type)){
				delMap.put("categoryId", new Long(id));
			}
			supplierCategoryService.deletebymap(delMap);
			mv.setViewName("redirect:/supplier/torecruitmenttype.html");
		}
		
		return mv;
	}
}

