package com.wode.factory.mapper;

import java.util.Map;

import com.wode.factory.model.UserTicket;

/**
 * Created by zoln on 2015/7/24.
 */
public interface UserTicketDao extends  FactoryBaseDao<UserTicket> {
	void updateLimitByTicketId(Map<String,Object> map);
}
