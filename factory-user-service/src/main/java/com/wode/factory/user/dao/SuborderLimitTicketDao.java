/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.dao;

import com.wode.common.frame.base.FactoryBaseDao;
import com.wode.factory.model.SuborderLimitTicket;

public interface SuborderLimitTicketDao extends FactoryBaseDao<SuborderLimitTicket>{

	SuborderLimitTicket findBySuborderId(String subOrderId);

}