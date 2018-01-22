package com.wode.factory.company.service;

import com.wode.factory.model.UserBalance;


public interface EmpBalanceService extends BasePageService<UserBalance> {

	/**
	 * 查询用户余额
	 * @param id 用户id
	 * @param string 币种名称
	 * @return
	 */
	public UserBalance fetchByUserAndName(Long userId, String currencyName);
}
