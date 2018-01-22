/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.model.SuborderLimitTicket;
import com.wode.factory.user.dao.SuborderLimitTicketDao;
import com.wode.factory.user.service.SuborderLimitTicketService;

@Service("suborderLimitTicketService")
public class SuborderLimitTicketServiceImpl extends  FactoryEntityServiceImpl<SuborderLimitTicket> implements SuborderLimitTicketService{

    @Autowired
    private SuborderLimitTicketDao dao;
    @Override
    public SuborderLimitTicketDao getDao() {
        return dao;
    }

    @Override
    public Long getId(SuborderLimitTicket entity) {
        return entity.getId();
    }

    @Override
    public void setId(SuborderLimitTicket entity, Long id) {
        if(entity!=null) {
            entity.setId(id);
        }
    }

	@Override
	public SuborderLimitTicket findBySuborderId(String subOrderId) {
		return dao.findBySuborderId(subOrderId);
	}

}