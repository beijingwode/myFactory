package com.wode.factory.mapper;

import com.wode.factory.model.Shop;

public interface ShopDao {

	Shop getById(Long shopId);

	void deleteCollectionShopBySupplierId(Long supplierId);

	void deleteShopPromotionBySupplierId(Long supplierId);

	void deleteShopSettingBySupplierId(Long supplierId);

	void deleteBySupplierId(Long supplierId);

}
