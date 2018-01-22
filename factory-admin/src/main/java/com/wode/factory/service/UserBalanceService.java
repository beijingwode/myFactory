/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import com.wode.factory.model.UserBalance;

public interface UserBalanceService{
	/**
	 * 退货成功之后退款
	 * @param refundOrderId
	 * @param userId
	 * @param i
	 * @param string
	 * @return
	 */
//	ActResult<String> refundToUser(Long refundOrderId, Long userId, int i,
//			String string);
//	

	public UserBalance findByUserAndCurrencyId(Long userId, Long currencyId);
	public void saveOrUpdate(UserBalance entity);

	void deleteByUserId(Long userId);
}
