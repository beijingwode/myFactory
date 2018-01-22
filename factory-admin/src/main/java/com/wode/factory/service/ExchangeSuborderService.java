package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.ExchangeOrders;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.ExchangeSuborderitem;
import com.wode.factory.model.UserExchangeFavorites;
import com.wode.factory.vo.ExchangeSuborderVo;

/**
 *
 */
public interface ExchangeSuborderService extends FactoryEntityService<ExchangeSuborder> {

	ExchangeSuborder getById(String id);
	PageInfo<ExchangeSuborder> getPageList(Map<String, Object> params);
	
	ExchangeOrders getOrderById(Long orderId);
	List<ExchangeSuborderitem> selectItems(String subOrderId);
	List<UserExchangeFavorites> selectFavorites(Long userId);
	PageInfo<ExchangeSuborderVo> getExSuborderList(Map<String, Object> params);
}
