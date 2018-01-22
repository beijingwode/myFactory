package com.wode.factory.supplier.service;

import java.util.List;

import com.wode.factory.company.service.BasePageService;
import com.wode.factory.model.ProductShipping;


public interface ProductShippingService extends BasePageService<ProductShipping> {
   
	public List<ProductShipping> getByProductId(Long productId);
}
