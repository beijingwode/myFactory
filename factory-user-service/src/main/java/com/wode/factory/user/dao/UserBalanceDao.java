/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.math.BigDecimal;
import java.util.List;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.UserBalance;
import com.wode.factory.user.query.UserBalanceQuery;

public interface UserBalanceDao extends  EntityDao<UserBalance,Long>{
	
	public Page findPage(UserBalanceQuery query);
	
	/**
	 * 减财富
	 * @param userId
	 * 用户id
	 * @param currencyId
	 *  货币类型id
	 * @param amount
	 * 使用数量（减去数量）
	 */
	public void cutAmount(Long userId, Long currencyId,double amount);
	
	/**
	 * 添加财富
	 * @param userId
	 * @param currencyId
	 * @param amount
	 */
	public void addAmount(Long userId, Long currencyId,double amount);
	
	
	/**
	 * 查询用户货币余额（目前只有企业券）
	 * @param userId
	 * @return
	 */
	public List<UserBalance> findByUser(Long userId);
	/**
	 * 查询用户平台账户余额
	 * @param userId
	 * @return
	 */
	public UserBalance findMoneyByUser(Long userId);
	/**
	 * 查询用户某种货币余额
	 * @param query
	 * @return
	 */
	public UserBalance findBalanceByUser(UserBalanceQuery query);

	/**
	 * 使用平台余额支付
	 * @param userId
	 * @return
	 */
	public void updateBalanceByUser(BigDecimal totalFee,Long userId);
}
