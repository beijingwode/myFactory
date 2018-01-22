package com.wode.factory.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 促销活动
 * @author Liuc
 *
 */
public class Promotion {
	// ID
    private Long id;
    // 活动名称
    private String name;
    // 顺序
    private Integer orders;
    // 开始时间
    private Date beginDate;
    // 结束时间
    private Date endDate;
    // 是否免运费1免运费 0不免
    private Boolean freeShipping;
    // 最小金额
    private BigDecimal minPrice;
    // 最小数量
    private Integer minQuantity;
    // 是否与其他促销共存:0可以共存，1：不可共存
    private Boolean exclusive;
    // 店铺ID,为空则为平台活动
    private Long shopId;
    // 规则
    private String rule;
    // 成功参加活动的商品数量
    private Long joinTotal;
    // 状态
    private Integer status;
    // 活动类型id
    private Integer type;
    // 创建时间
    private Date createDate;
    // 修改时间
    private Date modifyDate;
    // 是否能退货 1能 -1不能
    private Integer canReturn;
    // 是否能换货 1能 -1不能
    private Integer canReplace;
    // 是否保修 1是 -1否
    private Integer canRepair;
    // 活动介绍
    private String introduction;
    // 活动需知
    private String notice;
    
	//是否共享库存
	private Boolean stockShared;

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

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Boolean getExclusive() {
        return exclusive;
    }

    public void setExclusive(Boolean exclusive) {
        this.exclusive = exclusive;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Long getJoinTotal() {
        return joinTotal;
    }

    public void setJoinTotal(Long joinTotal) {
        this.joinTotal = joinTotal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getCanReturn() {
        return canReturn;
    }

    public void setCanReturn(Integer canReturn) {
        this.canReturn = canReturn;
    }

    public Integer getCanReplace() {
        return canReplace;
    }

    public void setCanReplace(Integer canReplace) {
        this.canReplace = canReplace;
    }

    public Integer getCanRepair() {
        return canRepair;
    }

    public void setCanRepair(Integer canRepair) {
        this.canRepair = canRepair;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getNotice() {
		return notice;
	}

    public void setNotice(String notice) {
		this.notice = notice;
	}
    

	public Boolean getStockShared() {
		return stockShared;
	}

	public void setStockShared(Boolean stockShared) {
		this.stockShared = stockShared;
	}
}