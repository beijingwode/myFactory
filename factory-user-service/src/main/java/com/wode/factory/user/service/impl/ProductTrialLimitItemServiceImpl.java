/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.model.ProductTrialLimitGroup;
import com.wode.factory.model.ProductTrialLimitItem;
import com.wode.factory.user.dao.ProductTrialLimitItemDao;
import com.wode.factory.user.service.ProductTrialLimitItemService;

@Service("productTrialLimitItemService")
public class ProductTrialLimitItemServiceImpl extends FactoryEntityServiceImpl<ProductTrialLimitItem> implements  ProductTrialLimitItemService{
	@Autowired
	private ProductTrialLimitItemDao dao;

	@Override
	public ProductTrialLimitItemDao getDao() {
		return dao;
	}
	@Override
	public Long getId(ProductTrialLimitItem entity) {
		return entity.getId();
	}

	@Override
	public void setId(ProductTrialLimitItem entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}
	
	@Override
	public List<ProductTrialLimitItem> getListByProductId(Long productId) {
		return dao.getListByProductId(productId);
	}
	@Override
	public Integer getProductTrialLimitItemByProductId(List<Long> list) {
		return dao.getProductTrialLimitItemByProductId(list);
	}	
}