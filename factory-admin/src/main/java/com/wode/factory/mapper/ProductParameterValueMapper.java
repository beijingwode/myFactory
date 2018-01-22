package com.wode.factory.mapper;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductParameterValue;

public interface ProductParameterValueMapper extends  EntityDao<ProductParameterValue,Long>{
	public List<ProductParameterValue> getByProductId(Long productId);
	public void saveOrUpdate(ProductParameterValue entity);
	public void insert(ProductParameterValue entity);
	public void deleteBySupplierId(Long supplierId);
}
