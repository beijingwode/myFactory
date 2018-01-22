package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.StatisticalSale;

/**
 * Created by zoln on 2015/7/24.
 */
public interface StatisticalSaleDao extends  FactoryBaseDao<StatisticalSale> {

    List<StatisticalSale> countByDate(String countDate);

	List<StatisticalSale> selectByModel(Map<String, Object> params);
	List<StatisticalSale> selectByModelEmpty(Map<String, Object> params);	
}
