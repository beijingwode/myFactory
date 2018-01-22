package com.wode.factory.user.facade;

import java.util.Date;
import java.util.Map;

import com.wode.factory.model.UserFactory;
import com.wode.factory.user.model.UseExchangeTicketVo;

public interface UserExchangeTicketFacade {
	
	/**
	 * 员工优惠券处理
	 * @param userId 员工id
	 * @param ticketType 优惠券类型
	 * @param opCode 操作代码
	 * @param amount 金额（不要符号）
	 * @param updUser 更新用户
	 * @param key 关键字
	 * @param flowId 流水id
	 * @param desrc 备注
	 * @param subOrderId 订单id
	 * @return 1：正常处理/0：系统异常/-1：余额不足
	 */
	public int cancelOrderTicket(UserFactory user,Map<Long,UseExchangeTicketVo> mapExchange, String updUser, Long key,Date createTime);

	/**
	 * 员工优惠券处理
	 * @param userTicket 优惠券
	 * @param opCode 操作代码
	 * @param amount 金额（不要符号）
	 * @param updUser 更新用户
	 * @param key 关键字
	 * @param flowId 流水id
	 * @param desrc 备注
	 * @param subOrderId 订单id
	 * @return 1：正常处理/0：系统异常/-1：余额不足
	 */
	public int orderUserTicket(Long userId,Map<Long,UseExchangeTicketVo> mapExchange, String updUser, Long key);
}
