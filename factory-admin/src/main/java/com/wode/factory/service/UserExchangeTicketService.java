package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.UserExchangeTicket;

/**
 *
 */
public interface UserExchangeTicketService extends FactoryEntityService<UserExchangeTicket> {

	public List<UserExchangeTicket> selectLeft(UserExchangeTicket query);
	
	public void updateEnds(UserExchangeTicket query);

	public PageInfo<UserExchangeTicket> findPageInfo(Map<String, Object> params);
}
