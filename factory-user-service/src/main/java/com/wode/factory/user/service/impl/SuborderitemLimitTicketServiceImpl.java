/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.model.SuborderitemLimitTicket;
import com.wode.factory.user.dao.SuborderitemLimitTicketDao;
import com.wode.factory.user.service.SuborderitemLimitTicketService;

@Service("suborderitemLimitTicketService")
public class SuborderitemLimitTicketServiceImpl extends  FactoryEntityServiceImpl<SuborderitemLimitTicket> implements SuborderitemLimitTicketService{

    @Autowired
    private SuborderitemLimitTicketDao dao;
    @Override
    public SuborderitemLimitTicketDao getDao() {
        return dao;
    }

    @Override
    public Long getId(SuborderitemLimitTicket entity) {
        return entity.getId();
    }

    @Override
    public void setId(SuborderitemLimitTicket entity, Long id) {
        if(entity!=null) {
            entity.setId(id);
        }
    }

	@Override
	public List<SuborderitemLimitTicket> findBySuborderId(String subOrderId) {
		return dao.findBySuborderId(subOrderId);
	}

}