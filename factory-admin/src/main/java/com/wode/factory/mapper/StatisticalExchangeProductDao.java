package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.StatisticalExchangeProduct;

/**
 * Created by zoln on 2015/7/24.
 */
public interface StatisticalExchangeProductDao extends  FactoryBaseDao<StatisticalExchangeProduct> {

	List<StatisticalExchangeProduct> selectByModel(Map<String, Object> params);
	List<StatisticalExchangeProduct> selectByModelEmpty(Map<String, Object> params);
	
}
