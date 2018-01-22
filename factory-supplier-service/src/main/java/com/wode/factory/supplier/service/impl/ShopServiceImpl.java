/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.Shop;
import com.wode.factory.supplier.dao.ShopDao;
import com.wode.factory.supplier.service.ShopService;

@Service("shopService")
public class ShopServiceImpl extends BasePageServiceImpl<Shop> implements  ShopService{
	@Autowired
	private ShopDao ShopDao;
	
	@Override
	protected ShopDao getBaseDao() {
		return ShopDao;
	}

}
