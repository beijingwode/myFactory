/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.facade.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.util.StringUtils;
import com.wode.factory.model.SpecificationValue;
import com.wode.factory.model.SupplierSpecification;
import com.wode.factory.supplier.facade.SupplierSpecificationFacade;
import com.wode.factory.supplier.query.SpecificationValueQuery;
import com.wode.factory.supplier.service.ProductSpecificationValueService;
import com.wode.factory.supplier.service.SpecificationValueService;
import com.wode.factory.supplier.service.SupplierSpecificationService;

import cn.org.rapid_framework.page.Page;

@Service("supplierSpecificationFacade")
public class SupplierSpecificationFacadeImpl implements SupplierSpecificationFacade {

	@Autowired
	private SupplierSpecificationService supplierSpecificationService;

	@Autowired
	private SpecificationValueService specificationValueService;
	@Autowired
	@Qualifier("productSpecificationValueService")
	private ProductSpecificationValueService productSpecificationValueService;
	
	@Override
	@Transactional
	public void SaveSupplierSpecification(Long supplierId, Long categoryId,
			SupplierSpecification specification1, List<SpecificationValue> values1, SupplierSpecification specification2,
			List<SpecificationValue> values2) { 
		SupplierSpecification record = new SupplierSpecification();
		record.setCategoryId(categoryId);
		record.setSupplierId(supplierId);
		record.setType(2);

		/////////////////////////////////////////////////////////////////////
		//规格1处理
		specification1.setCategoryId(categoryId);
		specification1.setSupplierId(supplierId);
		specification1.setUpdateDate(new Date());
		List<SupplierSpecification> lst= supplierSpecificationService.selectByModel(record);
		
		//规格保存
		if(lst!=null && lst.size() > 0) {
			specification1.setId(lst.get(0).getId());
		}
		supplierSpecificationService.saveOrUpdate(specification1);
		
		//规格值保存
		for (int i=0;i<values1.size();i++) {
			values1.get(i).setSpecificationId(specification1.getId());
			values1.get(i).setOrders(i+1);
			specificationValueService.saveOrUpdate(values1.get(i));
		}
		
		//无用规格值删除
		SpecificationValueQuery query = new SpecificationValueQuery();
		query.setPageSize(100);
		query.setSpecificationId(specification1.getId());
		Page p = specificationValueService.findPage(query);
		for (Object object : p) {
			SpecificationValue sv = (SpecificationValue)object;
			boolean del=true;
			for (SpecificationValue v : values1) {
				if(v.getId().equals(sv.getId())) {
					del=false;
					break;
				}
			}
			
			if(del) {
				specificationValueService.removeById(sv.getId());
			}
		}
		/////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////
		//规格2处理
		if(specification2 != null) {

			specification2.setCategoryId(categoryId);
			specification2.setSupplierId(supplierId);
			specification2.setUpdateDate(new Date());
			
			//规格保存
			if(lst!=null && lst.size() > 1) {
				specification2.setId(lst.get(1).getId());
			}
			supplierSpecificationService.saveOrUpdate(specification2);
			
			//规格值保存
			for (int i=0;i<values2.size();i++) {
				values2.get(i).setSpecificationId(specification2.getId());
				values2.get(i).setOrders(i+1);
				specificationValueService.saveOrUpdate(values2.get(i));
			}
			
			//无用规格值删除
			SpecificationValueQuery query2 = new SpecificationValueQuery();
			query2.setPageSize(100);
			query2.setSpecificationId(specification2.getId());
			Page p2 = specificationValueService.findPage(query2);
			for (Object object : p2) {
				SpecificationValue sv = (SpecificationValue)object;
				boolean del=true;
				for (SpecificationValue v : values2) {
					if(v.getId().equals(sv.getId())) {
						del=false;
						break;
					}
				}
				
				if(del) {
					specificationValueService.removeById(sv.getId());
				}
			}
		} else {
			if(lst!=null && lst.size() > 1) {
				supplierSpecificationService.removeById(lst.get(1).getId());
				
				SpecificationValueQuery query2 = new SpecificationValueQuery();
				query2.setPageSize(100);
				query2.setSpecificationId(lst.get(1).getId());
				Page p2 = specificationValueService.findPage(query2);
				for (Object object : p2) {
					SpecificationValue sv = (SpecificationValue)object;
					specificationValueService.removeById(sv.getId());
				}
			}
			
		}
		/////////////////////////////////////////////////////////////////////
		
	}

