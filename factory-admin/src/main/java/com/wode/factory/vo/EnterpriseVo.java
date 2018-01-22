package com.wode.factory.vo;

import com.wode.factory.model.Enterprise;

public class EnterpriseVo extends Enterprise {
	
	private String typeName;
	
	private String industryName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

}
