/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.SuborderLimitTicket;
import com.wode.factory.user.dao.SuborderLimitTicketDao;

@Repository("suborderLimitTicketDao")
public class SuborderLimitTicketDaoImpl extends  FactoryBaseDaoImpl<SuborderLimitTicket> implements SuborderLimitTicketDao{

    @Override
    public String getIbatisMapperNamesapce() {
        return "SuborderLimitTicketMapper";
    }

	@Override
	public SuborderLimitTicket findBySuborderId(String subOrderId) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".findBySuborderId",subOrderId);
	}
}