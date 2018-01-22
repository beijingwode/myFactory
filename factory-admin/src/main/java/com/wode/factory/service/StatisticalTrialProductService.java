package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.StatisticalTrialProduct;

/**
 *
 */
public interface StatisticalTrialProductService extends FactoryEntityService<StatisticalTrialProduct> {

	PageInfo<StatisticalTrialProduct> findList(Map<String, Object> params);


}
