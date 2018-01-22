/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.PageChannelHotCategory;
import com.wode.factory.user.dao.PageChannelHotCategoryDao;
import com.wode.factory.user.query.PageChannelHotCategoryQuery;

@Repository("pageChannelHotCategoryDao")
public class PageChannelHotCategoryDaoImpl extends BaseDao<PageChannelHotCategory,java.lang.Long> implements PageChannelHotCategoryDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "PageChannelHotCategoryMapper";
	}
	
	public void saveOrUpdate(PageChannelHotCategory entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(PageChannelHotCategoryQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public List<PageChannelHotCategory> selectByChannelId(Long channelId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findByChannelId", channelId);
	}
	

}
