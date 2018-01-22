/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.SuborderitemLimitTicket;
import com.wode.factory.user.dao.SuborderitemLimitTicketDao;

@Repository("suborderitemLimitTicketDao")
public class SuborderitemLimitTicketDaoImpl extends  FactoryBaseDaoImpl<SuborderitemLimitTicket> implements SuborderitemLimitTicketDao{

    @Override
    public String getIbatisMapperNamesapce() {
        return "SuborderitemLimitTicketMapper";
    }

	@Override
	public List<SuborderitemLimitTicket> findBySuborderId(String subOrderId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce() + ".findBySuborderId",subOrderId);
	}
}