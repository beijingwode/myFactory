package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.ProductHiddenDao;
import com.wode.factory.model.ProductHidden;
import com.wode.factory.service.ProductHiddenService;

/**
 * Created by gaoyj on 2017/12/19.
 */
@Service("productHiddenService")
public class ProductHiddenServiceImpl extends FactoryEntityServiceImpl<ProductHidden> implements ProductHiddenService {
	@Autowired
	ProductHiddenDao dao;
	
	@Override
	public ProductHiddenDao getDao() {
		return dao;
	}
	@Override
	public Long getId(ProductHidden entity) {
		return entity.getId();
	}
	@Override
	public void setId(ProductHidden entity, Long id) {
		entity.setId(id);
	}
}
