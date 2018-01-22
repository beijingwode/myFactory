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
import com.wode.factory.model.BrandProducttype;
import com.wode.factory.supplier.dao.BrandProducttypeDao;
import com.wode.factory.supplier.query.BrandProducttypeQuery;

@Repository("brandProducttypeDao")
public class BrandProducttypeDaoImpl extends BaseDao<BrandProducttype,java.lang.Long> implements BrandProducttypeDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "BrandProducttypeMapper";
	}
	
	public void saveOrUpdate(BrandProducttype entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(BrandProducttypeQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public void removeByMap(Map<String, Object> map) {
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".removeByMap",map);
	}

	@Override
	public List<BrandProducttype> findAllByMap(Map<String, Object> map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findAllByMap",map);
	}

	@Override
	public void deleteByShop(Long shopId) {
		getSqlSession().delete(getIbatisMapperNamesapce()+".deleteByShop", shopId);
	}
	

	@Override
	public void copyByShop(Map<String, Long> map) {
		this.getSqlSession().insert(getIbatisMapperNamesapce()+".copyByShop",map);
	}
}
