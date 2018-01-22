package com.wode.factory.mapper;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductAttribute;


public interface ProductAttributeMapper extends  EntityDao<ProductAttribute,Long>{
	public List<ProductAttribute> getByProductId(Long productId);
	public void saveOrUpdate(ProductAttribute entity);
	public void insert(ProductAttribute entity);
	public void deleteBySupplierId(Long supplierId);
}
