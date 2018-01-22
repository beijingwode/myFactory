/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Currency;
import com.wode.factory.model.UserBalance;
import com.wode.factory.user.dao.CurrencyDao;
import com.wode.factory.user.dao.UserBalanceDao;
import com.wode.factory.user.query.UserBalanceQuery;
import com.wode.factory.user.service.UserBalanceService;

import cn.org.rapid_framework.page.Page;

@Service("userBalanceService")
public class UserBalanceServiceImpl extends BaseService<UserBalance,java.lang.Long>  implements UserBalanceService{
	@Autowired
	@Qualifier("userBalanceDao")
	private UserBalanceDao dao;
	
	@Autowired
	@Qualifier("currencyDao")
	private CurrencyDao currencyDao;
	
	@Override
	public EntityDao getEntityDao() {
		return this.dao;
	}
	
	public Page findPage(UserBalanceQuery query) {
		return dao.findPage(query);
	}

	@Override
	public UserBalance findByUserAndType(Long userId, Long currencyId) {
		UserBalanceQuery query = new UserBalanceQuery();
		query.setUserId(userId);
		query.setCurrencyId(currencyId);
		UserBalance ub = dao.findBalanceByUser(query);
		return ub;
	}

	@Override
	public UserBalance findByUserAndName(Long userId, String currencyName) {
		Currency currency = currencyDao.getByName(currencyName);
		if(currency!=null){
			UserBalanceQuery query = new UserBalanceQuery();
			query.setUserId(userId);
			query.setCurrencyId(currency.getId());
			UserBalance ub = dao.findBalanceByUser(query);
			if(ub==null){
				ub = new UserBalance();
				ub.setCurrencyId(currency.getId());
				ub.setUserId(userId);
				ub.setBalance(BigDecimal.valueOf(0));
				dao.save(ub);
			}
			return ub;
		}else
			return null;
	}

	@Override
	public List<UserBalance> findByUser(Long userId) {
		// TODO Auto-generated method stub
		return dao.findByUser(userId);
	}
	@Override
	public UserBalance findMoneyByUser(Long userId) {
		// TODO Auto-generated method stub
		return dao.findMoneyByUser(userId);
	}

	@Override
	public void balanceToPay(BigDecimal money,Long userId) {
		// TODO Auto-generated method stub
		dao.updateBalanceByUser(money,userId);
	}
	
}
