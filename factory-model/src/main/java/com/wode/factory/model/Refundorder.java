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

@Table("t_refundorder")
public class Refundorder extends BaseModel implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	//alias
	public static final String TABLE_ALIAS = "Refundorder";

	public static final String ALIAS_REFUND_ORDER_ID = "退款单ID";

	public static final String ALIAS_RETURN_ORDER_ID = "退货单id";

	public static final String ALIAS_EXPRESS_COMPANY = "退货快递公司";

	public static final String ALIAS_REFUND_PRICE = "退款金额";

	public static final String ALIAS_REFUND_ACCOUNT = "退款帐号";

	public static final String ALIAS_REASON = "退货原因";

	public static final String ALIAS_STATUS = "状态 1提交申请 2:处理中  3;退款异常 10：退款完毕";

	public static final String ALIAS_CREATE_TIME = "创建时间";

	public static final String ALIAS_UPDATE_TIME = "更新时间";

	public static final String ALIAS_UPDATE_BY = "修改者";

	//date formats
	public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;

	//columns START
	/**
	 * 退款单ID       db_column: refundOrderId
	 */
	@PrimaryKey
	@Column("refundOrderId")
	@Id
	private java.lang.Long refundOrderId;
	/**
	 * 退货单id       db_column: returnOrderId
	 *
	 * @NotNull
	 */
	@Column("returnOrderId")
	private java.lang.Long returnOrderId;
	/**
	 * 退货快递公司       db_column: express_company
	 *
	 * @Length(max=100)
	 */
	@Column("express_company")
	private java.lang.String expressCompany;
	/**
	 * 退款金额       db_column: refundPrice
	 *
	 * @NotNull
	 */
	@Column("refundPrice")
	private BigDecimal refundPrice;
	/**
	 * 退款帐号       db_column: refundAccount
	 *
	 * @Length(max=50)
	 */
	@Column("refundAccount")
	private java.lang.String refundAccount;
	/**
	 * 退货原因       db_column: reason
	 *
	 * @Length(max=300)
	 */
	@Column("reason")
	private java.lang.String reason;

	/**
	 * 退款备注
	 */
	private String note;

	/**
	 * 状态 1提交申请 2:处理中  3;退款异常 10：退款完毕       db_column: status
	 *
	 * @NotNull
	 */
	@Column("status")
	private java.lang.Integer status;
	/**
	 * 创建时间       db_column: createTime
	 */
	@Column("createTime")
	private java.util.Date createTime;
	/**
	 * 更新时间       db_column: updateTime
	 */
	@Column("updateTime")
	private java.util.Date updateTime;

	/**
	 * 最后处理时间       db_column: lastTime
	 */
	@Column("lastTime")
	private java.util.Date lastTime;
	/**
	 * 修改者       db_column: updateBy
	 *
	 * @Length(max=50)
	 */
	@Column("updateBy")
	private java.lang.String updateBy;
	//columns END

	private Integer goodsStatus;// 货物状态（0：未收到货物；1：已收到货物）

	private Long userId;

	private List<RefundorderAttachment> attachmentList;

	public Integer getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(Integer goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public Refundorder() {
	}

	public Refundorder(
			java.lang.Long refundOrderId
	) {
		this.refundOrderId = refundOrderId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setRefundOrderId(java.lang.Long value) {
		this.refundOrderId = value;
	}

	public java.lang.Long getRefundOrderId() {
		return this.refundOrderId;
	}

	public void setReturnOrderId(java.lang.Long value) {
		this.returnOrderId = value;
	}

	public java.lang.Long getReturnOrderId() {
		return this.returnOrderId;
	}

	public void setExpressCompany(java.lang.String value) {
		this.expressCompany = value;
	}

	public java.lang.String getExpressCompany() {
		return this.expressCompany;
	}

	public void setRefundPrice(BigDecimal value) {
		this.refundPrice = value;
	}

	public BigDecimal getRefundPrice() {
		return this.refundPrice;
	}

	public void setRefundAccount(java.lang.String value) {
		this.refundAccount = value;
	}

	public java.lang.String getRefundAccount() {
		return this.refundAccount;
	}

	public void setReason(java.lang.String value) {
		this.reason = value;
	}

	public java.util.Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(java.util.Date lastTime) {
		this.lastTime = lastTime;
	}

	public java.lang.String getReason() {
		return this.reason;
	}

	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}

	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), FORMAT_CREATE_TIME);
	}

	public void setCreateTimeString(String value) {
		setCreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME, java.util.Date.class));
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
		setUpdateTime(DateConvertUtils.parse(value, FORMAT_UPDATE_TIME, java.util.Date.class));
	}

	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}

	public List<RefundorderAttachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<RefundorderAttachment> attachmentList) {
		this.attachmentList = attachmentList;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("RefundOrderId", getRefundOrderId())
				.append("ReturnOrderId", getReturnOrderId())
				.append("ExpressCompany", getExpressCompany())
				.append("RefundPrice", getRefundPrice())
				.append("RefundAccount", getRefundAccount())
				.append("Reason", getReason())
				.append("Status", getStatus())
				.append("CreateTime", getCreateTime())
				.append("UpdateTime", getUpdateTime())
				.append("UpdateBy", getUpdateBy())
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getRefundOrderId())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof Refundorder == false) return false;
		if (this == obj) return true;
		Refundorder other = (Refundorder) obj;
		return new EqualsBuilder()
				.append(getRefundOrderId(), other.getRefundOrderId())
				.isEquals();
	}
}

