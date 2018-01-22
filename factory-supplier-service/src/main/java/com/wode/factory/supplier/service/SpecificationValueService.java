/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.SpecificationValue;
import com.wode.factory.supplier.query.SpecificationValueQuery;

import cn.org.rapid_framework.page.Page;

public interface SpecificationValueService extends EntityService<SpecificationValue,Long>{
	
	
	
	public Page findPage(SpecificationValueQuery query);
	
	public SpecificationValue findSpecificationValue(String categoryName,String speName, Long supplierId,int orders);

	public int copyFromOther(Long oId,Long nId);
	
	public List<SpecificationValue> findSpecificationValue(SpecificationValue speValue);
}
