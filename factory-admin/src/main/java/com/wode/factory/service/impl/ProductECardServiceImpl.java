package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.ProductECardDao;
import com.wode.factory.model.ProductECard;
import com.wode.factory.service.ProductECardService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("productECardService")
public class ProductECardServiceImpl extends FactoryEntityServiceImpl<ProductECard> implements ProductECardService {
	@Autowired
	ProductECardDao dao;

	@Override
	public ProductECardDao getDao() {
		return dao;
	}

	@Override
	public Long getId(ProductECard entity) {
		return entity.getId();
	}

	@Override
	public void setId(ProductECard entity, Long id) {
		entity.setId(id);
	}
}
