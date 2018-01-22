/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;


import java.util.List;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.UserLimitTicket;
import com.wode.factory.user.vo.UserLimitTicketVo;

public interface UserLimitTicketService extends FactoryEntityService<UserLimitTicket>{

	Integer getTicketCount(Long id);

	UserLimitTicketVo selectUnusedById(String limitTicketId, String userId);

	List<UserLimitTicketVo> getByUserId(Long id);

	List<UserLimitTicketVo> getAvailableTickets(Long id);

	UserLimitTicket getByLimitTicketIdAndUserId(Long id, Long id2);
	// 通过sku 获取可用优惠券 skuIds skuId1,skuId2
	List<UserLimitTicket> getAvailableTicketMap(Long userId,String skuIds,Integer limitType);
}
