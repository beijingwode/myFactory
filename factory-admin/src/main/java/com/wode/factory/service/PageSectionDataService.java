package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.PageSectionData;

/**
 *
 */
public interface PageSectionDataService extends FactoryEntityService<PageSectionData> {

	PageInfo<PageSectionData> findPage(Map<String, Object> params);

	void copyPageData(Map<String, Object> params);
}
