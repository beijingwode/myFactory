package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.ShippingTemplateDao;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.service.ShippingTemplateService;

@Service("shippingTemplateService")
public class ShippingTemplateServiceImpl implements ShippingTemplateService {
	
	@Autowired
	private ShippingTemplateDao shippingTemplateDao;

	@Override
	public List<ShippingTemplate> selectByModel(ShippingTemplate shippingTemplate) {
		return shippingTemplateDao.selectByModel(shippingTemplate);
	}

	@Override
	public PageInfo<ShippingTemplate> findPage(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ShippingTemplate> list = shippingTemplateDao.selectList(params);
		return new PageInfo<ShippingTemplate>(list);
	}

	@Transactional
	@Override
	public boolean update(ShippingTemplate shippingTemplate) {
		int i = shippingTemplateDao.update(shippingTemplate);
		return i > 0 ? true:false;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		shippingTemplateDao.deleteById(id);		
	}

}
