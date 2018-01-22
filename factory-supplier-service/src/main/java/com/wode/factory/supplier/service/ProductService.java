/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.common.util.ActResult;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.query.ProductQuery;

public interface ProductService extends EntityService<Product,Long>{
	
	
	
	public PageInfo findPage(ProductQuery query);
	
	/**
	 * 获取商品列表（带分页）
	 * @param map
	 * @return
	 */
	public List<Product> findProductlistPage(Map map);
	
	public Integer findProductlistPageCount(Map map);
	
	
	/**
	 * 商品上架
	 * @param productId
	 */
	public ActResult sellOn(List<Long> productId,UserFactory user);
	
	/**
	 * 商品下架
	 * @param productId
	 */
	public ActResult sellOff(List<Long> productId,UserFactory user);
	
	
	/**
	 * 批量更新
	 * @param map
	 */
	public void updateProductByids(Map map);
	
	/**
	 * 获取商品列表（带分页）
	 * @param map
	 * @return
	 */
	public List<Product> getProductByMap(Map map);
	
	/**
	 * 获取实体类对象，包含评价不通过信息
	 * @return
	 */
	public Product getProductCheckById(Map map);
	
	/**
	 * 销售排行榜
	 * @return
	 */
	public List<Product> getSaleroom(Map map);
	
	/**
	 * 根据供应商ID获取所有在售商品，以商品排位数降序排列
	 * @param supplierId 供应商ID
	 * @return
	 */
	public List<Product> getSellingBySupplierId(Long supplierId);
	/**
	 * 根据商品ID更新商品排位数
	 * @param productId 商品ID
	 * @param sortNum 商品展位排位数
	 */
	public void updateSortNum(Long productId, Integer sortNum);
	public List<Product> findProduct(ProductQuery productQuery);
	public List<Product> getNotDeleteProductByFullName(Product product);
	/**
	 * 根据商品id获取第三方价格
	 * @param valueOf
	 * @return
	 */
	public List<ProductThirdPrice> getProductThirdPriceByProductId(Long valueOf);
	/**
	 * 获取供应商下的商品名称
	 * @param map
	 * @return
	 */
	public Long getSupplierFullname(Map map);

	/**
	 * 查询商家所有在售商品
	 * @param map
	 * @return
	 */
	public List<Product> findProductlist(Map<String, Object> map);
	
//	/**
//	 * 新增商品或者修改商品
//	 * @param map
//	 */
//	public Product saveOrUpdateProduct(Map map);
	
	public void changProductOuterId(Map map);
}
