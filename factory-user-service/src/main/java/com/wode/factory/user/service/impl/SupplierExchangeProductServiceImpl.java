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

import com.wode.common.frame.base.BaseService;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.user.dao.SupplierExchangeProductDao;
import com.wode.factory.user.service.SupplierExchangeProductService;

@Service("supplierExchangeProductService")
public class SupplierExchangeProductServiceImpl extends BaseService<SupplierExchangeProduct,java.lang.Long> implements  SupplierExchangeProductService{
	@Autowired
	private SupplierExchangeProductDao dao;
	
	public SupplierExchangeProductDao getEntityDao() {
		return this.dao;
	}

	@Override
	public List<SupplierExchangeProduct> findProductByShareId(Long id) {
		return dao.findProductByShareId(id);
	}

	@Override
	public void updateEmpCnt(Long id) {
		dao.updateEmpCnt(id);
	}

}