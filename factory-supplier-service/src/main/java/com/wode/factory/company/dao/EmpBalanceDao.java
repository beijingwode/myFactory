package com.wode.factory.company.dao;

import com.wode.factory.model.UserBalance;

public interface EmpBalanceDao extends BasePageDao<UserBalance> {
	public UserBalance findByUserAndCurrency(UserBalance query);	
	public int insert(UserBalance userBalance);
}