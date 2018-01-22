package com.wode.factory.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.EntSeasonAct;
import com.wode.factory.model.ProductShipping;
import com.wode.factory.supplier.dao.ProductShippingDao;
@Repository("productShippingDao")
public class ProductShippingDaoImpl extends BasePageDaoImpl<ProductShipping> implements ProductShippingDao {


	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductShippingMapper";
	}
	
	public void saveOrUpdate(ProductShipping entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}


	@Override
	public Long getId(ProductShipping model) {
		return model.getId();
	}

	@Override
	public List<ProductShipping> getByProductId(Long productId) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getByProductId", productId);
	}

	@Override
	public void deleteApprRelation(Long productId) {
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".deleteApprRelation", productId);
		
	}
}
