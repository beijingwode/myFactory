/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.model.ApprSupplier;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.supplier.query.ProductQuery;

public interface ApprProductDao extends  EntityDao<ApprProduct,Long>{
	
	
    //public PageInfo findPage(ProductQuery query);
	public void saveOrUpdate(ApprProduct entity);
	public ApprProduct getById(Long productId);

	/**
	 * 获取商品列表（带分页）
	 * @param map
	 * @return
	 */
	public List<ApprProduct> findProductlistPage(Map map);
	/**
	 * 获取商品列表总条数（带分页）
	 * @param map
	 * @return
	 */
	public Integer findProductlistPageCount(Map map);
	
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
	public ApprProduct getProductCheckById(Map map);
	
	/**
	 * 销售排行榜
	 * @return
	 */
	public List<ApprProduct> getSaleroom(Map map);
	/**
	 * 根据供应商ID获取所有在售商品，以商品排位数降序排列
	 * @return
	 */
	public List<ApprProduct> getSellingBySupplierId(Map<String, Object> map);
	/**
	 * 只查询商品信息
	 * @param productQuery
	 * @return
	 */
	//public List<ApprProduct> findProduct(ProductQuery productQuery);
	/**
	 * 根据商品ID更新商品排位数
	 * @param map
	 */
	public void updateSortNum(Map<String, Object> map);
	public void insert(ApprProduct product);
	
	/**
	 * 查找未删除的商品
	 * @param product
	 * @return
	 */
	public List<ApprProduct> getNotDeleteProductByFullName(ApprProduct product);
	/**
	 * 根据商品id查询第三方商品价格
	 * @param id
	 * @return
	 */
	public List<ProductThirdPrice> getProductThirdPriceByProductId(Long id);
	
	
	public ApprProduct getProductandstauts(ApprProduct product);
	
	public ApprProduct selectProductIdAndStatus(Long productId);
	public ApprProduct selectByProductId(Long productId);
	//删除临时表中的数据
	public void deleteById(Long id);
	
	
	/**
	 * 获取供应商下的临时表商品名称
	 * @param map
	 * @return
	 */
	public Long getSupplierapprFullname(Map map);
	
	
}