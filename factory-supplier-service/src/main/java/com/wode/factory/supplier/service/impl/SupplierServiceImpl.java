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
import com.wode.common.frame.base.EntityDao;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.CheckOpinion;
import com.wode.factory.model.Supplier;
import com.wode.factory.supplier.dao.SupplierDao;
import com.wode.factory.supplier.query.SupplierQuery;
import com.wode.factory.supplier.service.SupplierService;

import cn.org.rapid_framework.page.Page;

@Service("supplierService")
public class SupplierServiceImpl extends BaseService<Supplier,java.lang.Long> implements  SupplierService{
	@Autowired
	@Qualifier("supplierDao")
	private SupplierDao supplierDao;

	@Autowired
	private RedisUtil redis;
	
	public EntityDao getEntityDao() {
		return this.supplierDao;
	}
	
	public Page findPage(SupplierQuery query) {
		return supplierDao.findPage(query);
	}

	@Override
	public Supplier getByUserId(Long userId) {
		return supplierDao.getByUserId(userId);
	}

	@Override
	public void updateEnter(Map<String, Object> reparm) {
		supplierDao.updateEnter(reparm);
	}

	@Override
	public List<Supplier> getBymap(Map<String, Object> map) {
		return supplierDao.getBymap(map);
	}

	@Override
	public List<CheckOpinion> getCheckOpinionListBySupplierId(Long id) {
		return this.supplierDao.getCheckOpinionListBySupplierId(id);
	}

	@Override
	public void updateShippingFree(Double shippingFree, Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shippingFree", shippingFree);
		map.put("id", id);
		supplierDao.updateShippingFree(map);
		redis.del(SupplierService.REDIS_SUPPLIER + id);
	}

	@Override
	public Supplier getById(Long id) {

		//缓存中取出
	/*	String json = redis.getData(SupplierService.REDIS_SUPPLIER + id);
		if(!StringUtils.isEmpty(json)) {
			return JsonUtil.getObject(json, Supplier.class);
		} else {*/
			Supplier s = super.getById(id);
			redis.setData(SupplierService.REDIS_SUPPLIER + id, JsonUtil.toJson(s));
			return s;
		//}
	}

	@Override
	public void updatePeopleNumber(Supplier supplier) {
		supplierDao.updatePeopleNumber(supplier);
	}

	@Override
	public void updateFirmLogo(Supplier supplier) {
		supplierDao.updateFirmLogo(supplier);
		
	}

	
}
