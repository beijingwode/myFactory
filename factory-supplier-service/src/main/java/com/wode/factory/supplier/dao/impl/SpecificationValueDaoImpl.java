/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.SpecificationValue;
import com.wode.factory.supplier.dao.SpecificationValueDao;
import com.wode.factory.supplier.query.SpecificationValueQuery;

@Repository("specificationValueDao")
public class SpecificationValueDaoImpl extends BaseDao<SpecificationValue,java.lang.Long> implements SpecificationValueDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SpecificationValueMapper";
	}
	
	public void saveOrUpdate(SpecificationValue entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(SpecificationValueQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public SpecificationValue findSpecificationValue(String categoryName,String speName, Long supplierId,int orders) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("categoryName", categoryName);
		map.put("speName", speName);
		map.put("orders", orders);
		if(StringUtils.isNullOrEmpty(supplierId)){
			return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findSpecificationValueOne", map);
		}else{
			map.put("supplierId", supplierId);
			return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findSpecificationValueTwo", map);
		}
	}

	@Override
	public List<SpecificationValue> findSpecificationValue(SpecificationValue speValue) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findPage", speValue);
	}

	@Override
	public int copyFromOther(Long oId, Long nId) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("oId", oId);
		map.put("nId", nId);
		return getSqlSession().insert(getIbatisMapperNamesapce()+".copyFromOther", map); 
	}
	

}
