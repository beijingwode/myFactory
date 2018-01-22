package com.wode.factory.vo;

import com.wode.factory.model.SupplierLimitTicket;

public class SupplierLimitTicketVo extends SupplierLimitTicket implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2330193787144051665L;
	
	private Integer comType;
	private String managerName;
	public Integer getComType() {
		return comType;
	}
	public void setComType(Integer comType) {
		this.comType = comType;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
}
