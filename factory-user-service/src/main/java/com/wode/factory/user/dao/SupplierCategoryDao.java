/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.user.query.SupplierCategoryQuery;

public interface SupplierCategoryDao extends  EntityDao<SupplierCategory,Long>{
	public Page findPage(SupplierCategoryQuery query);

	/**
	 * 根据条件获取全部符合条件的数据
	 * @param map
	 * @return
	 */
	public List<SupplierCategory> findAll(Map map);
	public List<SupplierCategory> getBySupplierId(Long supplierId);
	/**
	 * 根据商家和商品分类确定一条数据
	 * @param supplierId
	 * @param categoryId
	 * @return
	 */
	public SupplierCategory getBySupplierAndCategory(Long supplierId,
			Long categoryId,Long ShopId);
}
