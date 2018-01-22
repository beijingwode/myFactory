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
import com.wode.factory.model.ParameterGroup;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.supplier.query.ParameterGroupQuery;

public interface ParameterGroupService extends EntityService<ParameterGroup,Long>{
	

	
	public Page findPage(ParameterGroupQuery query);
	
	/**
	 * 根据商品类型id获取该类型的参数列表
	 * @param map
	 * @return
	 */
	public List<ParameterGroup> getParameterGrouplistByCategoryid(Map map);
	
}
