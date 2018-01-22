package com.wode.factory.model;

import java.math.BigDecimal;
import java.util.Date;

public class EntBenefitAppr {
    private Long id;

    private Long enterpriseId;

    private BigDecimal applyLimit;

    private Integer status;

    private String curYear;

    private String curSeason;

    private Date createDate;

    private Date updateDate;

    private String updateUser;

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

    public BigDecimal getApplyLimit() {
        return applyLimit;
    }

    public void setApplyLimit(BigDecimal applyLimit) {
        this.applyLimit = applyLimit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCurYear() {
        return curYear;
    }

    public void setCurYear(String curYear) {
        this.curYear = curYear == null ? null : curYear.trim();
    }

    public String getCurSeason() {
        return curSeason;
    }

    public void setCurSeason(String curSeason) {
        this.curSeason = curSeason == null ? null : curSeason.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}