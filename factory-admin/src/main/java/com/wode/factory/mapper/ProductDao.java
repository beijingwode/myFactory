package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.Product;
import com.wode.factory.vo.ProductVO;


public interface ProductDao {

	/**
	 * 
	 * 功能说明：根据id查询
	 *
	 * @param id
	 * @return
	 */
	public Product selectById(Long id);
	
	/**
	 * 
	 * 功能说明：查询属性列表
	 *
	 * @param params
	 * @return
	 */
	public List<Product> findList(Map<String, Object> params);
	
	/**
	 * 
	 * 功能说明：根据id查询
	 *
	 * @param pageTypeId
	 * @return
	 */
	public ProductVO getById(Long id);
	
	
	/**
	 * 功能说明：
	 *
	 * @param pojo
	 */
	public Integer checkByid(Product map);
	/**
	 * 功能说明：强制下架处理
	 *
	 * @param pojo
	 */
	public Integer selloffByid(Product map);
	/**
	 * 功能说明：
	 *
	 * @param pojo
	 */
	public Integer updateByBusiness(Product map);
	
	/**
	 * 
	 * 功能说明：查询热卖商品
	 * 日期:	2015年8月18日
	 * 开发者:宋艳垒
	 *
	 * @param parPro
	 * @return
	 */
	public List<Product> findRecormendHotPro(Product parPro);
	/**
	 * 更新总库存
	 * @param params
	 */
	public void updateAllNum(Map<String, Object> params);
	/**
	 * 更新总库存
	 * @param params
	 */
	public void unlockExchangeProduct(Product map);
	/**
	 * 更新总库存
	 * @param params
	 */
	public void changeBrand(Map<String, Object> params);
	
	
	public Integer getCountBySupplier(Long supplierId);

	public Integer getCountBySupplierDate(Map<String,Object> map);
	/**
	 * 通过上线时间和上线状态获取商品信息
	 * @param map
	 * @return
	 */
	public List<Product> findBySelfTypeAndSelfTime(Map<String, Object> map);
	
	/**
	 * 更改上线状态
	 * @param map
	 */
	public void updateSelfType(Product map);

	public void deleteBySupplierId(Long supplierId);

	public void updateProductSelfType(Long id);

	public void updateProductSaleKbnByMap(Map<String, Object> map);
	

}