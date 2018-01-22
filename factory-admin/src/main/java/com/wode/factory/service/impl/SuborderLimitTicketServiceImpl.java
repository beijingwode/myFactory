/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.model.SuborderLimitTicket;
import com.wode.factory.mapper.SuborderLimitTicketDao;
import com.wode.factory.service.SuborderLimitTicketService;

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

}