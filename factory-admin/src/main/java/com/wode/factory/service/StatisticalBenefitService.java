package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.StatisticalBenefit;

/**
 *
 */
public interface StatisticalBenefitService extends FactoryEntityService<StatisticalBenefit> {

	PageInfo<StatisticalBenefit> findList(Map<String, Object> params);

}
