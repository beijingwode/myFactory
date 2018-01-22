package com.wode.factory.model;

import java.util.List;


public class Body {
    private Long id;
    private int state;
    private String updateUser;
    private String fetchUser;
    private String lastDealDate;
    private String updateDate;
    
    public List<LogisticsInfo> getHistory() {
		return history;
	}

	public void setHistory(List<LogisticsInfo> history) {
		this.history = history;
	}

	private List<LogisticsInfo> history;
    
    public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getFetchUser() {
		return fetchUser;
	}

	public void setFetchUser(String fetchUser) {
		this.fetchUser = fetchUser;
	}

	public String getLastDealDate() {
		return lastDealDate;
	}

	public void setLastDealDate(String lastDealDate) {
		this.lastDealDate = lastDealDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	private Long expressInfoId;

    private String expressNo;

    public Long getExpressInfoId() {
		return expressInfoId;
	}

	public void setExpressInfoId(Long expressInfoId) {
		this.expressInfoId = expressInfoId;
	}

	private String com;


    private String des;

    
    
    public Long getId() {
        return id;
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


    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }
}