package com.wode.factory.model.open;

import com.wode.factory.model.OpenRequestBase;
/**
 * 售后单列表
 * @author user
 *
 */
public class RefundGet extends OpenRequestBase {
	
	/**
	 * 交易创建时间开始
	 */
	private String start_created;
	/**
	 * 交易创建时间结束
	 */
	private String end_created;
	/**
	 * 交易状态(默认为待发货订单（WAIT_SELLER_CONFIRM）)
	 */
	private String status;
	/**
	 * 售后类型: 默认为全部类型
	 */
	private String refund_type;
	/**
	 * 退货物流状态（退货退款时适用）：默认为全部状态
	 */
	private String goods_status;
	public String getStart_created() {
		return start_created;
	}
	public void setStart_created(String start_created) {
		this.start_created = start_created;
	}
	public String getEnd_created() {
		return end_created;
	}
	public void setEnd_created(String end_created) {
		this.end_created = end_created;
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
    
}
