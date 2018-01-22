package com.wode.factory.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.util.NumberUtil;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.service.ShippingTemplateService;

@Service("shippingTemplateServiceFactory")
public class ShippingTemplateServiceImpl implements ShippingTemplateService {
	
	@Autowired
	private Dao dao;

	@Override
	public ShippingTemplate selectTemplateBySupplierId(Long supplierId) {
		List<Record> result = dao.query("t_shipping_template",Cnd.where("supplier_id", "=", supplierId).and("version", "=", "2").and("is_audit", "=", "0"));
		if(result != null && result.size() > 0 ){
			Record record = result.get(0);
			ShippingTemplate shippingTemplate = new ShippingTemplate();
			shippingTemplate.setId(NumberUtil.toLong(record.get("id")));
			return shippingTemplate;
		}
		return null;
	}

	@Override
	public ShippingTemplate selectTemplateBySupplierIds(Long supplierId) {
		List<Record> result = dao.query("t_shipping_template",Cnd.where("supplier_id","=",supplierId).and("version", "=", "2"));
		if(result != null && result.size() > 0 ){
			Record record = result.get(0);
			ShippingTemplate shippingTemplate = new ShippingTemplate();
			shippingTemplate.setId(NumberUtil.toLong(record.get("id")));
			return shippingTemplate;
		}
		return null;
	}

}
