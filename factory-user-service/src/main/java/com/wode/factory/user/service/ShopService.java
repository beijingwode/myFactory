package com.wode.factory.user.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.common.stereotype.QueryCached;
import com.wode.factory.model.FLJModel;
import com.wode.factory.model.Shop;
import com.wode.factory.model.StoreCategory;
import com.wode.factory.vo.SupplierShopVo;

/**
 * Created by Bing King on 2015/3/9.
 */
public interface ShopService {

	@QueryCached(keyPreFix="shop_getShopSettingById")
    public Shop getShopSettingById(Long shopId);
    
    /**
     * 根据供应商ID获取店铺分类
     * @param supplierId    供应商ID
     * @return
     */
    public List<StoreCategory> getStoreCategories(Long supplierId);

    /**
     * 根据商家分类获取商品
     * @param storeId   店铺分类ID
     * @param page 页数
     * @return
     */
    public PageInfo getProductsByStoreCategory(Long storeId, Integer page);

    /**
     * 根据供应商ID获取商品
     * @param supplierId
     * @param page
     * @return
     */
    public PageInfo getProductsByShop(Long supplierId, Long shopId,Integer page);

    /**
     * 获取全部有效店铺
     * @return
     */
	public List<FLJModel> findAllShop();

	/**
     * 根据供应商ID获取商品数量
     * @param supplierId
     * @return
     */
	@QueryCached(keyPreFix="findProductCount")
	public int findProductCount(Long supplierId,Long shopId);
	/**
	 * 根据企业id获取店铺信息
	 * @param supplierId
	 * @return
	 */
	public List<Shop> getShopBySupplierId(Long supplierId);

	public SupplierShopVo findShopByShopIdCache(Long shopId);

}
