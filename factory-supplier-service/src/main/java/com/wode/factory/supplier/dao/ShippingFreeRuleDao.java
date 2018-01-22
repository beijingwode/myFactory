package com.wode.factory.supplier.dao;

import java.util.List;

import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.model.ShippingFreeRule;

public interface ShippingFreeRuleDao extends  BasePageDao<ShippingFreeRule>{
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
	public void insert(ShippingFreeRule record);
	//public void update(ShippingFreeRule record);
	
	public int batchDelete(List<Long> list);

	public int batchAdd(List<ShippingFreeRule> list);
}