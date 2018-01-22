package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.PageTemplateDao;
import com.wode.factory.model.PageTemplate;
import com.wode.factory.service.PageTemplateService;
@Service("pageTemplateService")
public class PageTemplateServiceImpl extends FactoryEntityServiceImpl<PageTemplate> implements PageTemplateService {
	
	@Autowired
	PageTemplateDao dao;
	
	@Override
	public PageTemplateDao getDao() {
		return dao;
	}
	
	@Override
	public Long getId(PageTemplate entity) {
		return -1L;
	}

	@Override
	public void setId(PageTemplate entity, Long id) {
	}

	@Override
	public void delete(Long pageTemplateId) {
		 dao.deleteById(pageTemplateId);
	}

	@Override
	public PageInfo<PageTemplate> findTemplateList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<PageTemplate> list = dao.findTemplateList(params);
		return new PageInfo<PageTemplate>(list);
	}

	@Override
	public void add(PageTemplate pojo) {
		 dao.add(pojo);
	}


}
