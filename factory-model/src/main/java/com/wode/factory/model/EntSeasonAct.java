package com.wode.factory.model;

import java.math.BigDecimal;
import java.util.Date;

public class EntSeasonAct {
    private Long id;

    private Long enterpriseId;

    /**
     * 上季度剩余内购券总额
     */
    private BigDecimal lastTicketSum;

    /**
     * 本季度获取内购券总额
     */
    private BigDecimal currentTicketSum;
    /**
     * 本季度划入福利额度
     */
    private BigDecimal currentTransferSum;

    /**
     * 内购券总额
     */
    private BigDecimal allTicketSum;

    /**
     * 已发放内购券总额
     */
    private BigDecimal giveTicketSum;

    /**
     * 以划拨内购券总额
     */
    private BigDecimal transfeTicketSum;

    /**
     * 上季度剩余现金总额
     */
    private BigDecimal lastCashSum;

    /**
     * 本季度获取现金总额
     */
    private BigDecimal currentCashSum;

    /**
     * 现金总额
     */
    private BigDecimal allCashSum;

    /**
     * 已发放现金总额
     */
    private BigDecimal giveCashSum;

    private String curYear;

    private String curSeason;

    private String useFlg;

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

    public BigDecimal getLastTicketSum() {
        return lastTicketSum;
    }

    public void setLastTicketSum(BigDecimal lastTicketSum) {
        this.lastTicketSum = lastTicketSum;
    }

    public BigDecimal getCurrentTicketSum() {
        return currentTicketSum;
    }

    public void setCurrentTicketSum(BigDecimal currentTicketSum) {
        this.currentTicketSum = currentTicketSum;
    }
    public BigDecimal getCurrentTransferSum() {
        return currentTransferSum;
    }

    public void setCurrentTransferSum(BigDecimal currentTransferSum) {
        this.currentTransferSum = currentTransferSum;
    }

    public BigDecimal getAllTicketSum() {
        return allTicketSum;
    }

    public void setAllTicketSum(BigDecimal allTicketSum) {
        this.allTicketSum = allTicketSum;
    }

    public BigDecimal getGiveTicketSum() {
        return giveTicketSum;
    }

    public void setGiveTicketSum(BigDecimal giveTicketSum) {
        this.giveTicketSum = giveTicketSum;
    }

    public BigDecimal getTransfeTicketSum() {
        return transfeTicketSum;
    }

    public void setTransfeTicketSum(BigDecimal transfeTicketSum) {
        this.transfeTicketSum = transfeTicketSum;
    }

    public BigDecimal getLastCashSum() {
        return lastCashSum;
    }

    public void setLastCashSum(BigDecimal lastCashSum) {
        this.lastCashSum = lastCashSum;
    }

    public BigDecimal getCurrentCashSum() {
        return currentCashSum;
    }

    public void setCurrentCashSum(BigDecimal currentCashSum) {
        this.currentCashSum = currentCashSum;
    }

    public BigDecimal getAllCashSum() {
        return allCashSum;
    }

    public void setAllCashSum(BigDecimal allCashSum) {
        this.allCashSum = allCashSum;
    }

    public BigDecimal getGiveCashSum() {
        return giveCashSum;
    }

    public void setGiveCashSum(BigDecimal giveCashSum) {
        this.giveCashSum = giveCashSum;
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

    public String getUseFlg() {
        return useFlg;
    }

    public void setUseFlg(String useFlg) {
        this.useFlg = useFlg == null ? null : useFlg.trim();
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