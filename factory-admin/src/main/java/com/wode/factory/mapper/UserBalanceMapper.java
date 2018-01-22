/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import java.math.BigDecimal;
import java.util.Map;

import com.wode.factory.model.UserBalance;

public interface UserBalanceMapper{
	void update(UserBalance entity);

	UserBalance findByUserAndCurrencyId(Map<String,Object> map);

	BigDecimal selectBalanceByDate(String lastMonth);

	void deleteByUserId(Long userId);
}
