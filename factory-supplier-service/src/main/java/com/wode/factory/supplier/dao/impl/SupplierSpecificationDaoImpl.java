/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.SupplierSpecification;
import com.wode.factory.supplier.dao.SupplierSpecificationDao;

@Repository("supplierSpecificationDao")
public class SupplierSpecificationDaoImpl extends BasePageDaoImpl<SupplierSpecification> implements SupplierSpecificationDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierSpecificationMapper";
	}
	

	@Override
	public Long getId(SupplierSpecification model) {
		return model.getId();
	}
	
	/**
	 * 根据供应商id获取该供应商所有的用于生产sku的规格及其规格值 
	 * @return
	 */
	public List<SupplierSpecification> getSpecificationlistByCategoryid(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getSpecificationlistByCategoryid",map);
	}

	/**
	 * 根据供应商id获取该供应商所有的用于生产sku的规格及其规格值 
	 * @return
	 */
	public Integer getOtherUseCount(Map map){
		Number num = this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getOtherUseCount",map);
		return num.intValue();
	}


	@Override
	public SupplierSpecification getSpecificationByitemid(Long vid) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getSpecificationByitemid",vid);
	}
}
