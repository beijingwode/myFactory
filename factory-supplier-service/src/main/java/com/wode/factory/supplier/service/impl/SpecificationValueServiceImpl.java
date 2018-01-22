/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.SpecificationValue;
import com.wode.factory.supplier.dao.SpecificationValueDao;
import com.wode.factory.supplier.query.SpecificationValueQuery;
import com.wode.factory.supplier.service.SpecificationValueService;

@Service("specificationValueService")
public class SpecificationValueServiceImpl extends BaseService<SpecificationValue,java.lang.Long> implements  SpecificationValueService{
	@Autowired
	@Qualifier("specificationValueDao")
	private SpecificationValueDao specificationValueDao;
	
	public EntityDao getEntityDao() {
		return this.specificationValueDao;
	}
	
	public Page findPage(SpecificationValueQuery query) {
		return specificationValueDao.findPage(query);
	}

	@Override
	public SpecificationValue findSpecificationValue(String categoryName,
			String speName, Long supplierId,int orders) {
		return this.specificationValueDao.findSpecificationValue(categoryName, speName, supplierId,orders);
	}

	@Override
	public int copyFromOther(Long oId, Long nId) {
		return this.specificationValueDao.copyFromOther(oId, nId);
	}

	@Override
	public List<SpecificationValue> findSpecificationValue(SpecificationValue speValue) {
		return this.specificationValueDao.findSpecificationValue(speValue);
	}
	
}
