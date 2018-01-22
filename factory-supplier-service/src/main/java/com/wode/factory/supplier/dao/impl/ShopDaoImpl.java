/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.Shop;
import com.wode.factory.supplier.dao.ShopDao;

@Repository("shopDao")
public class ShopDaoImpl extends BasePageDaoImpl<Shop> implements ShopDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ShopMapper";
	}
	
	@Override
	public Long getId(Shop model) {
		return model.getId();
	}
}
