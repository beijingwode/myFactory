package com.wode.factory.user.service;

import java.util.List;

import com.wode.factory.model.FLJProductModel;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.vo.ProductSpecificationsVo;

public interface ProductSpecificationsService{
	
//	@QueryCached(keyPreFix="sku",timeout=45)
	ProductSpecificationsVo selectProductSpecifications(String itemids);
	
	ProductSpecificationsVo findByIdCache(long id,String productId);

	/**
     * 根据供应商ID获取商品销售数量前三个
     * @param supplierId
     * @return
     */
	List<FLJProductModel> findTop3(Long supplierId);
	
	/**
     * 重置商品的价格
     * @param supplierId
     * @return
     */
	boolean resetPrice(ProductSpecifications sku,Product p,UserFactory user,boolean isDetail,Integer quantity);
	/**
	 * 通过商品id获取sku信息
	 */
	List<ProductSpecifications> findByProductId(String productId);

}
