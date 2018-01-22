package com.wode.factory.company.query;

public class EnterpriseStructureVo {
	/**
	 * 企业组织id
	 */
	public Long id;
	/**
	 * 企业id
	 */
	public Long enterpriseId;
	/**
	 * 企业名称
	 */
	public String enterpriseName;
	/**
	 * 企业组织关系的类型
	 */
	public Integer type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
