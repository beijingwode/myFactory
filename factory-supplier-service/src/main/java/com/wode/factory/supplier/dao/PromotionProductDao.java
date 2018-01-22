/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import cn.org.rapid_framework.page.Page;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.supplier.query.PromotionProductQuery;

public interface PromotionProductDao extends  EntityDao<PromotionProduct,Long>{
	public Page findPage(PromotionProductQuery query);
	public void saveOrUpdate(PromotionProduct entity);
	// 根据商品SKU查询正在参加活动的商品列表（包括待审核、审核中、已通过）
	public List<PromotionProduct> findPromotionProductBySKU(Long skuID);
}
