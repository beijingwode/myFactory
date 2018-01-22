package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.StatisticalManagerResult;

/**
 * Created by zoln on 2015/7/24.
 */
public interface StatisticalManagerResultDao extends  FactoryBaseDao<StatisticalManagerResult> {

	List<StatisticalManagerResult> selectByModel(Map<String, Object> params);
}