	@Override
	@Transactional
	public void copySupplierSpecification(Long supplierId, Long oldId, Long newId, Long productId,Integer type) {
		if(oldId.equals(newId)) return;

		SupplierSpecification record = new SupplierSpecification();
		record.setCategoryId(oldId);
		record.setSupplierId(supplierId);
		record.setType(type);

		/////////////////////////////////////////////////////////////////////
		//copy 规格
		List<SupplierSpecification> oldlst= supplierSpecificationService.selectByModel(record);
		if(oldlst==null||oldlst.isEmpty()) return;
		
		record.setCategoryId(newId);
		List<SupplierSpecification> newlst= supplierSpecificationService.selectByModel(record);
		SupplierSpecification kingaku1 = null;
		SupplierSpecification kingaku2 = null;
		Long old1 = null;
		Long old2 = null;
		if(newlst!=null  && !newlst.isEmpty()) {
			//规格数不相等
			if(oldlst.size() != newlst.size()) {
				return;
			}
			
			//规格名称不相等
			if(oldlst.size()==1) {
				if(!oldlst.get(0).getName().equals(newlst.get(0).getName())) return;
				old1 = oldlst.get(0).getId();
				kingaku1=newlst.get(0);
			} else {
				if(!oldlst.get(0).getName().equals(newlst.get(0).getName()) || !oldlst.get(1).getName().equals(newlst.get(1).getName())) return;
				old1 = oldlst.get(0).getId();
				old2 = oldlst.get(1).getId();
				kingaku1=newlst.get(0);
				kingaku2=newlst.get(1);
			}
		} else {
			if(oldlst.size()==1) {
				old1 = oldlst.get(0).getId();
				kingaku1=oldlst.get(0);
				kingaku1.setId(null);
				kingaku1.setCategoryId(newId);
				supplierSpecificationService.saveOrUpdate(kingaku1);
			} else {
				old1 = oldlst.get(0).getId();
				old2 = oldlst.get(1).getId();
				kingaku1=oldlst.get(0);
				kingaku1.setId(null);
				kingaku1.setCategoryId(newId);
				kingaku2=oldlst.get(1);
				kingaku2.setId(null);
				kingaku2.setCategoryId(newId);
				supplierSpecificationService.saveOrUpdate(kingaku1);
				supplierSpecificationService.saveOrUpdate(kingaku2);
			}
		}
		/////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////
		//copy 规格1 value
		specificationValueService.copyFromOther(old1, kingaku1.getId());
		//copy 规格1 商品选择
		productSpecificationValueService.updateFromOther(productId, old1, kingaku1.getId());
		//copy 规格1 商品选择
		productSpecificationValueService.copyFromOther(productId, old1, kingaku1.getId());
		// 作废商品规格值
		Map delMap = new HashMap();
		delMap.put("productid", productId);
		delMap.put("specificationId", old1);
		productSpecificationValueService.removeAllByProductid(delMap);
		/////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////
		if(kingaku2!=null) {
			//copy 规格1 value
			specificationValueService.copyFromOther(old2, kingaku2.getId());
			//copy 规格1 商品选择
			productSpecificationValueService.updateFromOther(productId, old2, kingaku2.getId());
			//copy 规格2 商品选择
			productSpecificationValueService.copyFromOther(productId, old2, kingaku2.getId());
			// 作废商品规格值
			delMap.put("specificationId", old2);
			productSpecificationValueService.removeAllByProductid(delMap);
		}
		/////////////////////////////////////////////////////////////////////
		
	}


}
