package com.wode.factory.supplier.query;

import java.io.Serializable;

import com.wode.common.frame.base.BaseQuery;

public class ServiceReceiptVo extends BaseQuery implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1655301811860946399L;

    private Long id;
    private Long supplierId;
    
    String saleBillIds;
    String status;
    String startCreateDate;
    String endCreateDate;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSaleBillIds() {
		return saleBillIds;
	}
	public void setSaleBillIds(String saleBillIds) {
		this.saleBillIds = saleBillIds;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartCreateDate() {
		return startCreateDate;
	}
	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}
	public String getEndCreateDate() {
		return endCreateDate;
	}
	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

}
