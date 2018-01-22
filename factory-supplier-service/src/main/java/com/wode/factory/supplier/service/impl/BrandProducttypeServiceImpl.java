/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.factory.model.BrandProducttype;
import com.wode.factory.supplier.dao.BrandProducttypeDao;
import com.wode.factory.supplier.query.BrandProducttypeQuery;
import com.wode.factory.supplier.service.BrandProducttypeService;

import cn.org.rapid_framework.page.Page;

@Service("brandProducttypesService")
public class BrandProducttypeServiceImpl extends BaseService<BrandProducttype,java.lang.Long> implements  BrandProducttypeService{
	@Autowired
	@Qualifier("brandProducttypeDao")
	private BrandProducttypeDao brandProducttypeDao;
	
	public BrandProducttypeDao getEntityDao() {
		return this.brandProducttypeDao;
	}
	
	public Page findPage(BrandProducttypeQuery query) {
		return brandProducttypeDao.findPage(query);
	}

	@Override
	public void removeByMap(Map<String, Object> map) {
		brandProducttypeDao.removeByMap(map);
	}

	@Override
	public List<BrandProducttype> findAllByMap(Map<String, Object> map) {
		return brandProducttypeDao.findAllByMap(map);
	}

	@Override
	public void deleteByShop(Long shopId) {
		getEntityDao().deleteByShop(shopId);
	}

	@Override
	public void copyByShop(Long supplierId, Long shopId) {
		Map<String ,Long> map = new HashMap<String ,Long>();
		map.put("supplierId", supplierId);
		map.put("shopId", shopId);
		brandProducttypeDao.copyByShop(map);
	}
	
}
