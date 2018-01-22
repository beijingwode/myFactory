/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Inventory;
import com.wode.factory.user.dao.InventoryDao;
import com.wode.factory.user.query.InventoryQuery;

@Repository("inventoryDao")
public class InventoryDaoImpl extends BaseDao<Inventory,java.lang.Long> implements InventoryDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "InventoryMapper";
	}
	
	public void saveOrUpdate(Inventory entity){
		if(entity.getId() != null) 
 			update(entity);
	}
	
	
	@Override
	public Inventory findBySpecId(long productSpecificationsId) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findBySpecId", productSpecificationsId);
	}
	
	

}
