/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.AttributeOption;
import com.wode.factory.supplier.query.AttributeOptionQuery;

public interface AttributeOptionDao extends  EntityDao<AttributeOption,Long>{
	public Page findPage(AttributeOptionQuery query);
	public void saveOrUpdate(AttributeOption entity);

}
