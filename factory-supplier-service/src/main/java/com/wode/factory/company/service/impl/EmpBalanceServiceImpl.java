package com.wode.factory.company.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.db.DBUtils;
import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.dao.EmpBalanceDao;
import com.wode.factory.company.service.EmpBalanceService;
import com.wode.factory.model.Currency;
import com.wode.factory.model.UserBalance;
import com.wode.factory.supplier.dao.CurrencyDao;

@Service("empBalanceService")
public class EmpBalanceServiceImpl extends BasePageServiceImpl<UserBalance>
		implements EmpBalanceService {

	@Autowired
	DBUtils dbUtils;
	@Autowired
	EmpBalanceDao empBalanceDao;
	
	@Autowired
	@Qualifier("currencyDao")
	private CurrencyDao currencyDao;

	@Override
	public UserBalance fetchByUserAndName(Long userId, String currencyName) {
		Currency currency = currencyDao.getByName(currencyName);
		if(currency!=null){
			UserBalance query = new UserBalance();
			query.setUserId(userId);
			query.setCurrencyId(currency.getId());
			UserBalance ub = empBalanceDao.findByUserAndCurrency(query);
			if(ub==null){
				ub = new UserBalance();
				ub.setId(dbUtils.CreateID());
				ub.setCurrencyId(currency.getId());
				ub.setUserId(userId);
				ub.setBalance(BigDecimal.ZERO);
				empBalanceDao.insert(ub);
			}
			return ub;
		}else
			return null;
	}

	@Override
	protected BasePageDao<UserBalance> getBaseDao() {
		return empBalanceDao;
	}

}
