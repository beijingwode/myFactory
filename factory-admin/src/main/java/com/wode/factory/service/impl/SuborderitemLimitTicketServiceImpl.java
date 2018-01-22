/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.SuborderitemLimitTicketDao;
import com.wode.factory.model.SuborderitemLimitTicket;
import com.wode.factory.service.SuborderitemLimitTicketService;

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
	public Integer selectProCnt(Long productId) {
		return dao.selectProCnt(productId);
	}

}