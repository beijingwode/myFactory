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
import com.wode.factory.model.Inventory;
import com.wode.factory.supplier.dao.InventoryDao;
import com.wode.factory.supplier.query.InventoryQuery;

@Repository("inventoryDao")
public class InventoryDaoImpl extends BaseDao<Inventory,java.lang.Long> implements InventoryDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "InventoryMapper";
	}
	
	public void saveOrUpdate(Inventory entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(InventoryQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	/**
	 * 根据某一个或几个属性值获取条件的对象集合
	 * @param map
	 * @return
	 */
	public List<Inventory> findAllBymap(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findAllBymap",map);
	}
	/**
	 * 根据某一个或几个属性值获取条件的对象集合
	 * @param map
	 * @return
	 */
	public List<Inventory> findByProduct(Long productId){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findByProduct",productId);
	}

	@Override
	public List<Inventory> getByProductId(Long id) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getByProductId",id);
	}

	@Override
	public void deleteApprRelation(Long productId) {
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".deleteApprRelation", productId);
		
	}

	@Override
	public Inventory findBySpecId(long skuId) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".findBySpecId", skuId);
	}
	
}
