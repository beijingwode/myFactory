package com.wode.factory.user.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.FLJProductModel;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.user.dao.ProductSpecificationsDao;
import com.wode.factory.user.vo.ProductSpecificationsVo;

@Repository("productSpecificationsDao")
public class ProductSpecificationsDaoImpl extends BaseDao<ProductSpecifications,java.lang.Long> implements ProductSpecificationsDao {
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductSpecificationsMapper";
	}
	@Override
	public void saveOrUpdate(ProductSpecifications entity)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProductSpecificationsVo selectOne(String itemids) {
		
		ProductSpecificationsVo psv = getSqlSession().selectOne(getIbatisMapperNamesapce()+".findByItemids", itemids);
		return psv;
	}
	
	@Override
	public List<FLJProductModel> findTop3(Long supplierId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findTop3",supplierId);
	}
	@Override
	public List<ProductSpecifications> findByProductId(String productId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findByProductId",productId);
	}
	
	
}
