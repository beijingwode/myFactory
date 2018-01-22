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

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.user.dao.SupplierCategoryDao;
import com.wode.factory.user.query.SupplierCategoryQuery;

@Repository("supplierCategoryDao")
public class SupplierCategoryDaoImpl extends BaseDao<SupplierCategory,java.lang.Long> implements SupplierCategoryDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierCategoryMapper";
	}
	
	public void saveOrUpdate(SupplierCategory entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(SupplierCategoryQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	/**
	 * 根据条件获取全部符合条件的数据
	 * @param map
	 * @return
	 */
	public List<SupplierCategory> findAll(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findAllBymap",map);
	}

	@Override
	public List<SupplierCategory> getBySupplierId(Long supplierId) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getBySupplierId",supplierId);
	}


	@Override
	public SupplierCategory getBySupplierAndCategory(Long supplierId,
			Long categoryId,Long ShopId) {
		SupplierCategoryQuery query = new SupplierCategoryQuery();
		query.setCategoryId(categoryId);
		query.setSupplierId(supplierId);
		query.setShopId(ShopId);
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".selectOne",query);
	}
}
