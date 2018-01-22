package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.StatisticalFirstOrder;

/**
 * Created by zoln on 2015/7/24.
 */
public interface StatisticalFirstOrderDao extends  FactoryBaseDao<StatisticalFirstOrder> {

    List<StatisticalFirstOrder> countByTakeDate(String countDate);

	List<StatisticalFirstOrder> selectByModel(Map<String, Object> params);
	List<StatisticalFirstOrder> countSupplierByModel(Map<String, Object> params);
	List<StatisticalFirstOrder> countEnterpriseByModel(Map<String, Object> params);

	List<StatisticalFirstOrder> countEnterpriseEmpty(Map<String, Object> params);
	List<StatisticalFirstOrder> countSupplierEmpty(Map<String, Object> params);
	
}
