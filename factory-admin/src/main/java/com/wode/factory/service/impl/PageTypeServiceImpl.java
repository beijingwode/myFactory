
package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.PageTypeDao;
import com.wode.factory.model.PageTypeSetting;
import com.wode.factory.service.PageTypeService;

@Service("pageTypeService")
public class PageTypeServiceImpl implements PageTypeService {
	
	@Autowired
	private PageTypeDao pageTypeDao;

	@Override
	public Integer add(PageTypeSetting pojo) {
		 return pageTypeDao.add(pojo);
	}

	@Override
	public Integer update(PageTypeSetting pojo) {
		return pageTypeDao.update(pojo);
	}

	@Override
	public Integer delete(Long pageTypeId) {
		 return pageTypeDao.delete(pageTypeId);
	}

	@Override
	public PageInfo<PageTypeSetting> findTypeList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<PageTypeSetting> list = pageTypeDao.findTypeList(params);
		return new PageInfo<PageTypeSetting>(list);
	}

	@Override
	public PageTypeSetting getById(Long pageTypeId) {
		return pageTypeDao.getById(pageTypeId);
	}

	@Override
	public List<PageTypeSetting> selectPageType(PageTypeSetting data) {
		return pageTypeDao.selectByChannelId(data);
	}
	
}
