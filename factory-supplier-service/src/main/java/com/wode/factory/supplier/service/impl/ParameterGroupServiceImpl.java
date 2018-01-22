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
import com.wode.factory.model.ParameterGroup;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.supplier.dao.ParameterGroupDao;
import com.wode.factory.supplier.query.ParameterGroupQuery;
import com.wode.factory.supplier.service.ParameterGroupService;

@Service("parameterGroupService")
public class ParameterGroupServiceImpl extends BaseService<ParameterGroup,java.lang.Long> implements  ParameterGroupService{
	@Autowired
	@Qualifier("parameterGroupDao")
	private ParameterGroupDao parameterGroupDao;
	
	public EntityDao getEntityDao() {
		return this.parameterGroupDao;
	}
	
	public Page findPage(ParameterGroupQuery query) {
		return parameterGroupDao.findPage(query);
	}
	
	/**
	 * 根据商品类型id获取该类型的参数列表
	 * @param map
	 * @return
	 */
	public List<ParameterGroup> getParameterGrouplistByCategoryid(Map map){
		return parameterGroupDao.getParameterGrouplistByCategoryid(map);
	}
	
}
