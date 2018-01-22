package com.wode.factory.company.query;

import java.io.Serializable;

public class EnterpriseVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1655301811860946399L;
	/**
     * 企业id
     */
    private Long id;
    /**
     * 企业名称
     */
    private String name;
    /**
     * 企业类型id
     */
    private Long typeId;
    /**
     * 企业类型
     */
    private String type;
    /**
     * 所属行业id
     */
    private Long industryId;
    /**
     * 所属行业
     */
    private String industry;
    /**
     * 是否上市
     */
    private String listed;
    /**
     * 营业规模(营业额)
     */
    private String turnover;
    /**
     * 人数
     */
    private Integer peopleNumber;
    /**
     * 企业的福利级别
     */
    private Integer welfareLevel;
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
	
	private java.lang.String nickName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getIndustryId() {
		return industryId;
	}
	public void setIndustryId(Long industryId) {
		this.industryId = industryId;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getListed() {
		return listed;
	}
	public void setListed(String listed) {
		this.listed = listed;
	}
	public String getTurnover() {
		return turnover;
	}
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}
	public Integer getPeopleNumber() {
		return peopleNumber;
	}
	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	public Integer getWelfareLevel() {
		return welfareLevel;
	}
	public void setWelfareLevel(Integer welfareLevel) {
		this.welfareLevel = welfareLevel;
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
	public java.lang.String getNickName() {
		return nickName;
	}
	public void setNickName(java.lang.String nickName) {
		this.nickName = nickName;
	}
	
}
