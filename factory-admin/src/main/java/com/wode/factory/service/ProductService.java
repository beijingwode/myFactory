package com.wode.factory.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.util.ActResult;
import com.wode.factory.model.Product;


public interface ProductService{
	public static final String PAGE_LOCK_REASON = "首页、活动页自动锁定";
	/**
	 * 
	 * 功能说明：查询商品列表（带分页）
	 * @param params
	 * @return
	 */
	PageInfo<Product> findList(Map<String, Object> params);

	/**
	 * 
	 * 功能说明：商品详情
	 * @param id
	 * @return
	 */
	Product getById(Long id);

	/**
	 * 
	 * 功能说明：商品审核
	 *
	 * @param id
	 * @return
	 */
	void unlockExchangeProduct(Product map);
	Integer updateByBusiness(Product product);
	
	/**
	 * 
	 * 功能说明：缓存商品信息以及规格id对应的productId
	 *
	 * @param productId
	 * @return
	 */
	public Product cache(Long productId,Map<Long,Long> pPvs);

	/**
	 * 
	 * 功能说明：缓存商品信息以及规格id对应的productId
	 *
	 * @param productId
	 * @return
	 */
	public void destroy(Long productId);
	/**
	 * 
	 * 功能说明：查询热卖商品
	 * 日期:	2015年8月18日
	 * 开发者:宋艳垒
	 *
	 * @param parPro
	 * @return
	 */
	List<Product> findRecormendHotPro(Product parPro);

	List<Product> find(Map<String, Object> params);
	/**
	 * 更新商品总库存
	 * @param productId
	 * @param total
	 */
	void updateAllNum(Long productId, int total);

	public void changeBrand(Long supplierId,Long shopId);
	ActResult createProductHtml(Long productId);
	public Integer getCountBySupplier(Long supplierId);
	public Integer getCountBySupplierDate(Long supplierId,Date startDate,Date endDate);
	/**
	 * 
	 * 功能说明：根据id查询
	 *
	 * @param id
	 * @return
	 */
	public Product selectById(Long id);

	void updateProduct(Long id);

	void updateProductSaleKbnByMap(Map<String, Object> map);
}
