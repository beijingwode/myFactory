/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Currency;
import com.wode.factory.supplier.dao.CurrencyDao;
import com.wode.factory.supplier.query.CurrencyQuery;
import com.wode.factory.supplier.service.CurrencyService;

@Service("currencyService")
public class CurrencyServiceImpl extends BaseService<Currency,java.lang.Long> implements  CurrencyService{
	@Autowired
	@Qualifier("currencyDao")
	private CurrencyDao currencyDao;
	
	public EntityDao getEntityDao() {
		return this.currencyDao;
	}
	
	public Page findPage(CurrencyQuery query) {
		return currencyDao.findPage(query);
	}
	
}
