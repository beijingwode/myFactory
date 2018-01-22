package com.wode.factory.mapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;

import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;



public interface ProductSpecificationsDao extends  EntityDao<ProductSpecifications,Long>{

	/**
	 * 
	 * 功能说明：根据日查询
	 *
	 * @return
	 */
	public List<ProductSpecifications> findlistByProductid(Long productid);

	public ProductSpecifications getById(Long id);
	
	public List<ProductSpecificationValue> findProductSpecificationValueBymap(Map map);
	/**
	 * 通过商家id获取商品sku
	 * @param supplierId
	 * @return
	 */
	public BigDecimal findlistBySupplierId(Long supplierId);
	public List<ProductSpecifications> getlistByProductid(Long id);
	public void saveOrUpdate(ProductSpecifications entity);
	public void insert(ProductSpecifications entity);

	public void deleteBySupplierId(Long supplierId);
}