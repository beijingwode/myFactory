/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CmsContent;
import com.wode.factory.supplier.query.CmsContentQuery;

public interface CmsContentDao extends  EntityDao<CmsContent,Long>{
	public Page findPage(CmsContentQuery query);
	public void saveOrUpdate(CmsContent entity);

}
