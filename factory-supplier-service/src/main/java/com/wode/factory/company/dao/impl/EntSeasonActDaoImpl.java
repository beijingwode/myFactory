/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.company.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.EntSeasonActDao;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.EntSeasonAct;

@Repository("entSeasonActDao")
public class EntSeasonActDaoImpl extends BasePageDaoImpl<EntSeasonAct> implements EntSeasonActDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "EntSeasonActMapper";
	}

	@Override
	public void saveOrUpdate(EntSeasonAct entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getId(EntSeasonAct model) {
		return model.getId();
	}
}
