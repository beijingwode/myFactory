package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.UserExchangeTicketDao;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.service.UserExchangeTicketService;
import com.wode.factory.vo.SupplierExchangeProductVo;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("userExchangeTicketService")
public class UserExchangeTicketServiceImpl extends FactoryEntityServiceImpl<UserExchangeTicket> implements UserExchangeTicketService {
	@Autowired
	UserExchangeTicketDao dao;
	
	@Override
	public UserExchangeTicketDao getDao() {
		return dao;
	}


	@Override
	public Long getId(UserExchangeTicket entity) {
		return entity.getId();
	}

	@Override
	public void setId(UserExchangeTicket entity, Long id) {
		entity.setId(id);
	}


	@Override
	public List<UserExchangeTicket> selectLeft(UserExchangeTicket query) {
		return dao.selectLeft(query);
	}

	@Override
	public void updateEnds(UserExchangeTicket entity) {
		dao.updateEnds(entity);
	}


	@Override
	public PageInfo<UserExchangeTicket> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<UserExchangeTicket> listSaleBill = dao.findPageInfo(params);
		return new PageInfo(listSaleBill);
	}
}
