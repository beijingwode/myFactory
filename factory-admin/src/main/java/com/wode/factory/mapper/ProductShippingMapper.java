package com.wode.factory.mapper;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductShipping;

public interface ProductShippingMapper extends  EntityDao<ProductShipping,Long>{
	public List<ProductShipping> getByProductId(Long productId);
	public void saveOrUpdate(ProductShipping entity);
	public void insert(ProductShipping entity);
}
