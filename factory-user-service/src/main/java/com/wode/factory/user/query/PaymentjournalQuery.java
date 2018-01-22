/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class PaymentjournalQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 主键 */
	private java.lang.Long id;
	/** 对外交易号 */
	private java.lang.String outTradeNo;
	/** 订单号 */
	private java.lang.String orderId;
	/** 数据类型 */
	private Integer type;
	/** 交易流水号 */
	private java.lang.String tradeNo;
	/** 创建时间 */
	private java.util.Date createdTimeBegin;
	private java.util.Date createdTimeEnd;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.String getOutTradeNo() {
		return this.outTradeNo;
	}
	
	public void setOutTradeNo(java.lang.String value) {
		this.outTradeNo = value;
	}
	
	public java.lang.String getOrderId() {
		return this.orderId;
	}
	
	public void setOrderId(java.lang.String value) {
		this.orderId = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	
	public void setType(Integer value) {
		this.type = value;
	}
	
	public java.lang.String getTradeNo() {
		return this.tradeNo;
	}
	
	public void setTradeNo(java.lang.String value) {
		this.tradeNo = value;
	}
	
	public java.util.Date getCreatedTimeBegin() {
		return this.createdTimeBegin;
	}
	
	public void setCreatedTimeBegin(java.util.Date value) {
		this.createdTimeBegin = value;
	}	
	
	public java.util.Date getCreatedTimeEnd() {
		return this.createdTimeEnd;
	}
	
	public void setCreatedTimeEnd(java.util.Date value) {
		this.createdTimeEnd = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

