package com.wode.factory.model;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;


public class CooperationId extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "CooperationId";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_REGISTER_NUMBER = "企业编号";
	
	public static final String ALIAS_STATUS = "注册编码状态值  0：表示未使用，1：表示已使用";
	
	public static final String ALIAS_ENTERPRISE_ID = "企业id  默认为空，企业用到的时候才会有值";
	
	//date formats
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * 企业编号       db_column: register_number  
     * 
     * 
     * @NotBlank @Length(max=255)
     */	
	private java.lang.String registerNumber;
    /**
     * 注册编码状态值  0：表示未使用，1：表示已使用       db_column: status  
     * 
     * 
     * @NotNull 
     */	
	private java.lang.Integer status;
    /**
     * 企业id  默认为空，企业用到的时候才会有值       db_column: enterprise_id  
     * 
     * 
     * 
     */	
	private java.lang.Long enterpriseId;
	//columns END

	public CooperationId(){
	}

	public CooperationId(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setRegisterNumber(java.lang.String value) {
		this.registerNumber = value;
	}
	
	public java.lang.String getRegisterNumber() {
		return this.registerNumber;
	}
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setEnterpriseId(java.lang.Long value) {
		this.enterpriseId = value;
	}
	
	public java.lang.Long getEnterpriseId() {
		return this.enterpriseId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("RegisterNumber",getRegisterNumber())
			.append("Status",getStatus())
			.append("EnterpriseId",getEnterpriseId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CooperationId == false) return false;
		if(this == obj) return true;
		CooperationId other = (CooperationId)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

