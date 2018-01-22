package com.wode.factory.vo;

import com.wode.factory.model.SupplierExchangeProduct;

public class SupplierExchangeProductVo extends SupplierExchangeProduct implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1565311424564772023L;
	private String comName;
	private Integer comType;
	private String managerName;
	private String queryLink;
	private Long userShareTicketId;
	private String userNick;
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
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
	public String getQueryLink() {
		return queryLink;
	}
	public void setQueryLink(String queryLink) {
		this.queryLink = queryLink;
	}
	public Long getUserShareTicketId() {
		return userShareTicketId;
	}
	public void setUserShareTicketId(Long userShareTicketId) {
		this.userShareTicketId = userShareTicketId;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	
	
}
