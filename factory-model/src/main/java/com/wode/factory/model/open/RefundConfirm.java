package com.wode.factory.model.open;
/**
 * 退款单
 * @author user
 *
 */
public class RefundConfirm extends  TradeGet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2943667237325052572L;
	
	private Long refund_id;//退款单号

	public Long getRefund_id() {
		return refund_id;
	}

	public void setRefund_id(Long refund_id) {
		this.refund_id = refund_id;
	}

    
}
