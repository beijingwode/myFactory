package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.UserExchangeTicket;

/**
 * Created by zoln on 2015/7/24.
 */
public interface UserExchangeTicketDao extends  FactoryBaseDao<UserExchangeTicket> {

	public List<UserExchangeTicket> selectLeft(UserExchangeTicket query);

	/**
	 * 更新使用期限
	 * @param query
	 */
	public void updateEnds(UserExchangeTicket query);

	public void deleteBySupplierId(Long supplierId);

	public List<UserExchangeTicket> findPageInfo(Map<String, Object> params);
	
	
}
