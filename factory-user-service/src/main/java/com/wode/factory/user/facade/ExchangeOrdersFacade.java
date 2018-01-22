package com.wode.factory.user.facade;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import com.wode.common.util.ActResult;
import com.wode.factory.exception.BenefitLessException;
import com.wode.factory.exception.SupplierShardeLessException;
import com.wode.factory.model.ExchangeOrders;
import com.wode.factory.model.Payment;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.model.Cart;

public interface ExchangeOrdersFacade {

	/**
	 * 创建订单
	 * @param order	订单基本信息
	 * @param cart 购物车信息，（商品详情）
	 * @param useCash	使用现金抵扣
	 * @param subIds	子单id
	 * @return
	 * @throws SupplierShardeLessException 
	 */
	ActResult<Object> createOrder(ExchangeOrders order,Cart cart,BigDecimal useCash,List<String> subIds,String message,String freeSwap) throws BenefitLessException, SupplierShardeLessException;

	/**
	 * 订单取消 
	 * @param user
	 * @param subOrderId
	 * @param closeReason
	 * @return
	 */
	ActResult<String> cancel(UserFactory user, String subOrderId, String closeReason,Integer exchangeStatus,boolean flowResult);  

	ActResult paySuccess(Long userId, String suborderId,String way,String trade_no,BigDecimal totalFee) throws Exception;

	ActResult<Object> cancelOrder(UserFactory user, Long orderId, String closeReason,boolean flowResult);
	
	void updateOrderToPay(Payment payment);
	
	/**
	 * 匹配成功直接下单
	 * @param groupId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	boolean submitOrder(String subOrderId,String updateBy) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 调剂清单匹配换领商品
	 * @param userId		买家id
	 * @param orderId		收货订单id
	 * @param subOrderIds	子单id 多个，以逗号分隔
	 * @param sku_nums		skuId_数量  多个，以逗号分隔
	 * @param updateBy		操作员 id
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	boolean mergeOrder(Long userId,Long orderId,String subOrderIds,String sku_nums,String updateBy,List<String> subIds) throws IllegalAccessException, InvocationTargetException;
	
	
}
