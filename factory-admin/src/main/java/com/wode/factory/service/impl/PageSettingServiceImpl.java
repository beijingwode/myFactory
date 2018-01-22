package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.PageSettingDao;
import com.wode.factory.model.PageSetting;
import com.wode.factory.service.PageSettingService;
@Service("pageSettingService")
public class PageSettingServiceImpl extends FactoryEntityServiceImpl<PageSetting> implements PageSettingService {
	
	@Autowired
	PageSettingDao dao;
	
	@Override
	public PageSettingDao getDao() {
		return dao;
	}
	
	@Override
	public Long getId(PageSetting entity) {
		return -1L;
	}

	@Override
	public void setId(PageSetting entity, Long id) {
	}

	@Override
	public PageInfo<PageSetting> findPage(Map<String, Object> params) {

		PageHelper.startPage(params);
		List<PageSetting> list = dao.selectByModel(params);
		return new PageInfo<PageSetting>(list);
	}


}
