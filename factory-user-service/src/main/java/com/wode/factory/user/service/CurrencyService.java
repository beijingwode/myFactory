/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.common.stereotype.QueryCached;
import com.wode.factory.model.Currency;

@Service("currencyService")
public interface CurrencyService extends EntityService<Currency,Long>{
	
	public EntityDao getEntityDao() ;

	@QueryCached(keyPreFix="currency_findByName", timeout=60*60*12)
	public Currency findByName(String name);
	
	public List<Currency> findAll();

}
