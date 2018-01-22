/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.user.dao.UserExchangeTicketDao;
import com.wode.factory.user.service.UserExchangeTicketService;

@Service("userExchangeTicketService")
public class UserExchangeTicketServiceImpl extends BaseService<UserExchangeTicket,java.lang.Long> implements  UserExchangeTicketService{
	@Autowired
	private UserExchangeTicketDao dao;
	
	public UserExchangeTicketDao getEntityDao() {
		return this.dao;
	}

	@Override
	public List<UserExchangeTicket> selectByModel(UserExchangeTicket query) {
		return dao.selectByModel(query);
	}

	public List<UserExchangeTicket> selectExByUser(Long userId) {
		UserExchangeTicket query = new UserExchangeTicket();
		query.setUserId(userId);
		return selectByModel(query);
	}
	@Override
	public List<UserExchangeTicket> usingTicket(Long userId) {
		return this.usingTicket(userId, new Date());
	}

	@Override
	public BigDecimal getShareAmout(Long userId) {
		return dao.getShareAmout(userId);
	}

	@Override
	public List<UserExchangeTicket> usingTicket(Long userId, Date date) {
		UserExchangeTicket query=new UserExchangeTicket();
		query.setUserId(userId);
		query.setLimitEnd(date);
		return dao.selectByModel(query);
	}

}