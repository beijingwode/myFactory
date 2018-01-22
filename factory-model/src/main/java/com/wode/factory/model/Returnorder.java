/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_returnorder")
public class Returnorder extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Returnorder";
	
	public static final String ALIAS_RETURN_ORDER_ID = "退货单号ID";
	
	public static final String ALIAS_SUB_ORDER_ID = "子单号";
	
	public static final String ALIAS_RETURN_PRICE = "退款金额";
	
	public static final String ALIAS_STATUS = "状态";
	
	public static final String ALIAS_EXPRESS_TYPE = "快递公司类型";
	
	public static final String ALIAS_EXPRESS_NO = "快递单号";
	
	public static final String ALIAS_REASON = "退款原因";
	
	public static final String ALIAS_NOTE = "备注";
	
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	public static final String ALIAS_UPDATE_BY = "修改者";
	
	public static final String ALIAS_LAST_TIME = "最后处理时间";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_LAST_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * 退货单号ID       db_column: returnOrderId  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Column("returnOrderId")
	@Id
	private java.lang.Long returnOrderId;
    /**
     * 子单号       db_column: subOrderId  
     * 
     * 
     * 
     */	
	@Column("subOrderId")
	private java.lang.String subOrderId;
    /**
     * 退款金额       db_column: returnPrice  
     * 
     * 
     * 
     */	
	@Column("returnPrice")
	private BigDecimal returnPrice;
    /**
     * 状态       db_column: status  
     * 
     * 
     * @Length(max=2)
     */	
	@Column("status")
	private java.lang.Integer status;
    /**
     * 快递公司类型       db_column: expressType  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("expressType")
	private java.lang.String expressType;
    /**
     * 快递单号       db_column: expressNo  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("expressNo")
	private java.lang.String expressNo;
    /**
     * 退款原因       db_column: reason  
     * 
     * 
     * @Length(max=100)
     */	
	@Column("reason")
	private java.lang.String reason;
    /**
     * 备注       db_column: note  
     * 
     * 
     * @Length(max=200)
     */	
	@Column("note")
	private java.lang.String note;
    /**
     * 创建时间       db_column: createTime  
     * 
     * 
     * 
     */	
	@Column("createTime")
	private java.util.Date createTime;
    /**
     * 更新时间       db_column: updateTime  
     * 
     * 
     * 
     */	
	@Column("updateTime")
	private java.util.Date updateTime;
    /**
     * 修改者       db_column: updateBy  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("updateBy")
	private java.lang.String updateBy;
    /**
     * 最后处理时间       db_column: lastTime  
     * 
     * 
     * 
     */	
	@Column("lastTime")
	private java.util.Date lastTime;
	/**
     * 退款用户ID       db_column: userId  
     * 
     * 
     * 
     */	
	@Column("userId")
	private Long userId;
	/**
	 * 换货物流状态
	 */
	@Column("goods_status")
	private Integer goodsStatus;
	//columns END
	
	private List<Returnorderitem> returnorderitemList;
	
	private List<ReturnorderAttachment> returnorderAttachmentList;
	
	private Refundorder refundorder;

	public Returnorder(){
	}

	public Returnorder(
		java.lang.Long returnOrderId
	){
		this.returnOrderId = returnOrderId;
	}

	public void setReturnOrderId(java.lang.Long value) {
		this.returnOrderId = value;
	}
	
	public java.lang.Long getReturnOrderId() {
		return this.returnOrderId;
	}
	public java.lang.String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(java.lang.String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public void setReturnPrice(BigDecimal value) {
		this.returnPrice = value;
	}
	
	public BigDecimal getReturnPrice() {
		return this.returnPrice;
	}
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setExpressType(java.lang.String value) {
		this.expressType = value;
	}
	
	public java.lang.String getExpressType() {
		return this.expressType;
	}
	public void setExpressNo(java.lang.String value) {
		this.expressNo = value;
	}
	
	public java.lang.String getExpressNo() {
		return this.expressNo;
	}
	public void setReason(java.lang.String value) {
		this.reason = value;
	}
	
	public java.lang.String getReason() {
		return this.reason;
	}
	public void setNote(java.lang.String value) {
		this.note = value;
	}
	
	public java.lang.String getNote() {
		return this.note;
	}
	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public String getUpdateTimeString() {
		return DateConvertUtils.format(getUpdateTime(), FORMAT_UPDATE_TIME);
	}
	public void setUpdateTimeString(String value) {
		setUpdateTime(DateConvertUtils.parse(value, FORMAT_UPDATE_TIME,java.util.Date.class));
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setUpdateBy(java.lang.String value) {
		this.updateBy = value;
	}
	
	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}
	public String getLastTimeString() {
		return DateConvertUtils.format(getLastTime(), FORMAT_LAST_TIME);
	}
	public void setLastTimeString(String value) {
		setLastTime(DateConvertUtils.parse(value, FORMAT_LAST_TIME,java.util.Date.class));
	}
	
	public void setLastTime(java.util.Date value) {
		this.lastTime = value;
	}
	
	public java.util.Date getLastTime() {
		return this.lastTime;
	}
	
	public List<Returnorderitem> getReturnorderitemList() {
		return returnorderitemList;
	}

	public void setReturnorderitemList(List<Returnorderitem> returnorderitemList) {
		this.returnorderitemList = returnorderitemList;
	}

	public List<ReturnorderAttachment> getReturnorderAttachmentList() {
		return returnorderAttachmentList;
	}

	public void setReturnorderAttachmentList(
			List<ReturnorderAttachment> returnorderAttachmentList) {
		this.returnorderAttachmentList = returnorderAttachmentList;
	}
	
	public Refundorder getRefundorder() {
		return refundorder;
	}

	public void setRefundorder(Refundorder refundorder) {
		this.refundorder = refundorder;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("ReturnOrderId",getReturnOrderId())
			.append("SubOrderId",getSubOrderId())
			.append("ReturnPrice",getReturnPrice())
			.append("Status",getStatus())
			.append("ExpressType",getExpressType())
			.append("ExpressNo",getExpressNo())
			.append("Reason",getReason())
			.append("Note",getNote())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.append("UpdateBy",getUpdateBy())
			.append("LastTime",getLastTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getReturnOrderId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Returnorder == false) return false;
		if(this == obj) return true;
		Returnorder other = (Returnorder)obj;
		return new EqualsBuilder()
			.append(getReturnOrderId(),other.getReturnOrderId())
			.isEquals();
	}

	public Integer getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(Integer goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	
	
}

