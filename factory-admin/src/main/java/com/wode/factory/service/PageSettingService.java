package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.PageSetting;

/**
 *
 */
public interface PageSettingService extends FactoryEntityService<PageSetting> {

	PageInfo<PageSetting> findPage(Map<String, Object> params);
}
