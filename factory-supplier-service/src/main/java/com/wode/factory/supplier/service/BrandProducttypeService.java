/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.BrandProducttype;
import com.wode.factory.supplier.query.BrandProducttypeQuery;

import cn.org.rapid_framework.page.Page;

public interface BrandProducttypeService extends EntityService<BrandProducttype,Long>{
	
	public EntityDao getEntityDao() ;
	
	public Page findPage(BrandProducttypeQuery query);

	public void removeByMap(Map<String, Object> map);

	public List<BrandProducttype> findAllByMap(Map<String, Object> map);

	public void deleteByShop(Long shopId);
	public void copyByShop(Long supplierId,Long shopId);
}
