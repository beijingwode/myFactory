package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.UserShareTicket;

/**
 * Created by zoln on 2015/7/24.
 */
public interface UserShareTicketDao extends  FactoryBaseDao<UserShareTicket> {

	UserShareTicket findByTicketIdBySuppliderId(Map prm);

	List<UserShareTicket> getByShareId(Long shareId);
}
