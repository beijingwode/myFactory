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

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.CheckOpinion;
import com.wode.factory.model.Supplier;
import com.wode.factory.supplier.dao.SupplierDao;
import com.wode.factory.supplier.query.SupplierQuery;

@Repository("supplierDao")
public class SupplierDaoImpl extends BaseDao<Supplier,java.lang.Long> implements SupplierDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierMapper";
	}
	
	public void saveOrUpdate(Supplier entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(SupplierQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public Supplier getByUserId(Long userId) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getByUserId",userId);
	}

	@Override
	public void updateEnter(Map<String, Object> reparm) {
		this.getSqlSession().update(getIbatisMapperNamesapce()+".updateEnter",reparm);
	}

	@Override
	public void updateShippingFree(Map<String, Object> reparm) {
		this.getSqlSession().update(getIbatisMapperNamesapce()+".updateShippingFree",reparm);
	}
	
	@Override
	public List<Supplier> getBymap(Map<String, Object> map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getBymap",map);
	}

	@Override
	public List<CheckOpinion> getCheckOpinionListBySupplierId(Long id) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getCheckOpinionListBySupplierId",id);
	}

	@Override
	public void updatePeopleNumber(Supplier supplier) {
		this.getSqlSession().update(getIbatisMapperNamesapce()+".updatePeopleNumber",supplier);
	}

	@Override
	public void updateFirmLogo(Supplier supplier) {
		this.getSqlSession().update(getIbatisMapperNamesapce()+".updateFirmLogo",supplier);
		
	}
	

}
