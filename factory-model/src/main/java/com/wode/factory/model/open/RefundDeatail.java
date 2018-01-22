package com.wode.factory.model.open;

public class RefundDeatail extends RefundOrders {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1694322034593778835L;
	/**
	 * :退款原因
	 */
	private String reason;
	/**
	 * :退款说明
	 */
	private String note;
	/**
	 * :凭证1 图片url
	 */
	private String attachment1;
	/**
	 * :凭证2 图片url
	 */
	private String attachment2;
	/**
	 * :凭证3 图片url
	 */
	private String attachment3;
	/**
	 * :快递公司简称    （退货退款时适用）
	 */
	private String express_com;
	/**
	 * :快递单号    （退货退款时适用）
	 */
	private String express_no;
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getAttachment1() {
		return attachment1;
	}
	public void setAttachment1(String attachment1) {
		this.attachment1 = attachment1;
	}
	public String getAttachment2() {
		return attachment2;
	}
	public void setAttachment2(String attachment2) {
		this.attachment2 = attachment2;
	}
	public String getAttachment3() {
		return attachment3;
	}
	public void setAttachment3(String attachment3) {
		this.attachment3 = attachment3;
	}
	public String getExpress_com() {
		return express_com;
	}
	public void setExpress_com(String express_com) {
		this.express_com = express_com;
	}
	public String getExpress_no() {
		return express_no;
	}
	public void setExpress_no(String express_no) {
		this.express_no = express_no;
	}
	
}
