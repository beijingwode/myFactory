package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.PageTemplate;

/**
 *
 */
public interface PageTemplateService extends FactoryEntityService<PageTemplate> {

	void delete(Long pageTemplateId);
	void add(PageTemplate pojo);
	
	/**
	 * 分页查询模板
	 * @param params
	 * @return
	 */
	PageInfo<PageTemplate> findTemplateList(Map<String, Object> params);

}
