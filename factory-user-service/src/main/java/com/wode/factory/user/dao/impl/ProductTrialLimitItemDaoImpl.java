/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.ProductTrialLimitItem;
import com.wode.factory.user.dao.ProductTrialLimitItemDao;

@Repository("productTrialLimitItemDao")
public class ProductTrialLimitItemDaoImpl extends FactoryBaseDaoImpl<ProductTrialLimitItem> implements ProductTrialLimitItemDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductTrialLimitItemMapper";
	}
	@Override
	public List<ProductTrialLimitItem> getListByProductId(Long productId) {
		Map<String,Object> query = new HashMap<String, Object>();
		query.put("productId", productId);
		return getSqlSession().selectList(getIbatisMapperNamesapce() + ".getListByProductId", query);
	}
	@Override
	public Integer getProductTrialLimitItemByProductId(List<Long> list) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".getProductTrialLimitItemByProductId", list);
	}
}
