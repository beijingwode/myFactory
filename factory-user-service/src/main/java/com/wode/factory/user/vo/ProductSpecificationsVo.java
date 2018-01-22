package com.wode.factory.user.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.wode.factory.model.ProductSpecifications;

public class ProductSpecificationsVo extends ProductSpecifications implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer quantity;// 库存
	private Integer lockQuantity;// 锁定库存

	public BigDecimal carriage;// 运费
	private String carriageDes;// 运费描述
	
	private String freeDes;//包邮描述
	
	private Integer saleKbn;	//销售区分 0：普通/1:特省',
    private String saleNote;	//让利原因

	private int isMarketable;// 上下架标识
	
	private String salesPromotion; // 促销信息这里直接拼好了，前台展示就可以
	
	private boolean isQuestioned =true;//是否回答过调查问卷
	
	private String questionId;//调查问卷id

	private boolean limit = false;	//是否限购
	private String limitMsg = ""; //限购信息
	private Integer orderCount;	//匹配数量
	
	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Integer getLockQuantity() {
		return lockQuantity;
	}

	public void setLockQuantity(int lockQuantity) {
		this.lockQuantity = lockQuantity;
	}

	public BigDecimal getCarriage() {
		return carriage;
	}

	public void setCarriage(BigDecimal carriage) {
		this.carriage = carriage;
	}

	public int getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(int isMarketable) {
		this.isMarketable = isMarketable;
	}

	public String getCarriageDes() {
		return carriageDes;
	}

	public void setCarriageDes(String carriageDes) {
		this.carriageDes = carriageDes;
	}

	public Integer getSaleKbn() {
		return saleKbn;
	}

	public void setSaleKbn(Integer saleKbn) {
		this.saleKbn = saleKbn;
	}

	public String getSaleNote() {
		return saleNote;
	}

	public void setSaleNote(String saleNote) {
		this.saleNote = saleNote;
	}

	public String getFreeDes() {
		return freeDes;
	}

	public void setFreeDes(String freeDes) {
		this.freeDes = freeDes;
	}

	public String getSalesPromotion() {
		return salesPromotion;
	}

	public void setSalesPromotion(String salesPromotion) {
		this.salesPromotion = salesPromotion;
	}

	public boolean getIsQuestioned() {
		return isQuestioned;
	}

	public void setIsQuestioned(boolean isQuestioned) {
		this.isQuestioned = isQuestioned;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public boolean getLimit() {
		return limit;
	}

	public void setLimit(boolean limit) {
		this.limit = limit;
	}

	public String getLimitMsg() {
		return limitMsg;
	}

	public void setLimitMsg(String limitMsg) {
		this.limitMsg = limitMsg;
	}
	
}
