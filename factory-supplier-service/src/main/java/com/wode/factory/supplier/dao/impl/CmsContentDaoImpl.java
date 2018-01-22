/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.CmsContent;
import com.wode.factory.supplier.dao.CmsContentDao;
import com.wode.factory.supplier.query.CmsContentQuery;

@Repository("cmsContentDao")
public class CmsContentDaoImpl extends BaseDao<CmsContent,java.lang.Long> implements CmsContentDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "CmsContentMapper";
	}
	
	public void saveOrUpdate(CmsContent entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(CmsContentQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	

}
