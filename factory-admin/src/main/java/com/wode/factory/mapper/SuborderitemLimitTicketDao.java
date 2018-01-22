/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.mapper;

import com.wode.factory.model.SuborderitemLimitTicket;

public interface SuborderitemLimitTicketDao extends FactoryBaseDao<SuborderitemLimitTicket>{
	Integer selectProCnt(Long productId);
}