package com.wode.factory.model;


import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_manager_order_record")
public class ManagerOrderRecord extends BaseModel implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9186434502708140429L;
	//columns START
    /**
     * id       db_column: id
     * 
     * 
     * 
     */ 
    @PrimaryKey
    @Column("id")
    private java.lang.Long id;
    /**
     * 订单id       db_column: orderid
     * 
     * 
     * 
     */ 
    @Column("subOrderid")
    private java.lang.String subOrderId;
    /**
     * 商品名称       db_column: productName
     * 
     * 
     * 
     */ 
    @Column("productName")
    private java.lang.String productName;
    /**
     * 操作状态      db_column: operationStatus
     * 
     * 
     * 
     */ 
    @Column("operationStatus")
    private java.lang.Integer operationStatus;
    /**
     * 招商id       db_column: userId
     * 
     * 
     * 
     */ 
    @Column("userid")
    private java.lang.Long userId;
    /**
     * 更新时间       db_column: update_time
     * 
     * 
     * 
     */ 
    @Column("create_date")
    private java.util.Date createDate;
    
    private String itemValues;
    private Integer skuNumber;
    private String userName;
    private Integer orderType;
    
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(java.lang.String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public java.lang.String getProductName() {
		return productName;
	}
	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}
	public java.lang.Integer getOperationStatus() {
		return operationStatus;
	}
	public void setOperationStatus(java.lang.Integer operationStatus) {
		this.operationStatus = operationStatus;
	}
	public java.lang.Long getUserId() {
		return userId;
	}
	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public String getItemValues() {
		return itemValues;
	}
	public void setItemValues(String itemValues) {
		this.itemValues = itemValues;
	}
	public Integer getSkuNumber() {
		return skuNumber;
	}
	public void setSkuNumber(Integer skuNumber) {
		this.skuNumber = skuNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
    
}
