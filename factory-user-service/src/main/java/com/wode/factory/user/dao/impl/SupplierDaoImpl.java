/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Supplier;
import com.wode.factory.user.dao.SupplierDao;

@Repository("supplierDao")
public class SupplierDaoImpl extends BaseDao<Supplier,java.lang.Long> implements SupplierDao{

	@Autowired
	private RedisUtil redis;
	
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

	@Override
    public Supplier getById(Long id) {

		//缓存中取出
		String json = redis.getData("REDIS_USER_SUPPLIER_" + id);
		if(!StringUtils.isEmpty(json)) {
			return JsonUtil.getObject(json, Supplier.class);
		} else {
			Supplier s = super.getById(id);
			redis.setData("REDIS_USER_SUPPLIER_" + id, JsonUtil.toJson(s),60*60*2);
			return s;
		}
    }

	@Override
	public List<Supplier> findByManagerId(Map<String, String> queryMap) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findByManagerId",queryMap);
	}
}
