package com.wode.factory.mapper;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductSpecificationValue;

public interface ProductSpecificationValueMapper extends  EntityDao<ProductSpecificationValue,Long>{
	public List<ProductSpecificationValue> getByProductId(Long productId);
	public void saveOrUpdate(ProductSpecificationValue entity);
	public void insert(ProductSpecificationValue entity);
	public void deleteBySupplierId(Long supplierId);
}
