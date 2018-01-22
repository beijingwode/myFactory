/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.user.dao.SupplierExchangeProductDao;

@Repository("supplierExchangeProductDao")
public class SupplierExchangeProductDaoImpl extends BaseDao<SupplierExchangeProduct,java.lang.Long> implements SupplierExchangeProductDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierExchangeProductMapper";
	}
	
	public void saveOrUpdate(SupplierExchangeProduct entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public List<SupplierExchangeProduct> findProductByShareId(Long id) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("date", new Date());
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findProductByShareId",map);
	}

	@Override
	public void updateEmpCnt(Long id) {
		Map map = new HashMap();
		map.put("id", id);
		getSqlSession().update(getIbatisMapperNamesapce()+".updateEmpCnt",map);
	}
}
