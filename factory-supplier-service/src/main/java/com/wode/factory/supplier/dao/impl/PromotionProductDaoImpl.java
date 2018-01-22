/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.supplier.dao.PromotionProductDao;
import com.wode.factory.supplier.query.PromotionProductQuery;

@Repository("promotionProductDao")
public class PromotionProductDaoImpl extends BaseDao<PromotionProduct,java.lang.Long> implements PromotionProductDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "PromotionProductMapper";
	}
	
	public void saveOrUpdate(PromotionProduct entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(PromotionProductQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	/**
	 * 根据商品SKU查询正在参加活动的商品列表（包括待审核、审核中、已通过）
	 */
	public List<PromotionProduct> findPromotionProductBySKU(Long skuID) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce() + ".findPromotionProductBySKU", skuID);
	}
	

}
