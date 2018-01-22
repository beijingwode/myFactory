/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.dao;

import java.util.List;

import com.wode.common.frame.base.FactoryBaseDao;
import com.wode.factory.model.SuborderitemLimitTicket;

public interface SuborderitemLimitTicketDao extends FactoryBaseDao<SuborderitemLimitTicket>{

	List<SuborderitemLimitTicket> findBySuborderId(String subOrderId);

}