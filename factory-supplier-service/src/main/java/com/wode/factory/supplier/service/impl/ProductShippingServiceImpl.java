package com.wode.factory.supplier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.ProductShipping;
import com.wode.factory.supplier.dao.ProductShippingDao;
import com.wode.factory.supplier.service.ProductShippingService;

@Service("productShippingService")
public class ProductShippingServiceImpl extends BasePageServiceImpl<ProductShipping>
		implements ProductShippingService {

	@Autowired
	private ProductShippingDao dao;
	
	@Override
	protected BasePageDao<ProductShipping> getBaseDao() {
		return dao;
	}

	@Override
	public List<ProductShipping> getByProductId(Long productId) {
		
		return dao.getByProductId(productId);
	}

}
