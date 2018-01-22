/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Currency;
import com.wode.factory.user.dao.CurrencyDao;
import com.wode.factory.user.service.CurrencyService;

@Service("currencyService")
public class CurrencyServiceImpl extends BaseService<Currency,java.lang.Long> implements  CurrencyService{
	@Autowired
	@Qualifier("currencyDao")
	private CurrencyDao currencyDao;
	
	public EntityDao getEntityDao() {
		return this.currencyDao;
	}

	@Override
	public Currency findByName(String name) {
		return currencyDao.getByName(name);
	}
	
	@Override
	public List<Currency> findAll(){
		return currencyDao.findAll();
	}

}
