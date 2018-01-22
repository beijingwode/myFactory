/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Promotion;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.user.dao.PromotionDao;
import com.wode.factory.user.query.PromotionQuery;

@Repository("promotionDao")
public class PromotionDaoImpl extends BaseDao<Promotion,java.lang.Long> implements PromotionDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "PromotionMapper";
	}
	
	public void saveOrUpdate(Promotion entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(PromotionQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public Integer findAllCount(Map<String, Object> map) {
		Number num = this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".findAllCount",map);
		return num.intValue();
	}

	@Override
	public List<Promotion> findByMap(Map<String, Object> map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findByMap",map);
	}

	@Override
	public Integer findAllProductCount(Map<String, Object> map) {
		Number num = this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".findAllProductCount",map);
		return num.intValue();
	}

	@Override
	public List<PromotionProduct> findProductByMap(Map<String, Object> map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findProductByMap",map);
	}
	

}
