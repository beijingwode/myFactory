package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.UserLimitTicket;

public interface UserLimitTicketService extends FactoryEntityService<UserLimitTicket>{

	List<UserLimitTicket> getpastLimitTicket(Long limitTicketId);

	List<UserLimitTicket> getUserLimitTicketByMap(Map<String, Long> map);

}
