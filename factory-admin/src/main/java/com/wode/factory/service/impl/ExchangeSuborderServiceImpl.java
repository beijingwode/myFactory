package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.ExchangeSuborderDao;
import com.wode.factory.model.ExchangeOrders;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.ExchangeSuborderitem;
import com.wode.factory.model.UserExchangeFavorites;
import com.wode.factory.service.ExchangeSuborderService;
import com.wode.factory.vo.ExchangeSuborderVo;
import com.wode.factory.vo.GroupSubOrderVo;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("exchangeSuborderService")
public class ExchangeSuborderServiceImpl extends FactoryEntityServiceImpl<ExchangeSuborder> implements ExchangeSuborderService {
	@Autowired
	ExchangeSuborderDao dao;
	
	@Override
	public ExchangeSuborderDao getDao() {
		return dao;
	}


	@Override
	public Long getId(ExchangeSuborder entity) {
		return -1L;
	}

	@Override
	public void setId(ExchangeSuborder entity, Long id) {
	}

	@Override
	public PageInfo<ExchangeSuborder> getPageList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ExchangeSuborder> list = dao.selectByModel(params);
		return new PageInfo<ExchangeSuborder>(list);
	}

	@Override
	public ExchangeSuborder getById(String id) {
		return dao.getById(id);
	}

	@Override
	public ExchangeOrders getOrderById(Long orderId) {
		return dao.getOrderById(orderId);
	}


	@Override
	public List<ExchangeSuborderitem> selectItems(String subOrderId) {
		return dao.selectItems(subOrderId);
	}


	@Override
	public List<UserExchangeFavorites> selectFavorites(Long userId) {
		return dao.selectFavorites(userId);
	}


	@Override
	public PageInfo<ExchangeSuborderVo> getExSuborderList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ExchangeSuborderVo> list = dao.getExSuborderList(params);
		return new PageInfo<ExchangeSuborderVo>(list);
	}

}
