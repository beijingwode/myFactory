package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ShippingTemplate;

public interface ShippingTemplateDao  extends  EntityDao<ShippingTemplate,Long>{
	
	public List<ShippingTemplate> selectByModel(ShippingTemplate shippingTemplate);
	
	public List<ShippingTemplate> selectList(Map<String, Object> params);

	public void deleteBySupplierId(Long supplierId);
}