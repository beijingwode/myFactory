package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.StatisticalExchangeProduct;

/**
 *
 */
public interface StatisticalExchangeProductService extends FactoryEntityService<StatisticalExchangeProduct> {

	PageInfo<StatisticalExchangeProduct> findList(Map<String, Object> params);

}
