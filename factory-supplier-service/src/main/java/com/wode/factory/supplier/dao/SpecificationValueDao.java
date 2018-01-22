/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.SpecificationValue;
import com.wode.factory.supplier.query.SpecificationValueQuery;

public interface SpecificationValueDao extends  EntityDao<SpecificationValue,Long>{
	public Page findPage(SpecificationValueQuery query);
	public void saveOrUpdate(SpecificationValue entity);
	
	public SpecificationValue findSpecificationValue(String categoryName,String speName,Long supplierId,int orders);
	
	public List<SpecificationValue> findSpecificationValue(SpecificationValue speValue);
	
	public int copyFromOther(Long oId,Long nId);
}
