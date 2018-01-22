/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.user.dao.EntParamCodeDao;


@Repository("entParamCodeDao")
public class EntParamCodeDaoImpl extends BaseDao<EntParamCode,Long> implements EntParamCodeDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "EntParamCodeMapper";
	}

	@Override
	public void saveOrUpdate(EntParamCode entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EntParamCode> selectByModel(EntParamCode m) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNamesapce() + ".selectByModel", m);
		
	}

}
