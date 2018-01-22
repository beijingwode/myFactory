package com.wode.factory.supplier.dao;

import java.util.List;

import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.model.ProductShipping;

public interface ProductShippingDao extends  BasePageDao<ProductShipping>{
//    int deleteByPrimaryKey(Long id);
//
//    int insert(ProductShipping record);
//
//    int insertSelective(ProductShipping record);
//
//    ProductShipping selectByPrimaryKey(Long id);
//
//    int updateByPrimaryKeySelective(ProductShipping record);
//
//    int updateByPrimaryKey(ProductShipping record);
	
	public List<ProductShipping> getByProductId(Long productId);
	public void deleteApprRelation(Long productId);
}