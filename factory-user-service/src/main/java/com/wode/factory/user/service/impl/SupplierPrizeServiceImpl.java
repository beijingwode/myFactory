/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.model.SupplierPrize;
import com.wode.factory.user.dao.SupplierPrizeDao;
import com.wode.factory.user.service.SupplierPrizeService;

@Service("supplierPrizeService")
public class SupplierPrizeServiceImpl extends FactoryEntityServiceImpl<SupplierPrize> implements  SupplierPrizeService{
	@Autowired
	private SupplierPrizeDao dao;
	
	@Override
	public SupplierPrizeDao getDao() {
		return dao;
	}

	@Override
	public Long getId(SupplierPrize entity) {
		return entity.getId();
	}

	@Override
	public void setId(SupplierPrize entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}

	@Override
	public SupplierPrize findPrizeByMap(Map<String, Object> map) {
		return dao.findPrizeByMap(map);
	}

	@Override
	public List<SupplierPrize> findPrizeListByMap(Map<String, Object> map) {
		return dao.findPrizeListByMap(map);
	}
}