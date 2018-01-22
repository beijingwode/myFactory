package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.PageTemplate;

/**
 * Created by zoln on 2015/7/24.
 */
public interface PageTemplateDao extends  FactoryBaseDao<PageTemplate> {

	List<PageTemplate> findTemplateList(Map<String, Object> params);

	void add(PageTemplate pojo);

}
