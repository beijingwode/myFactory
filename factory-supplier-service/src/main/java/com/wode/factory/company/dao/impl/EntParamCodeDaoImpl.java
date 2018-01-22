/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.company.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.EntParamCodeDao;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.EnterpriseUser;

@Repository("entParamCodeDao")
public class EntParamCodeDaoImpl extends BasePageDaoImpl<EntParamCode> implements EntParamCodeDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "EntParamCodeMapper";
	}

	@Override
	public void saveOrUpdate(EntParamCode entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getId(EntParamCode model) {
		return model.getId();
	}
}
