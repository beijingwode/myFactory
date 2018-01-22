/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.SupplierPrize;

public interface SupplierPrizeService extends FactoryEntityService<SupplierPrize>{

	PageInfo<SupplierPrize> findInfoPageList(Map<String, Object> params);

}