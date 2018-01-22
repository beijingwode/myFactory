/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;

import com.wode.factory.model.FLJModel;
import com.wode.factory.model.Shop;

public interface ShopSettingDao {
	public List<Shop> getBySupplierId(Long SupplierId);

	public Shop getById(Long shopId);
	
	public int findcount(Long supplierId,Long shopId);

	public List<FLJModel> findAllShop();
    
    
}
