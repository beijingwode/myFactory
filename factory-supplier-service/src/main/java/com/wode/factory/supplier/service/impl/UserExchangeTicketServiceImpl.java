/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wode.factory.company.query.UserExchangeTicketVo;
import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.supplier.dao.UserExchangeTicketDao;
import com.wode.factory.supplier.service.UserExchangeTicketService;

@Service("userExchangeTicketService")
public class UserExchangeTicketServiceImpl extends BasePageServiceImpl<UserExchangeTicket> implements  UserExchangeTicketService{
	@Autowired
	private UserExchangeTicketDao dao;
	
	@Override
	protected UserExchangeTicketDao getBaseDao() {
		return dao;
	}

	@Override
	public int updateEnds(UserExchangeTicket entity) throws DataAccessException {
		return dao.updateEnds(entity);
	}

	@Override
	public List<UserExchangeTicketVo> findListByMap(Map<String, Object> map) {
		return dao.findListByMap(map);
	}
}
