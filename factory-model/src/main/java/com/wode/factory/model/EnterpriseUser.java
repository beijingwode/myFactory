package com.wode.factory.model;

import java.io.Serializable;

public class EnterpriseUser implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5095786948339715049L;
	private Long id;
    /**
     * 员工序号
     */
    private String empNumber;
    /**
     * 福利级别
     */
    private Integer welfareLevel;
    /**
     * 工龄
     */
    private Integer seniority;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 企业id
     */
    private Long enterpriseId;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 是否已注销   0:未注销，1:已注销
     */
    private Byte logout;
    /**
     * 类型  1:管理员，2:员工
     */
    private Integer type;
    /**
     * 账号
     */
    private String userName;
    /**
     * 手机号 
     */
    private String phone;
    /**
     * 职务
     */
    private String duty;
    
    /**
     * 部门
     */
    private String sectionName;
    
    


	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(String empNumber) {
        this.empNumber = empNumber;
    }

    public Integer getWelfareLevel() {
        return welfareLevel;
    }

    public void setWelfareLevel(Integer welfareLevel) {
        this.welfareLevel = welfareLevel;
    }

    public Integer getSeniority() {
        return seniority;
    }

    public void setSeniority(Integer seniority) {
        this.seniority = seniority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getLogout() {
        return logout;
    }

    public void setLogout(Byte logout) {
        this.logout = logout;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}