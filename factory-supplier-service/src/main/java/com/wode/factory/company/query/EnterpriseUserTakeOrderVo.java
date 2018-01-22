package com.wode.factory.company.query;

import java.io.Serializable;
import java.util.Date;

import com.wode.common.frame.base.BaseQuery;

public class EnterpriseUserTakeOrderVo extends BaseQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3103615653109095063L;
	private String name;//员工
	private String phone;//电话
	private String sectionName;//部门
	private String productName;//商品名称
	private String itemValues;//规格
	private String number;//数量
	private Date createTime;//下单日期
	private Integer orderStatus;//状态
	private Date startTime;
	private Date endTime;
	private Long enterpriseId;
	
	
	
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getItemValues() {
		return itemValues;
	}
	public void setItemValues(String itemValues) {
		this.itemValues = itemValues;
	}
	
}
