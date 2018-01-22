/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.model.ProductTrialLimitGroup;
import com.wode.factory.user.dao.ProductTrialLimitGroupDao;
import com.wode.factory.user.service.ProductTrialLimitGroupService;
import com.wode.search.WodeSearchManager;

@Service("productTrialLimitGroupService")
public class ProductTrialLimitGroupServiceImpl extends FactoryEntityServiceImpl<ProductTrialLimitGroup> implements  ProductTrialLimitGroupService{
	@Autowired
	private ProductTrialLimitGroupDao dao;

	@Override
	public ProductTrialLimitGroupDao getDao() {
		return dao;
	}
	@Override
	public Long getId(ProductTrialLimitGroup entity) {
		return entity.getId();
	}

	@Override
	public void setId(ProductTrialLimitGroup entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}	
}