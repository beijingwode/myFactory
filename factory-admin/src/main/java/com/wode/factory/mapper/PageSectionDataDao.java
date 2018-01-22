package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.PageSectionData;

/**
 * Created by zoln on 2015/7/24.
 */
public interface PageSectionDataDao extends  FactoryBaseDao<PageSectionData> {
	public List<PageSectionData> selectPage(Map<String, Object> params);

	public void copyPageData(Map<String, Object> params);
}
