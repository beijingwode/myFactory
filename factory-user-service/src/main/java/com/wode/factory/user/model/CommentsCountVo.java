package com.wode.factory.user.model;

import java.io.Serializable;

import com.wode.factory.model.Comments;

public class CommentsCountVo extends Comments implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5698066845531303466L;
	private Integer praiseCount;//好评数
	private Integer nomalCount;//中评数
	private Integer badCount;//差评数
	private double goodsRatings;//商品平均评分
	private double serviceRatings;//服务平均评分
	private double logisticsRatings;//物流平均评分
	public Integer getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}
	public Integer getNomalCount() {
		return nomalCount;
	}
	public void setNomalCount(Integer nomalCount) {
		this.nomalCount = nomalCount;
	}
	public Integer getBadCount() {
		return badCount;
	}
	public void setBadCount(Integer badCount) {
		this.badCount = badCount;
	}
	public double getGoodsRatings() {
		return goodsRatings;
	}
	public void setGoodsRatings(double goodsRatings) {
		this.goodsRatings = goodsRatings;
	}
	public double getServiceRatings() {
		return serviceRatings;
	}
	public void setServiceRatings(double serviceRatings) {
		this.serviceRatings = serviceRatings;
	}
	public double getLogisticsRatings() {
		return logisticsRatings;
	}
	public void setLogisticsRatings(double logisticsRatings) {
		this.logisticsRatings = logisticsRatings;
	}
	
	
}
