/*
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.wode.factory.supplier.controller;

import java.util.Date;

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
import com.wode.factory.model.SupplierSkuImage;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.query.SupplierSkuImageQuery;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierSkuImageService;
import com.wode.factory.supplier.util.UserInterceptor;

@Controller
@RequestMapping("supplierSkuImage")
public class SupplierSkuImageController extends BaseSpringController{
	@Autowired
	@Qualifier("supplierSkuImageService")
	private SupplierSkuImageService supplierSkuImageService;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	
	/**
	 * 添加sku主图
	 * @param request
	 * @param skuImage
	 * @return
	 */
	@RequestMapping("addSkuImage")
	@ResponseBody
	public ActResult<Object> addSkuImage(HttpServletRequest request,SupplierSkuImage skuImage){
		if(StringUtils.isNullOrEmpty(skuImage))
			return ActResult.fail("上传图片为空");
		
		if(StringUtils.isNullOrEmpty(skuImage.getImage1())||StringUtils.isNullOrEmpty(skuImage.getImage2())||StringUtils.isNullOrEmpty(skuImage.getImage3())||StringUtils.isNullOrEmpty(skuImage.getImage4())||StringUtils.isNullOrEmpty(skuImage.getImage5()))
			return ActResult.fail("上传图片数量错误");
		
		UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us==null){
			return ActResult.fail("用户未登录");
		}
		skuImage.setCreateTime(new Date());
		skuImage.setSupplierId(us.getSupplierId());
		this.supplierSkuImageService.save(skuImage);
		
		return ActResult.success("上传成功");
	}
	/**
	 * 删除sku图片
	 * @param request
	 * @param skuImageId
	 * @return
	 */
	@RequestMapping("delSkuImage")
	@ResponseBody
	public ActResult<Object> delSkuImage(HttpServletRequest request,Long skuImageId){
		if(StringUtils.isNullOrEmpty(skuImageId))
			return ActResult.fail("参数为空");
		
		UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us==null){
			return ActResult.fail("用户未登录");
		}
		//根据id查询图片信息
		SupplierSkuImage skuImage = this.supplierSkuImageService.getById(skuImageId);
		if(StringUtils.isNullOrEmpty(skuImage)){
			return ActResult.fail("您删除的图片不存在");
		}else{
			if(skuImage.getSupplierId().equals(us.getSupplierId())){
				//删除
				this.supplierSkuImageService.removeById(skuImageId);
				return ActResult.success("删除成功");
			}else{
				return ActResult.fail("删除失败");
			}
		}
	}
	
	/**
	 * 分页查询sku图片
	 * @param request
	 * @param model
	 * @param skuImageQuery
	 * @return
	 */
	@RequestMapping("fetchSkuImage")
	public ModelAndView delSkuImage(HttpServletRequest request,ModelAndView model,SupplierSkuImageQuery skuImageQuery){
		UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us==null){
			model.setViewName("redirect:/user/login.html");
		}else{
			skuImageQuery.setSupplierId(us.getSupplierId());
			PageInfo<SupplierSkuImageQuery> page = this.supplierSkuImageService.selectPageInfo(skuImageQuery);//根据商家id查询
			model.addObject("page", page);
			model.addObject("query", skuImageQuery);
			model.setViewName("product/product/picasa");
		}
		return model;
	}
}

