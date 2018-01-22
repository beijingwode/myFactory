package com.wode.factory.vo;

import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.GroupSuborder;

public class GroupSubOrderVo extends GroupSuborder implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5403778081380895945L;
	private String managerName;
	private String supplierName;
	private String productName;
	private Long userId;
	private Long enterpriseId;
	private Long enterpriseName;
	private Long groupId;
	private GroupBuy groupBuy;
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public Long getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(Long enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public GroupBuy getGroupBuy() {
		return groupBuy;
	}
	public void setGroupBuy(GroupBuy groupBuy) {
		this.groupBuy = groupBuy;
	}
}
