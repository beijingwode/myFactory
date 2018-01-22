package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.UserShareTicket;

/**
 *
 */
public interface UserShareTicketService extends FactoryEntityService<UserShareTicket> {

	UserShareTicket findByTicketIdBySuppliderId(Map prm);

	List<UserShareTicket> getByShareId(Long shareId);

}
