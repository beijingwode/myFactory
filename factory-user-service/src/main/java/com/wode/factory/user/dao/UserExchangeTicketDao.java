/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.math.BigDecimal;
import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.UserExchangeTicket;

public interface UserExchangeTicketDao extends  EntityDao<UserExchangeTicket,Long>{
	public List<UserExchangeTicket> selectByModel(UserExchangeTicket query);
	public BigDecimal getShareAmout(Long userId);
}
