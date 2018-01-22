package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.StatisticalTrialProduct;

/**
 * Created by zoln on 2015/7/24.
 */
public interface StatisticalTrialProductDao extends  FactoryBaseDao<StatisticalTrialProduct> {

	List<StatisticalTrialProduct> selectByModel(Map<String, Object> params);
	List<StatisticalTrialProduct> selectByModelEmpty(Map<String, Object> params);
}
