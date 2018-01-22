package com.wode.factory.supplier.dao;

import java.util.List;

import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.model.ShippingTemplateRule;

public interface ShippingTemplateRuleDao extends  BasePageDao<ShippingTemplateRule>{
//    int deleteByPrimaryKey(Long id);
//
//    int insert(ShippingTemplateRule record);
//
//    int insertSelective(ShippingTemplateRule record);
//
//    ShippingTemplateRule selectByPrimaryKey(Long id);
//
//    int updateByPrimaryKeySelective(ShippingTemplateRule record);
//
//    int updateByPrimaryKey(ShippingTemplateRule record);

	/**
	 * 
	 * @param list
	 * @return
	 */
	public int batchDelete(List<Long> list); 
	
	public int bathcAdd(List<ShippingTemplateRule> list);
}