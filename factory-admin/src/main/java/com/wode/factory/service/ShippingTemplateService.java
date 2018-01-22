package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.ShippingTemplate;


public interface ShippingTemplateService{

	public List<ShippingTemplate> selectByModel(ShippingTemplate shippingTemplate);

	/**
	 * 列表查询
	 * @return
	 */
	public PageInfo<ShippingTemplate> findPage(Map<String, Object> params);
	
	public boolean update(ShippingTemplate shippingTemplate);
	
	public void delete(Long id);
	
}
