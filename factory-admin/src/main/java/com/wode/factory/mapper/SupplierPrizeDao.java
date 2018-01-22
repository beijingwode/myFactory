/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.SupplierPrize;

public interface SupplierPrizeDao extends FactoryBaseDao<SupplierPrize>{

	List<SupplierPrize> findInfoPageList(Map<String, Object> params);

}