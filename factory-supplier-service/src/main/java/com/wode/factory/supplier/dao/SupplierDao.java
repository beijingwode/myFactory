/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CheckOpinion;
import com.wode.factory.model.Supplier;
import com.wode.factory.supplier.query.SupplierQuery;

public interface SupplierDao extends  EntityDao<Supplier,Long>{
	public Page findPage(SupplierQuery query);
	public void saveOrUpdate(Supplier entity);
	public Supplier getByUserId(Long userId);
	public void updateEnter(Map<String, Object> reparm);
	public void updateShippingFree(Map<String, Object> reparm);
	public List<Supplier> getBymap(Map<String, Object> map);
	public List<CheckOpinion> getCheckOpinionListBySupplierId(Long id);
	public void updatePeopleNumber(Supplier supplier);
	public void updateFirmLogo(Supplier supplier);
	
}
