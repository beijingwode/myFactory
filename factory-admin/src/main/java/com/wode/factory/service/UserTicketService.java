package com.wode.factory.service;

import java.util.Date;
import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.UserTicket;

/**
 *
 */
public interface UserTicketService extends EntityService<UserTicket,Long> {

	public List<UserTicket> selectByModel(UserTicket query);
	void updateLimitByTicketId(Long ticketId,Date ticketLimitDate);
}
