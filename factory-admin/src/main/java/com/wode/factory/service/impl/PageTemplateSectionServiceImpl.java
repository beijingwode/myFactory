package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.PageTemplateSectionDao;
import com.wode.factory.model.PageTemplateSection;
import com.wode.factory.service.PageTemplateSectionService;
@Service("pageTemplateSectionService")
public class PageTemplateSectionServiceImpl extends FactoryEntityServiceImpl<PageTemplateSection> implements PageTemplateSectionService {
	
	@Autowired
	PageTemplateSectionDao dao;
	
	@Override
	public PageTemplateSectionDao getDao() {
		return dao;
	}
	
	@Override
	public Long getId(PageTemplateSection entity) {
		return -1L;
	}

	@Override
	public void setId(PageTemplateSection entity, Long id) {
	}

	@Override
	public PageInfo<PageTemplateSection> findPageTemplateSectionList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<PageTemplateSection> list = dao.findPageTemplateSectionList(params);
		return new PageInfo<PageTemplateSection>(list);
	}


}
