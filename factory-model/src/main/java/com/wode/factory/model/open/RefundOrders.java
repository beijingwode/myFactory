package com.wode.factory.model.open;

import java.math.BigDecimal;

/**
 * 返回售后单列表
 * @author user
 *
 */
public class RefundOrders implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7744938512508448401L;
	/**
	 * 售后单id
	 */
	private Long refund_id;
	/**
	 * 	:订单id 
	 */
	private String trade_id;
	/**
	 * :售后状态
	 */
	private String status;
	/**
	 * :售后类型
	 */
	private String refund_type;
	/**
	 * ：退货物流状态 （退货退款时适用）
	 */
	private String goods_status;
	/**
	 * :创建时间
	 */
	private String created;
	/**
	 * ：订单金额
	 */
	private BigDecimal payment;
	/**
	 * ：订单运费
	 */
	private BigDecimal shipping_fee;
	/**
	 * :退款金额
	 */
	private BigDecimal refund_fee;
	/**
	 * 退款单id
	 */
	private Long returnOrderid;
	public Long getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(Long refund_id) {
		this.refund_id = refund_id;
	}
	public String getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRefund_type() {
		return refund_type;
	}
	public void setRefund_type(String refund_type) {
		this.refund_type = refund_type;
	}
	public String getGoods_status() {
		return goods_status;
	}
	public void setGoods_status(String goods_status) {
		this.goods_status = goods_status;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public BigDecimal getPayment() {
		return payment;
	}
	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}
	public BigDecimal getShipping_fee() {
		return shipping_fee;
	}
	public void setShipping_fee(BigDecimal shipping_fee) {
		this.shipping_fee = shipping_fee;
	}
	public BigDecimal getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(BigDecimal refund_fee) {
		this.refund_fee = refund_fee;
	}
	public Long getReturnOrderid() {
		return returnOrderid;
	}
	public void setReturnOrderid(Long returnOrderid) {
		this.returnOrderid = returnOrderid;
	}

}
