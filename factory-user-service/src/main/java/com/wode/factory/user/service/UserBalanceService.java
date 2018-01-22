/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.UserBalance;
import com.wode.factory.user.query.UserBalanceQuery;

import cn.org.rapid_framework.page.Page;

@Service("userBalanceService")
public interface UserBalanceService extends EntityService<UserBalance,Long>{
	

	
	public EntityDao getEntityDao();
	
	public Page findPage(UserBalanceQuery query);

	public UserBalance findByUserAndType(Long userId, Long currencyId);
	/**
	 * 获取用户余额
	 * @param id
	 * @param string
	 * @return
	 */
	public UserBalance findByUserAndName(Long userId, String currencyName);
	/**
	 * 根据用户查询所有的财富信息
	 * @param id
	 * @return
	 */
	public List<UserBalance> findByUser(Long userId);
	/**
	 * 根据用户查询平台余额信息
	 * @param id
	 * @return
	 */
	public UserBalance findMoneyByUser(Long userId);
	/**
	 * 使用平台余额支付
	 */
	public void balanceToPay(BigDecimal money,Long userId);
}
