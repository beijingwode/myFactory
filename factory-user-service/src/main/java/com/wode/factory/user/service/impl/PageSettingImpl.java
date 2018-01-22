/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.model.PageSetting;
import com.wode.factory.user.dao.PageSettingDao;
import com.wode.factory.user.service.PageSettingService;

@Service("pageSettingService")
public class PageSettingImpl extends FactoryEntityServiceImpl<PageSetting> implements  PageSettingService{
	@Autowired
	private PageSettingDao dao;

	@Override
	public PageSettingDao getDao() {
		return dao;
	}

	@Override
	public Long getId(PageSetting entity) {
		return entity.getId();
	}

	@Override
	public void setId(PageSetting entity, Long id) {
		entity.setId(id);
	}
	
}
