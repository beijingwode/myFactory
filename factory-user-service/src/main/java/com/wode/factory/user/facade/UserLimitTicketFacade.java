package com.wode.factory.user.facade;

import java.util.List;

import com.wode.factory.model.UserLimitTicket;

public interface UserLimitTicketFacade {
	
	/**
	 * 根据用户id及选中优惠券id 获取可用列表
	 * @param userId
	 * @param limitTicketIds
	 * @return
	 */
	List<UserLimitTicket> getUsableTickits(Long userId,String limitTicketIds);

	/**
	 * 根据用户id及skus  获取可用列表
	 * @param userId
	 * @param skuIds
	 * @param hasLimit4
	 * @return
	 */
	List<UserLimitTicket> getUsableTickits(Long userId,List<Long> skuIds,boolean hasLimit4);
}
