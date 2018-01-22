/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Specification;
import com.wode.factory.supplier.dao.SpecificationDao;
import com.wode.factory.supplier.query.SpecificationQuery;
import com.wode.factory.supplier.service.SpecificationService;

@Service("specificationService")
public class SpecificationServiceImpl extends BaseService<Specification,java.lang.Long> implements  SpecificationService{
	@Autowired
	@Qualifier("specificationDao")
	private SpecificationDao specificationDao;
	
	public EntityDao getEntityDao() {
		return this.specificationDao;
	}
	
	public Page findPage(SpecificationQuery query) {
		return specificationDao.findPage(query);
	}
	
	/**
	 * 根据供应商id获取该供应商所有的用于生产sku的规格及其规格值 
	 * @return
	 */
	public List<Specification> getSpecificationlistByCategoryid(Map map){
		return specificationDao.getSpecificationlistByCategoryid(map);
	}

	@Override
	public List<Specification> selectByModel(Specification specification) {
		return this.specificationDao.selectByModel(specification);
	}
}
