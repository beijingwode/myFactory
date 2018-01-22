/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.SupplierPrize;
import com.wode.factory.user.dao.SupplierPrizeDao;

@Repository("supplierPrizeDao")
public class SupplierPrizeDaoImpl extends FactoryBaseDaoImpl<SupplierPrize> implements SupplierPrizeDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierPrizeMapper";
	}

	@Override
	public SupplierPrize findPrizeByMap(Map<String, Object> map) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".findPrizeByMap", map);
	}

	@Override
	public List<SupplierPrize> findPrizeListByMap(Map<String, Object> map) {
		return getSqlSession().selectList(getIbatisMapperNamesapce() + ".findPrizeListByMap", map);
	}
	
}
