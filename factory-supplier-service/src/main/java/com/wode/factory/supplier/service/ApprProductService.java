/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityService;
import com.wode.common.util.ActResult;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.model.Product;
import com.wode.factory.model.UserFactory;

public interface ApprProductService extends EntityService<ApprProduct,Long>{
	
	/**
	 * 新增商品或者修改商品
	 * @param map
	 */
	public ApprProduct saveOrUpdateProduct(Map map);
	//public void saveOrUpdatePrice(ApprProduct entity);
	
	/**
	 * 获取商品列表（带分页）
	 * @param map
	 * @return
	 */
	public List<ApprProduct> findProductlistPage(Map map);
	
	public Integer findProductlistPageCount(Map map);

	public List<Product> getProductByMap(Map map);
	
	/**
	 * 批量更新 
	 * @param map
	 */
	public void updateProductByids(Map map);
	
	public ApprProduct getProductandstauts(ApprProduct product);
	
	public ApprProduct selectProductIdAndStatus(Long productId);
	public ApprProduct selectByProductId(Long productId);
	
	/**
	 * 获取实体类对象，包含评价不通过信息
	 * @return
	 */
	public ApprProduct getProductCheckById(Map map);
	
	//删除临时表中的数据
	public void deleteById(Long id);
	
	/**
	 * 商品上架
	 * @param productId
	 */
	public ActResult sellOn(List<Long> productId,UserFactory user);
	/**
	 * 查找未删除的商品
	 * @param product
	 * @return
	 */
	public List<ApprProduct> getNotDeleteProductByFullName(ApprProduct product);
	/**
	 * 获取供应商下的临时表商品名称
	 * @param map
	 * @return
	 */
	public Long getSupplierapprFullname(Map map);
	
}
