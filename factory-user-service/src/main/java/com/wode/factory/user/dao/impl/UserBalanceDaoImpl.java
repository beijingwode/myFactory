/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.UserBalance;
import com.wode.factory.user.dao.UserBalanceDao;
import com.wode.factory.user.query.UserBalanceQuery;

import cn.org.rapid_framework.page.Page;

@Repository("userBalanceDao")
public class UserBalanceDaoImpl extends BaseDao<UserBalance,java.lang.Long> implements UserBalanceDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserBalanceMapper";
	}
	
	public void saveOrUpdate(UserBalance entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(UserBalanceQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	
	@Override
	public List<UserBalance> findByUser(Long userId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findByUser", userId);
	}
	@Override
	public UserBalance findMoneyByUser(Long userId) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findByUser1", userId);
	}
	
	@Override
	public UserBalance findBalanceByUser(UserBalanceQuery query) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findByUserAndCurrency", query);
	}
	
	@Override
	public void cutAmount(Long userId, Long currencyId,double amount) {
		Map param=new HashMap();
		param.put("userId", userId);
		param.put("currencyId", currencyId);
		amount=amount*-1;
		param.put("balance", amount);
		if(amount<0){
			int affectCount = getSqlSession().update(getIbatisMapperNamesapce()+".update", param);
		}
		
		
	}

	@Override
	public void addAmount(Long userId, Long currencyId, double amount) {
		UserBalanceQuery query = new UserBalanceQuery();
		query.setUserId(userId);
		query.setCurrencyId(currencyId);
		UserBalance userBalance = getSqlSession().selectOne(getIbatisMapperNamesapce()+".findByUserAndCurrency", query);
		Map param=new HashMap();
		param.put("userId", userId);
		param.put("currencyId", currencyId);
		param.put("balanceTotal", userBalance.getBalanceTotal());
		param.put("balance", userBalance.getBalance().intValue()+amount);
		if(amount>0){
			getSqlSession().update(getIbatisMapperNamesapce()+".update", param);
		}
		
	}

	@Override
	public void updateBalanceByUser(BigDecimal money,Long userId) {
		// TODO Auto-generated method stub
		Map param=new HashMap();
		param.put("money", money);
		param.put("userId", userId);
		getSqlSession().update(getIbatisMapperNamesapce()+".updateBalance",param);
	}
	

}
