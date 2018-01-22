/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.BrandProducttype;
import com.wode.factory.supplier.query.BrandProducttypeQuery;

public interface BrandProducttypeDao extends  EntityDao<BrandProducttype,Long>{
	public Page findPage(BrandProducttypeQuery query);
	public void saveOrUpdate(BrandProducttype entity);
	public void removeByMap(Map<String, Object> map);
	public List<BrandProducttype> findAllByMap(Map<String, Object> map);

	public void deleteByShop(Long shopId);
	public void copyByShop(Map<String,Long> map);
}
