/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.service;

import java.util.List;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.SuborderitemLimitTicket;
public interface SuborderitemLimitTicketService extends FactoryEntityService<SuborderitemLimitTicket>{

	List<SuborderitemLimitTicket> findBySuborderId(String subOrderId);

}