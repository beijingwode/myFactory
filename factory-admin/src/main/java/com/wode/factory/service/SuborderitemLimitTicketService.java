/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.service;

import com.wode.factory.model.SuborderitemLimitTicket;

public interface SuborderitemLimitTicketService extends FactoryEntityService<SuborderitemLimitTicket>{
	Integer selectProCnt(Long productId);

}