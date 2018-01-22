package com.wode.tongji.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.service.impl.FactoryEntityServiceImpl;
import com.wode.tongji.mapper.SmsTemplateMapper;
import com.wode.tongji.model.SmsTemplate;
import com.wode.tongji.service.SmsTemplateService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("smsTemplateService")
public class SmsTemplateServiceImpl extends FactoryEntityServiceImpl<SmsTemplate> implements SmsTemplateService {
	@Autowired
	SmsTemplateMapper dao;
	
	@Override
	public SmsTemplateMapper getDao() {
		return dao;
	}

	@Override
	public Long getId(SmsTemplate entity) {
		return -1L;	// 自增列 无需设置id
	}

	@Override
	public void setId(SmsTemplate entity, Long id) {
		entity.setId(id);
	}
}
