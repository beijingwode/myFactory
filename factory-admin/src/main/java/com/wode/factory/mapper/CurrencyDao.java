/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import com.wode.factory.model.Currency;

public interface CurrencyDao{
	public void saveOrUpdate(Currency entity);

	public Currency getByName(String name);

}
