package com.wode.factory.supplier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.ShippingTemplateRule;
import com.wode.factory.supplier.dao.ShippingTemplateRuleDao;
import com.wode.factory.supplier.service.ShippingTemplateRuleService;

@Service("supplierShippingTemplateRuleService")
public class ShippingTemplateRuleServiceImpl extends BasePageServiceImpl<ShippingTemplateRule>
		implements ShippingTemplateRuleService {

	@Autowired
	private ShippingTemplateRuleDao dao;
	
	@Override
	protected BasePageDao<ShippingTemplateRule> getBaseDao() {
		return dao;
	}

	@Override
	public boolean batchDelete(List<Long> list) {
		if(null != list && !list.isEmpty()){
			int i = dao.batchDelete(list);
			if(i>0){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean batchAdd(List<ShippingTemplateRule> list) {
		if(null != list && !list.isEmpty()){
			int i = dao.bathcAdd(list);
			if(i>0){
				return true;
			}
		}
		return false;
	}

}
