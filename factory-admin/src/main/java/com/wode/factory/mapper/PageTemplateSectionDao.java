package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.PageTemplateSection;

/**
 * Created by zoln on 2015/7/24.
 */
public interface PageTemplateSectionDao extends  FactoryBaseDao<PageTemplateSection> {
	/**
	 * 分页面查询
	 * @param params
	 * @return
	 */
	List<PageTemplateSection> findPageTemplateSectionList(Map<String, Object> params);
}
