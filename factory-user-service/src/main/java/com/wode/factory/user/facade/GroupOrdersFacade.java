package com.wode.factory.user.facade;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wode.common.util.ActResult;
import com.wode.factory.exception.BenefitLessException;
import com.wode.factory.exception.SupplierShardeLessException;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.Payment;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.vo.GroupAndOrderVO;
import com.wode.factory.user.vo.GroupOrderVO;

public interface GroupOrdersFacade {

	/**
	 * 创建订单
	 * @param order	订单基本信息
	 * @param cart 购物车信息，（商品详情）
	 * @param useCash	使用现金抵扣
	 * @param subIds	子单id
	 * @return
	 * @throws SupplierShardeLessException 
	 */
	public ActResult<Object> createOrder(GroupOrderVO order,Cart cart,BigDecimal useCash,List<String> subIds,String message) throws BenefitLessException, SupplierShardeLessException;

	/**
	 * 订单取消 
	 * @param user
	 * @param subOrderId
	 * @param closeReason
	 * @return
	 */
	public ActResult<String> cancel(UserFactory user, GroupSuborder subOrder, String closeReason,boolean flowResult);
	
	/**
	 * 订单取消  下单失败直接取消
	 * @param user
	 * @param subOrderId
	 * @param closeReason
	 * @return
	 */
	public ActResult<String> cancelOrder(UserFactory user, Long orderId, String closeReason,boolean flowResult);

	/**
	 * 订单取消  开团后订单取消
	 * @param groupId
	 * @param closeReason
	 * @return
	 */
	public ActResult<String> cancelAfterOpen(Long groupId, String closeReason,Integer status);
	
	/**
	 * 开团
	 * @param groupId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public boolean mergerOrder(Long groupId,List<String> subIds) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 开团
	 * @param groupId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public boolean refundShipp(Long groupId) throws IllegalAccessException, InvocationTargetException;
	/**
	 * 查询我的团购订单
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public GroupAndOrderVO findGroupAndOrder(Long groupId,Long userId);
	
	/**
	 * 查询团员信息
	 * @param groupId
	 * @return
	 */
	public List<GroupAndOrderVO> findAllGroupAndOrder(Long groupId);
	/**
	 * 查询差多少包邮
	 * @param groupId
	 * @param supplierId
	 * @return
	 */
	public Map<String, Object> postageStrategy(Long groupId);
	/**
	 * 团购订单支付成功的处理
	 * @param userId
	 * @param subOrderId
	 * @param way
	 * @param trade_no
	 * @param totalFee
	 * @return
	 * @throws Exception
	 */
	ActResult payGroupSuccess(Long userId, String subOrderId, String way, String trade_no, BigDecimal totalFee)
			throws Exception;
	
	void updateOrderToPay(Payment payment);
	
	/**
	 * 团订单 团长签收
	 * @param groupId
	 */
	void masterTakeOrder(Long groupId,UserFactory userFactory);

	public ActResult<Object> checkGroupOrder(Long groupId);
}
