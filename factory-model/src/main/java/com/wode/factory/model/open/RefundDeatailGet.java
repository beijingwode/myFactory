package com.wode.factory.model.open;

import com.wode.factory.model.OpenRequestBase;
/**
 * 售后订单详情查询
 * @author user
 *
 */
public class RefundDeatailGet extends OpenRequestBase {
	
	/**
	 * 售后单id简易售后订单列表接口获取 （与订单id 二选一）
	 */
	private String refund_id;	
    /**
     * 订单id 可通过简易订单列表接口获取（与售后单id 二选一）
     */
	private String trade_id;
	public String getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}
	public String getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}	

}
