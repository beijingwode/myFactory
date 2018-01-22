/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */


package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;


@Table("t_enterprise")
public class Enterprise extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
		
	//date formats
	
	//columns START
    /**
     * 企业id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * 企业名称       db_column: name  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String name;
    /**
     * 企业类型       db_column: type  
     * 
     * 
     * 
     */	
	private java.lang.Long type;
    /**
     * 所属行业       db_column: industry  
     * 
     * 
     * 
     */	
	private java.lang.Long industry;
    /**
     * 是否上市       db_column: listed  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String listed;
    /**
     * 营业额       db_column: turnover  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String turnover;
    /**
     * 企业人数       db_column: people_number  
     * 
     * 
     * 
     */	
	private java.lang.Integer peopleNumber;
    /**
     * 企业的福利级别       db_column: welfare_level  
     * 
     * 
     * 
     */	
	private java.lang.Integer welfareLevel;
    /**
     * 企业邮箱后缀1      db_column: email_postfix1  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String emailPostfix1;
    /**
     * 企业邮箱后缀2      db_column: email_postfix1  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String emailPostfix2;
    /**
     * 企业邮箱后缀3      db_column: email_postfix2  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String emailPostfix3;
    /**
     * 员工默认头像      db_column: emp_defult_avatar  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String empDefultAvatar;
    /**
     * 搜索是可见 0:不可见/1:可见     db_column: can_search  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String canSearch;
	
	//columns END

	public Enterprise(){
	}

	public Enterprise(
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
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setType(java.lang.Long value) {
		this.type = value;
	}
	
	public java.lang.Long getType() {
		return this.type;
	}
	public void setIndustry(java.lang.Long value) {
		this.industry = value;
	}
	
	public java.lang.Long getIndustry() {
		return this.industry;
	}
	public void setListed(java.lang.String value) {
		this.listed = value;
	}
	
	public java.lang.String getListed() {
		return this.listed;
	}
	public void setTurnover(java.lang.String value) {
		this.turnover = value;
	}
	
	public java.lang.String getTurnover() {
		return this.turnover;
	}
	public void setPeopleNumber(java.lang.Integer value) {
		this.peopleNumber = value;
	}
	
	public java.lang.Integer getPeopleNumber() {
		return this.peopleNumber;
	}
	public void setWelfareLevel(java.lang.Integer value) {
		this.welfareLevel = value;
	}
	
	public java.lang.Integer getWelfareLevel() {
		return this.welfareLevel;
	}

	public java.lang.String getEmailPostfix1() {
		return emailPostfix1;
	}

	public void setEmailPostfix1(java.lang.String emailPostfix1) {
		this.emailPostfix1 = emailPostfix1;
	}

	public java.lang.String getEmailPostfix2() {
		return emailPostfix2;
	}

	public void setEmailPostfix2(java.lang.String emailPostfix2) {
		this.emailPostfix2 = emailPostfix2;
	}

	public java.lang.String getEmailPostfix3() {
		return emailPostfix3;
	}

	public void setEmailPostfix3(java.lang.String emailPostfix3) {
		this.emailPostfix3 = emailPostfix3;
	}

	public java.lang.String getEmpDefultAvatar() {
		return empDefultAvatar;
	}

	public void setEmpDefultAvatar(java.lang.String empDefultAvatar) {
		this.empDefultAvatar = empDefultAvatar;
	}

	public java.lang.String getCanSearch() {
		return canSearch;
	}

	public void setCanSearch(java.lang.String canSearch) {
		this.canSearch = canSearch;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Type",getType())
			.append("Industry",getIndustry())
			.append("Listed",getListed())
			.append("Turnover",getTurnover())
			.append("PeopleNumber",getPeopleNumber())
			.append("WelfareLevel",getWelfareLevel())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Enterprise == false) return false;
		if(this == obj) return true;
		Enterprise other = (Enterprise)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

