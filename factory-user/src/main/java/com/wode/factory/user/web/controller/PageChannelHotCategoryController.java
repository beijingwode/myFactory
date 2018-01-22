/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.util.ActResult;
import com.wode.factory.model.Product;
import com.wode.factory.user.service.PageChannelHotCategoryService;

@Controller
@RequestMapping("pageChannelHotCategory")
public class PageChannelHotCategoryController extends BaseSpringController{
	@Autowired
	@Qualifier("pageChannelHotCategoryService")
	private PageChannelHotCategoryService pageChannelHotCategoryService;
	
	/*
	 * 查询分类下的热销商品
	 */
	@RequestMapping("selectHotProductByCategory")
	@ResponseBody
	public ActResult<List<Map<String, List<Product>>>> checkLogin(HttpServletRequest request,Long categoryId){
		ActResult<List<Map<String, List<Product>>>> ret = new ActResult<List<Map<String, List<Product>>>>();
		ret = pageChannelHotCategoryService.selectProductByCategory(categoryId);
		return ret;
	}
	
}

