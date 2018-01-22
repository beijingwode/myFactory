/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.SupplierPrize;

public interface SupplierPrizeService extends FactoryEntityService<SupplierPrize>{

	SupplierPrize findPrizeByMap(Map<String, Object> map);

	List<SupplierPrize> findPrizeListByMap(Map<String, Object> map);

}
