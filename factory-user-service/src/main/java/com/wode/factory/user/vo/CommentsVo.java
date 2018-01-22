package com.wode.factory.user.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.wode.factory.model.Comments;


public class CommentsVo extends Comments {
    private String productName;//商品名称
    private String image;//图片
    private long itemId;//订单项ID
    private boolean commentFlag;//订单项表字段，评论标识
    private Date buyTime;//购买时间
    private Long supplyId;
    private String itemValues;
    private HashMap<String, String> proValues;
    private BigDecimal productPrice; //SKU价格
    private BigDecimal freight; //运费
    private Double score; //评分
    private Long count; //评论总数
    
    private Integer ticketUsage;//内购券使用量
    
    public Integer getTicketUsage() {
		return ticketUsage;
	}

	public void setTicketUsage(Integer ticketUsage) {
		this.ticketUsage = ticketUsage;
	}

	public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getItemValues() {
        return itemValues;
    }

    public void setItemValues(String itemValues) {
        this.itemValues = itemValues;
    }

    public HashMap<String, String> getProValues() {
        if (StringUtils.isNotBlank(itemValues)) {
            proValues = JSON.parseObject(this.itemValues, HashMap.class);
        }
        return proValues;
    }

    public void setProValues(HashMap<String, String> proValues) {
        this.proValues = proValues;
    }

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public boolean isCommentFlag() {
        return commentFlag;
    }

    public void setCommentFlag(boolean commentFlag) {
        this.commentFlag = commentFlag;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
