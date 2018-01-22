/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.FactoryBaseDao;
import com.wode.factory.model.SupplierPrize;

public interface SupplierPrizeDao extends  FactoryBaseDao<SupplierPrize>{

	SupplierPrize findPrizeByMap(Map<String, Object> map);

	List<SupplierPrize> findPrizeListByMap(Map<String, Object> map);

}
