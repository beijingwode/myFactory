package com.wode.factory.user.service;

import java.util.Map;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.ExpressCompany;

public interface ExpressComService extends EntityService<ExpressCompany,Long>{
	
	/**
	 * 取得快递公司情报
	 * @return
	 */
	public Map<String, ExpressCompany> getExpressCompanys();
	
	/**
	 * 取得快递公司情报
	 * @return
	 */
	public ExpressCompany getExpressComById(String expressType);
}
