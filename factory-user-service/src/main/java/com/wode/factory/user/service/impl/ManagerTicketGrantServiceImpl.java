/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.user.dao.ManagerTicketGrantDao;
import com.wode.factory.user.model.ManagerTicketGrant;
import com.wode.factory.user.service.ManagerTicketGrantService;

@Service("managerTicketGrantService")
public class ManagerTicketGrantServiceImpl extends FactoryEntityServiceImpl<ManagerTicketGrant> implements  ManagerTicketGrantService{
	@Autowired
	private ManagerTicketGrantDao dao;
	
	@Override
	public ManagerTicketGrantDao getDao() {
		return dao;
	}

	@Override
	public Long getId(ManagerTicketGrant entity) {
		return entity.getId();
	}

	@Override
	public void setId(ManagerTicketGrant entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}

}