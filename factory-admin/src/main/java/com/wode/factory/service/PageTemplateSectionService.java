package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.PageTemplateSection;

/**
 *
 */
public interface PageTemplateSectionService extends FactoryEntityService<PageTemplateSection> {
	/**
	 * 分页查询位置信息数据
	 * @param params
	 * @return
	 */
	PageInfo<PageTemplateSection> findPageTemplateSectionList(Map<String, Object> params);

}
