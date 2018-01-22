/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.common.util.ActResult;
import com.wode.factory.model.PageChannelHotCategory;
import com.wode.factory.model.Product;
import com.wode.factory.user.query.PageChannelHotCategoryQuery;

@Service("pageChannelHotCategoryService")
public interface PageChannelHotCategoryService extends EntityService<PageChannelHotCategory,Long>{
	
	public EntityDao getEntityDao() ;
	
	public Page findPage(PageChannelHotCategoryQuery query);

	public ActResult<List<Map<String, List<Product>>>> selectProductByCategory(Long categoryId);
	
}
