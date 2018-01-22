/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.wode.factory.supplier.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.SupplierImageResource;
import com.wode.factory.supplier.dao.SupplierImageResourceDao;
import com.wode.factory.supplier.query.SupplierImageResourceQuery;
import com.wode.factory.supplier.service.SupplierImageResourceService;

@Service("supplierImageResourceService")
public class SupplierImageResourceServiceImpl extends BaseService<SupplierImageResource,java.lang.Long> implements  SupplierImageResourceService{
	@Autowired
	@Qualifier("supplierImageResourceDao")
	private SupplierImageResourceDao supplierImageResourceDao;
	
	public EntityDao getEntityDao() {
		return this.supplierImageResourceDao;
	}

	@Override
	public PageInfo<SupplierImageResourceQuery> selectPageInfo(SupplierImageResourceQuery imageResourceQuery) {
		return this.supplierImageResourceDao.selectPageInfo(imageResourceQuery);
	}
	@Override
	public PageInfo<SupplierImageResourceQuery> findDateGroupBy(SupplierImageResourceQuery imageResourceQuery) {
		return this.supplierImageResourceDao.findDateGroupBy(imageResourceQuery);
	}

	@Override
	public Map<String, List<SupplierImageResource>> findPageImage(List<SupplierImageResourceQuery> dateS) {
		List<SupplierImageResource> list = new ArrayList<SupplierImageResource>();
		for(SupplierImageResourceQuery date:dateS){
			SupplierImageResource image = new SupplierImageResource();
			image.setDate(date.getDate());
			image.setSupplierId(date.getSupplierId());
			list.addAll(this.supplierImageResourceDao.findPageImage(image));
		}
		DateFormat f = new SimpleDateFormat("yyyy年MM月dd");
		Map<String,List<SupplierImageResource>> map = new LinkedHashMap<String, List<SupplierImageResource>>();
//		循环数据
		for(SupplierImageResource supplierImageResource:list){
			//key 对应日期存在
			if(map.containsKey(f.format(supplierImageResource.getDate()))){
				List<SupplierImageResource> listImage = map.get(f.format(supplierImageResource.getDate()));
				listImage.add(supplierImageResource);
			}else{
				List<SupplierImageResource> listImage = new ArrayList<SupplierImageResource>();
				listImage.add(supplierImageResource);
				map.put(f.format(supplierImageResource.getDate()), listImage);
			}
		}
		return map;
	}
}
