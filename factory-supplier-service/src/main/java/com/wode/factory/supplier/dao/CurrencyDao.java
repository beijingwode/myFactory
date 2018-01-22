/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Currency;
import com.wode.factory.supplier.query.CurrencyQuery;

public interface CurrencyDao extends  EntityDao<Currency,Long>{
	public Page findPage(CurrencyQuery query);
	public void saveOrUpdate(Currency entity);
	public Currency getByName(String name);

}
