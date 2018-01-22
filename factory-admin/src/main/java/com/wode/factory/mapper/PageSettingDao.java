package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.PageSetting;

/**
 * Created by zoln on 2015/7/24.
 */
public interface PageSettingDao extends  FactoryBaseDao<PageSetting> {

	public List<PageSetting> selectByModel(Map<String, Object> params);

}
