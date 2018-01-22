package com.wode.factory.supplier.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class SaleDetailQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** 子订单id */
	private java.lang.String subOrderId;
	/** 付款时间 */
	private java.util.Date payTimeBegin;
	private java.util.Date payTimeEnd;
	/** 收货时间 */
	private java.util.Date takeTimeBegin;
	private java.util.Date takeTimeEnd;
	/** 退货日期 */
	private java.util.Date returnTimeBegin;
	private java.util.Date returnTimeEnd;
	/** 货单类型：  0：本企业  1：其他企业 */
	private java.lang.Integer own;
	/** productId */
	private java.lang.Long productId;
	/** 商品名称 */
	private java.lang.String productName;
	/** skuId */
	private java.lang.Long skuId;
	/** categoryId */
	private java.lang.Long categoryId;
	/** 类目名称 */
	private java.lang.String categoryName;
	/** price */
	private Long price;
	/** number */
	private java.lang.Integer number;
	/** 总货款 */
	private Long allPrice;
	/** 佣金比例 */
	private java.lang.Float commissionRatio;
	/** commission */
	private Long commission;
	/** 实付金额 */
	private Long payPrice;
	/** 状态：1:确认收货   -1:已退货 */
	private java.lang.Integer status;
	/** 有优惠  0：无优惠  1：有优惠 */
	private java.lang.Integer haveCheap;
	/** 福利币　：如果为退货　则为负数 */
	private java.lang.Long fuCoin;
	/** createTime */
	private java.util.Date createTimeBegin;
	private java.util.Date createTimeEnd;
	/** createUserid */
	private java.lang.Long createUserid;
	/** 是否删除：  0：默认正常   1：已删除 */
	private java.lang.Integer isDelete;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.String getSubOrderId() {
		return this.subOrderId;
	}
	
	public void setSubOrderId(java.lang.String value) {
		this.subOrderId = value;
	}
	
	public java.util.Date getPayTimeBegin() {
		return this.payTimeBegin;
	}
	
	public void setPayTimeBegin(java.util.Date value) {
		this.payTimeBegin = value;
	}	
	
	public java.util.Date getPayTimeEnd() {
		return this.payTimeEnd;
	}
	
	public void setPayTimeEnd(java.util.Date value) {
		this.payTimeEnd = value;
	}
	
	public java.util.Date getTakeTimeBegin() {
		return this.takeTimeBegin;
	}
	
	public void setTakeTimeBegin(java.util.Date value) {
		this.takeTimeBegin = value;
	}	
	
	public java.util.Date getTakeTimeEnd() {
		return this.takeTimeEnd;
	}
	
	public void setTakeTimeEnd(java.util.Date value) {
		this.takeTimeEnd = value;
	}
	
	public java.util.Date getReturnTimeBegin() {
		return this.returnTimeBegin;
	}
	
	public void setReturnTimeBegin(java.util.Date value) {
		this.returnTimeBegin = value;
	}	
	
	public java.util.Date getReturnTimeEnd() {
		return this.returnTimeEnd;
	}
	
	public void setReturnTimeEnd(java.util.Date value) {
		this.returnTimeEnd = value;
	}
	
	public java.lang.Integer getOwn() {
		return this.own;
	}
	
	public void setOwn(java.lang.Integer value) {
		this.own = value;
	}
	
	public java.lang.Long getProductId() {
		return this.productId;
	}
	
	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}
	
	public java.lang.String getProductName() {
		return this.productName;
	}
	
	public void setProductName(java.lang.String value) {
		this.productName = value;
	}
	
	public java.lang.Long getSkuId() {
		return this.skuId;
	}
	
	public void setSkuId(java.lang.Long value) {
		this.skuId = value;
	}
	
	public java.lang.Long getCategoryId() {
		return this.categoryId;
	}
	
	public void setCategoryId(java.lang.Long value) {
		this.categoryId = value;
	}
	
	public java.lang.String getCategoryName() {
		return this.categoryName;
	}
	
	public void setCategoryName(java.lang.String value) {
		this.categoryName = value;
	}
	
	public Long getPrice() {
		return this.price;
	}
	
	public void setPrice(Long value) {
		this.price = value;
	}
	
	public java.lang.Integer getNumber() {
		return this.number;
	}
	
	public void setNumber(java.lang.Integer value) {
		this.number = value;
	}
	
	public Long getAllPrice() {
		return this.allPrice;
	}
	
	public void setAllPrice(Long value) {
		this.allPrice = value;
	}
	
	public java.lang.Float getCommissionRatio() {
		return this.commissionRatio;
	}
	
	public void setCommissionRatio(java.lang.Float value) {
		this.commissionRatio = value;
	}
	
	public Long getCommission() {
		return this.commission;
	}
	
	public void setCommission(Long value) {
		this.commission = value;
	}
	
	public Long getPayPrice() {
		return this.payPrice;
	}
	
	public void setPayPrice(Long value) {
		this.payPrice = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getHaveCheap() {
		return this.haveCheap;
	}
	
	public void setHaveCheap(java.lang.Integer value) {
		this.haveCheap = value;
	}
	
	public java.lang.Long getFuCoin() {
		return this.fuCoin;
	}
	
	public void setFuCoin(java.lang.Long value) {
		this.fuCoin = value;
	}
	
	public java.util.Date getCreateTimeBegin() {
		return this.createTimeBegin;
	}
	
	public void setCreateTimeBegin(java.util.Date value) {
		this.createTimeBegin = value;
	}	
	
	public java.util.Date getCreateTimeEnd() {
		return this.createTimeEnd;
	}
	
	public void setCreateTimeEnd(java.util.Date value) {
		this.createTimeEnd = value;
	}
	
	public java.lang.Long getCreateUserid() {
		return this.createUserid;
	}
	
	public void setCreateUserid(java.lang.Long value) {
		this.createUserid = value;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	
	public void setIsDelete(java.lang.Integer value) {
		this.isDelete = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

