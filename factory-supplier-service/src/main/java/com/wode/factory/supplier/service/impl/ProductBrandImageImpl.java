/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.ProductBrandImage;
import com.wode.factory.supplier.dao.ProductBrandImageDao;
import com.wode.factory.supplier.service.ProductBrandImageService;

@Service("productBrandImageService")
public class ProductBrandImageImpl extends BasePageServiceImpl<ProductBrandImage> implements  ProductBrandImageService{
	@Autowired
	@Qualifier("productBrandImageDao")
	private ProductBrandImageDao productBrandImageDao;
	
	@Override
	protected ProductBrandImageDao getBaseDao() {
		return productBrandImageDao;
	}

	@Override
	public void deleteByShop(Long shopId) {
		productBrandImageDao.deleteByShop(shopId);
	}
	@Override
	public void copyByShop(Long supplierId, Long shopId) {
		Map<String ,Long> map = new HashMap<String ,Long>();
		map.put("supplierId", supplierId);
		map.put("shopId", shopId);
		productBrandImageDao.copyByShop(map);
	}
}
