package com.wode.factory.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wode.common.stereotype.QueryCached;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Specification;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.vo.ProductAttributeVo;
import com.wode.factory.vo.ProductParameterVo;
import com.wode.factory.vo.ProductSpecificationsImgVo;
import com.wode.factory.vo.ProductVo;
import com.wode.factory.vo.SupplierShopVo;


public interface ProductService {

	
	/**
	 * 根据id查找商品
	 * @param id
	 * 商品id
	 * @param dropEmptyAttr
	 * 是否去掉空的属性
	 * @return
	 * 查出来的商品
	 */
	public ProductVo findById(Long id,boolean dropEmptyAttr) ;
		
	
	
	/**
	 * 查找商品属性
	 * @param productId
	 * @param categoryId
	 * @param withOutEmptyAttr
	 * @return
	 */
	@QueryCached
	public List<ProductAttributeVo> findAttr(Long productId, Long categoryId,boolean withOutEmptyAttr) ;
	
	/**
	 * 查找商品参数
	 * @param productId
	 * @param categoryId
	 * @param withOutEmptyAttr
	 * @return
	 */
	@QueryCached
	public List<ProductParameterVo> findPar(Long productId, Long categoryId,boolean withOutEmptyAttr) ;
	
	public SupplierShopVo findShopByProductId(Long id) ;
	@QueryCached
	public SupplierShopVo findShopByProductIdCache(Long id) ;
	
	/**
	 * 查找商品sku
	 * @param id
	 * 商品id
	 * @return
	 * key:sku名称（如：颜色）
	 */
	public Map<String,List<ProductSpecificationValue>> findSku4Show(Long productId) ;
	
	@QueryCached(keyPreFix="product_Allsku")
	public Map<String,List<ProductSpecificationValue>> findSku4ShowCache(Long productId) ;
	/**
	 * 查找商品sku
	 * @param productId
	 * 商品id
	 * @return
	 */
	public List<ProductSpecifications> findSku(Long productId) ;
	
	/**
	 * 根据sku查找商品
	 * @param skuId
	 * 商品id
	 * @return
	 */
	public Product findBySku(Long skuId) ;
	
	/**
	 * 根据商品查商家
	 * @param productId
	 * @return
	 */
	public Supplier findSupplier(Long productId) ;
	
	@QueryCached(keyPreFix="product_AllskuImg")
	public Map<String, List<ProductSpecificationsImgVo>> findSkuImg(Long productId);

	List<ProductSpecificationsImgVo> findSkuImgBySkuId(String skuId);

	/**
	 * 根据商品表最低价格与商品ID查询相关最低价格的SKU的LIST
	 */
	public List<ProductSpecifications> findByMinprice(Long productId,BigDecimal minprice);
	@QueryCached(keyPreFix="findByMinpriceCache")
	public List<ProductSpecifications> findByMinpriceCache(Long productId,BigDecimal minprice);



	public Specification findByItemsValue(String skuValueId);
	@QueryCached
	public Specification findByItemsValueCache(String skuValueId);



	public List<ProductSpecificationValue> findSKUValue(Long productId, Long id);
	
	public List<ProductSpecifications> findByProductIdAndSkuId(Long productId,Long skuId);
	
	/**
	 * 查询商品限购 限购返回true 
	 * @param product
	 * @param user
	 * @return
	 */
    public boolean checkProductLimit(Product product, UserFactory user);
    /**
     * 查询商家是否有上架中的商品
     * @param supplier
     * @return
     */
    public boolean queryIsMarketableBySupplier(Long supplier);
    
   /**
    * 查询商家可够商品list（一起购）
    * @param shopId
    * @return
    */
    public List<ProductVo> findProductByShopId(Long shopId);
}
