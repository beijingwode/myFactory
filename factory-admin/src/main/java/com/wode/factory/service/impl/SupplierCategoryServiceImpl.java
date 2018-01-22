package com.wode.factory.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.SupplierCategoryMapper;
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.service.SupplierCategoryService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("supplierCategoryServiceImpl")
public class SupplierCategoryServiceImpl implements SupplierCategoryService {

	@Autowired
	SupplierCategoryMapper supplierCategoryMapper;

	@Override
	public void updateCommissionRatio(SupplierCategory supplierCate) {
		// TODO Auto-generated method stub
		this.supplierCategoryMapper.updateCommissionRatio(supplierCate);
	}

	@Override
	public List<SupplierCategory> findByMap(Map<String, Object> map) {
		return supplierCategoryMapper.findByMap(map);
	}

	@Override
	public void changShop(Long oldId,Long shopId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("oldId", oldId);
		map.put("shopId", shopId);
		
		supplierCategoryMapper.changShop(map);
	}
	
		
}
