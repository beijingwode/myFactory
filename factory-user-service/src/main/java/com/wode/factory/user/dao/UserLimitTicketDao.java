/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.dao;

import java.util.List;

import com.wode.common.frame.base.FactoryBaseDao;
import com.wode.factory.model.UserLimitTicket;
import com.wode.factory.user.vo.UserLimitTicketVo;

public interface UserLimitTicketDao extends  FactoryBaseDao<UserLimitTicket>{

	Integer getTicketCount(Long id);

	UserLimitTicketVo selectUnusedById(String ticketId,String userId);

	List<UserLimitTicketVo> getByUserId(Long userId);

	List<UserLimitTicketVo> getAvailableTickets(Long userId);

	UserLimitTicket getByLimitTicketIdAndUserId(Long id, Long uid);

	// 通过sku 获取可用优惠券 skuIds skuId1,skuId2
	List<UserLimitTicket> getAvailableTicketMap(Long userId,String skuIds,Integer limitType);
}
