/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Currency;
import com.wode.factory.user.query.CurrencyQuery;

public interface CurrencyDao extends  EntityDao<Currency,Long>{
	public List<Currency> findPage(CurrencyQuery query);
	public void saveOrUpdate(Currency entity);
	public Currency getByName(String name);
}
