/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;


import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.company.query.ExchangeProductVo;
import com.wode.factory.model.ExchangeSuborder;

public interface ExchangeSuborderService extends FactoryEntityService<ExchangeSuborder>{

	Integer findReservedNumByMap(Map<String, Object> map);

	List<ExchangeProductVo> findExchangeSubOrderItemByMap(Map<String, Object> map);

	Integer findOrderTotalByMap(Map<String, Object> map);

}
