package com.wode.factory.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.ProductSpecificationsDao;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.service.ProductSpecificationsService;
@Service("productSpecificationsService")
public class ProductSpecificationsServiceImpl implements ProductSpecificationsService{
	@Autowired
	private ProductSpecificationsDao productSpecificationsDao;


	@Override
	public List<ProductSpecifications> findlistByProductid(Long productid) {
		return productSpecificationsDao.findlistByProductid(productid);
	}


	@Override
	public ProductSpecifications getById(Long id) {
		return productSpecificationsDao.getById(id);
	}

	public List<ProductSpecificationValue> findProductSpecificationValueBymap(Map map){
		return productSpecificationsDao.findProductSpecificationValueBymap(map);
		
	}


	@Override
	public List<ProductSpecifications> getlistByProductid(Long productId) {
		// TODO Auto-generated method stub
		return productSpecificationsDao.getlistByProductid(productId);
	}


	@Override
	public void saveOrUpdate(ProductSpecifications entity) throws DataAccessException {
		productSpecificationsDao.saveOrUpdate(entity);
		
	}
}
