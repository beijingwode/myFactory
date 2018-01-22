package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.StatisticalManagerResult;

/**
 *
 */
public interface StatisticalManagerResultService extends FactoryEntityService<StatisticalManagerResult> {

	PageInfo<StatisticalManagerResult> findList(Map<String, Object> params);


}
