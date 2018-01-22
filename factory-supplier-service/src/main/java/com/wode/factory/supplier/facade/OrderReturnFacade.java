package com.wode.factory.supplier.facade;

import com.wode.common.util.ActResult;
import com.wode.factory.model.Returnorder;

/**
 * 退货退款处理
 * @author user
 *
 */
public interface OrderReturnFacade {
	
	/**
	 * 卖家接受退货退款
	 * @param returnorder
	 * @return
	 */
	ActResult<String> argeeRetrun(Returnorder returnorder);

	/**
	 * 处理逆向订单
	 * @param returnOrderId 退货订单id
	 * @param refundOrderId 退货订单id
	 * @param action 1 同意 2 拒绝 
	 * @param type 1.退货 申请2.退款3.退货（退款）
	 * @param 拒绝退货的原因
	 * @param 退货地址
	 * @return
	 */
	ActResult<String> deal(Long returnOrderId, Long refundOrderId, Integer action, Integer type,String reason, String updName,String returnedAddress)  throws Exception ;
}
