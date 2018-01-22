/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.nutz.dao.QueryResult;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.common.util.ActResult;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.UserExchangeFavorites;

public interface UserExchangeFavoritesService extends FactoryEntityService<UserExchangeFavorites>{

	static final String fecthFields[] = {"image", "brand",
		    "name",
		    "supplierId",
		    "salePrice",
		    "maxFucoin",
		    "shopId",
		    "shopName",
		    "price",
		    "productId",
		    "minSkuId",
		    "stock",
		    "allStock",
		    "createDate"};
	static final Long SUPPER_SUPPLIER_ID= 1019589081269290L;		// 我的圈商家id

	ActResult<String> addFavorites(Long userId,Long productId);
	List<UserExchangeFavorites> autoAddFavorites(Long userId,ExchangeSuborder subOrder);
	
	QueryResult getSelectable(Integer page, Integer pageSize,Long userId);
	List<HashMap<String, Object>> getMaySelectable(Long id, BigDecimal productPrice, BigDecimal sum);
}
