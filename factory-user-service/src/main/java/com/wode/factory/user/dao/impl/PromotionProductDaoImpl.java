/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.user.dao.PromotionProductDao;
import com.wode.factory.user.query.PromotionProductQuery;
import com.wode.factory.user.vo.PromotionProductVo;

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
	
	public PageInfo<PromotionProductVo> findPage(PromotionProductQuery query) {
		List<PromotionProductVo> list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findPage", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
		return new PageInfo<PromotionProductVo>(list);
	}

	@Override
	public PromotionProductVo findById(PromotionProductQuery query) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".findPage", query);
	}

	@Override
	public PromotionProduct findByAttribute(PromotionProductQuery query) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".findByAttribute", query);
	}
	
	@Override
	public PromotionProduct findBySkuId(Long skuId) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".findBySkuId", skuId);
	}

	@Override
	public PromotionProduct findByCare(PromotionProductQuery query) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".findCare", query);
	}

	@Override
	public PromotionProductVo findOneCare(PromotionProductQuery query) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".findOneCare", query);
	}

}
