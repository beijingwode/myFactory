package com.wode.factory.supplier.query;

import java.io.Serializable;

import com.wode.common.frame.base.BaseQuery;

public class ShippingTemplateVo extends BaseQuery implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1655301811860946399L;

    private Long id;
    private Long supplierId;
    private String name;
    private Integer version;
    private Integer isAudit;//是否需要审核
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}
	
}
