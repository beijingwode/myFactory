package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.UserLimitTicket;

public interface UserLimitTicketDao extends  FactoryBaseDao<UserLimitTicket>{

	List<UserLimitTicket> getpastLimitTicket(Long limitTicketId);

	List<UserLimitTicket> getUserLimitTicketByMap(Map<String, Long> map);

}
