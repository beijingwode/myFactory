package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.StatisticalBenefit;

/**
 * Created by zoln on 2015/7/24.
 */
public interface StatisticalBenefitDao extends  FactoryBaseDao<StatisticalBenefit> {

    List<StatisticalBenefit> countByDate(String countDate);

	List<StatisticalBenefit> selectByModel(Map<String, Object> params);
	List<StatisticalBenefit> selectByModelEmpty(Map<String, Object> params);	
}
