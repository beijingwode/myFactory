package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.ProductSpecificationsImageDao;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.service.ProductSpecificationsImageService;
@Service("productSpecificationsImageService")
public class ProductSpecificationsImageServiceImpl implements ProductSpecificationsImageService{
	@Autowired
	private ProductSpecificationsImageDao productSpecificationsImageDao;


	@Override
	public List<ProductSpecificationsImage> findlistByProductSpecificationsid(Long id) {
		return productSpecificationsImageDao.findlistByProductSpecificationsid(id);
	}


	@Override
	public List<ProductSpecificationsImage> getByProductId(Long productId) {
		
		return productSpecificationsImageDao.getByProductId(productId);
	}


	@Override
	public void saveOrUpdate(ProductSpecificationsImage entity) throws DataAccessException {
		productSpecificationsImageDao.saveOrUpdate(entity);
		
	}

	@Override
	public void updateImg(Map<String,Object> param) {
		productSpecificationsImageDao.updateImg(param);
	}



	
	
}
