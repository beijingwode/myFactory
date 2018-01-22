/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.wode.factory.supplier.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.SupplierImageResource;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.query.SupplierImageResourceQuery;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierImageResourceService;
import com.wode.factory.supplier.util.UserInterceptor;

@Controller
@RequestMapping("supplierImageResource")
public class SupplierImageResourceController extends BaseSpringController {
	@Autowired
	@Qualifier("supplierImageResourceService")
	private SupplierImageResourceService supplierImageResourceService;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;

	/**
	 * 添加详情图片
	 * @param request
	 * @param imageResource
	 * @return
	 */
	@RequestMapping("addImageResource")
	@ResponseBody
	public ActResult<Object> addImageResource(HttpServletRequest request,SupplierImageResource imageResource){
		if(StringUtils.isNullOrEmpty(imageResource))
			return ActResult.fail("上传图片为空");
			
		UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us==null){
			return ActResult.fail("用户未登录");
		}
		
		imageResource.setSupplierId(us.getSupplierId());
		imageResource.setDate(new Date());
		imageResource.setYaer(imageResource.getDate().getYear());//年
		imageResource.setCreateTime(imageResource.getDate());
		this.supplierImageResourceService.save(imageResource);
		return ActResult.success("保存成功");
	}
	/**
	 * 分页查询
	 * @param request
	 * @param imageResourceQuery
	 * @return
	 */
	@RequestMapping("fetchImageResource")
	public ModelAndView fetchImageResource(HttpServletRequest request,ModelAndView model,SupplierImageResourceQuery imageResourceQuery){
		UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us==null){
			model.setViewName("redirect:/user/login.html");
		}
		
		imageResourceQuery.setSupplierId(us.getSupplierId());
		imageResourceQuery.setPageSizeNoMax(3);
		PageInfo<SupplierImageResourceQuery> page = this.supplierImageResourceService.findDateGroupBy(imageResourceQuery);
		
		if(StringUtils.isNullOrEmpty(page.getList())){
			model.addObject("image", new HashMap<String, List<SupplierImageResource>>());
		}else{
			model.addObject("image",this.supplierImageResourceService.findPageImage(page.getList()));
		}
		model.addObject("newDate", new SimpleDateFormat("yyyy年MM月dd").format(new Date()));
		model.addObject("page", page);
		model.addObject("query", imageResourceQuery);
		model.setViewName("product/product/details_pic");
		return model;
	}
}
