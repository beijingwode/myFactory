package com.wode.factory.user.facade;

import java.math.BigDecimal;
import java.util.List;

import com.wode.common.util.ActResult;
import com.wode.factory.exception.BenefitLessException;
import com.wode.factory.exception.SupplierShardeLessException;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Payment;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.vo.OrderVO;

public interface OrdersFacade {

	/**
	 * 创建订单
	 * @param order	订单基本信息
	 * @param cart 购物车信息，（商品详情）
	 * @param useCash	使用现金抵扣
	 * @param subIds	子单id
	 * @param fromType 
	 * @return
	 * @throws SupplierShardeLessException 
	 */
	public ActResult<Object> createOrder(OrderVO order,Cart cart,BigDecimal useCash,List<String> subIds,String message) throws BenefitLessException, SupplierShardeLessException;

	/**
	 * 订单取消 
	 * @param user
	 * @param subOrderId
	 * @param closeReason
	 * @return
	 */
	public ActResult<String> cancel(UserFactory user, String subOrderId, String closeReason,boolean flowResult);
	
	/**
	 * 订单合法性检测 检查限购
	 * @param vo 
	 * @param cart
	 * @param user
	 * @return
	 */
	ActResult<Object> checkOrder(Cart cart,UserFactory user);

    /**
     * 确认收货
     * @param user
     * @param sub
     * @return
     * @throws Exception
     */
    boolean updateOrderStatus4(UserFactory user, String sub);
    

	public ActResult paySuccess(Long userId, String suborderId,String way,String trade_no,BigDecimal totalFee) throws Exception;

	public ActResult<Object> cancelOrder(UserFactory user, Long orderId, String closeReason,boolean flowResult);
	
	void updateOrderToPay(Payment payment);
	
	void listAdd(List<Suborderitem> suborderitems, Suborderitem suborderitem);
	void dealOrders(Orders orders, Suborder suborder, List<Suborderitem> suborderitems);
}
