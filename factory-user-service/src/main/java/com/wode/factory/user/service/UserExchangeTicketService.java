/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.UserExchangeTicket;

public interface UserExchangeTicketService extends EntityService<UserExchangeTicket,Long>{
	public List<UserExchangeTicket> selectByModel(UserExchangeTicket query);
	public List<UserExchangeTicket> selectExByUser(Long userId);
	public List<UserExchangeTicket> usingTicket(Long userId);
	public List<UserExchangeTicket> usingTicket(Long userId,Date date);
	public BigDecimal getShareAmout(Long userId);
}
