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
import com.wode.factory.model.Attribute;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.supplier.query.AttributeQuery;

public interface AttributeService extends EntityService<Attribute,Long>{
	
	
	public Page findPage(AttributeQuery query);
	
	
	/**
	 * 根据商品类型id获取该商品类型对应的属性
	 * @param map
	 * @return
	 */
	public List<Attribute> getAttributelistByCategoryid(Map map);
	
}
