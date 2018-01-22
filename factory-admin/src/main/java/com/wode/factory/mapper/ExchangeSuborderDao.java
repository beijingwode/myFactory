package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.ExchangeOrders;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.ExchangeSuborderitem;
import com.wode.factory.model.UserExchangeFavorites;
import com.wode.factory.vo.ExchangeSuborderVo;

/**
 * Created by zoln on 2015/7/24.
 */
public interface ExchangeSuborderDao extends  FactoryBaseDao<ExchangeSuborder> {

	ExchangeSuborder getById(String id);
	List<ExchangeSuborder> selectByModel(Map<String, Object> params);
	

	ExchangeOrders getOrderById(Long orderId);
	List<ExchangeSuborderitem> selectItems(String subOrderId);

	List<UserExchangeFavorites> selectFavorites(Long userId);
	List<ExchangeSuborderVo> getExSuborderList(Map<String, Object> params);
}
