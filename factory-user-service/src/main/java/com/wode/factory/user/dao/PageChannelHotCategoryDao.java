/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.PageChannelHotCategory;
import com.wode.factory.user.query.PageChannelHotCategoryQuery;

public interface PageChannelHotCategoryDao extends  EntityDao<PageChannelHotCategory,Long>{
	public Page findPage(PageChannelHotCategoryQuery query);
	public void saveOrUpdate(PageChannelHotCategory entity);
	public List<PageChannelHotCategory> selectByChannelId(Long categoryId);

}
