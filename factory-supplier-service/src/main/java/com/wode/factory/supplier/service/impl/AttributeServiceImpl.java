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
import com.wode.factory.model.Attribute;
import com.wode.factory.supplier.dao.AttributeDao;
import com.wode.factory.supplier.query.AttributeQuery;
import com.wode.factory.supplier.service.AttributeService;

@Service("attributeService")
public class AttributeServiceImpl extends BaseService<Attribute,java.lang.Long> implements  AttributeService{
	@Autowired
	@Qualifier("attributeDao")
	private AttributeDao attributeDao;
	
	public EntityDao getEntityDao() {
		return this.attributeDao;
	}
	
	public Page findPage(AttributeQuery query) {
		return attributeDao.findPage(query);
	}
	
	/**
	 * 根据商品类型id获取该商品类型对应的属性
	 * @param map
	 * @return
	 */
	public List<Attribute> getAttributelistByCategoryid(Map map){
		return attributeDao.getAttributelistByCategoryid(map);
	}
	
}
