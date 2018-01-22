/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.service;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.SuborderLimitTicket;
public interface SuborderLimitTicketService extends FactoryEntityService<SuborderLimitTicket>{

	SuborderLimitTicket findBySuborderId(String subOrderId);

}