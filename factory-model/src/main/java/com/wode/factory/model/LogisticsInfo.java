package com.wode.factory.model;

import java.util.Date;

public class LogisticsInfo {
    private Long id;
    
    private Long expressInfoId;

    private String expressNo;

    public Long getExpressInfoId() {
		return expressInfoId;
	}

	public void setExpressInfoId(Long expressInfoId) {
		this.expressInfoId = expressInfoId;
	}

	private String com;

    private Date dealDate;

    private String status;

    private String des;

    private Date createDate;

    private String createUser;

    private String strCreate;
    
    
    public Long getId() {
        return id;
    }

    public String getStrCreate() {
		return strCreate;
	}

	public void setStrCreate(String strCreate) {
		this.strCreate = strCreate;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo == null ? null : expressNo.trim();
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com == null ? null : com.trim();
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }
}