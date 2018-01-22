/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.Specification;
import com.wode.factory.supplier.query.SpecificationQuery;

public interface SpecificationService extends EntityService<Specification,Long>{
	
	
	
	public Page findPage(SpecificationQuery query);
	
	/**
	 * 根据供应商id获取该供应商所有的用于生产sku的规格及其规格值 
	 * @return
	 */
	public List<Specification> getSpecificationlistByCategoryid(Map map);
	public List<Specification> selectByModel(Specification specification);
}
