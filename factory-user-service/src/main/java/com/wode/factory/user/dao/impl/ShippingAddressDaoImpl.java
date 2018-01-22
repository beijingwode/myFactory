/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.user.dao.ShippingAddressDao;
import com.wode.factory.user.query.ShippingAddressQuery;

@Repository("shippingAddressDao")
public class ShippingAddressDaoImpl extends BaseDao<ShippingAddress,java.lang.Long> implements ShippingAddressDao{
	



	@Override
	public List<ShippingAddress> findByUserId(long userId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findByUserId",userId);
	}

	@Override
	public String getIbatisMapperNamesapce() {
		return "ShippingAddressMapper";
	}
	
	public void saveOrUpdate(ShippingAddress entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ShippingAddressQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public ShippingAddress findByQuery(ShippingAddressQuery query) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findByQuery",query);
	}

}
