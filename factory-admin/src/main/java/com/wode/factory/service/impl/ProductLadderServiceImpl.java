package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.ProductLadderDao;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.service.ProductLadderService;
@Service("productLadderService")
public class ProductLadderServiceImpl extends FactoryEntityServiceImpl<ProductLadder> implements ProductLadderService {
	
	@Autowired
	ProductLadderDao productLadderMapper;
	
	@Override
	public ProductLadderDao getDao() {
		return productLadderMapper;
	}
	
	@Override
	public List<ProductLadder> getByProductId(Long productId) {
		return productLadderMapper.getByProductId(productId);
	}

	@Override
	public Long getId(ProductLadder entity) {
		return entity.getId();
	}

	@Override
	public void setId(ProductLadder entity, Long id) {
		entity.setId(id);
	}


}
