package com.wode.factory.user.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.wode.common.util.TimeUtil;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.LogisticsInfo;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;


public class SubOrderVo extends Suborder {
	private String supplierName;//供应商名称
	private String shopTel;//商家电话
	private String shopPhone;//商家手机
	private BigDecimal pointNum; // 订单使用的积分数量
	private BigDecimal cashPay; // 现金券支付订单额度
	private String name;//收货人
	private String phone;//座机
	private String mobile;//手机号
	private String address;//收获地址
	private String invoiceTitle;//发票抬头
	private String note;//备注
	private String selfDelivery;//自提
	private String expressName;//快递公司名称
	private ExpressCompany express;//快递公司信息
	private List<Suborderitem> subOrderItems;
	private List<Returnorder> returnorderList;
	private List<Refundorder> refundorderList;
	private List<LogisticsInfo> listlogInfo;// 物流信息
	
	private String orderProductType;
	
	public List<Returnorder> getReturnorderList() {
		return returnorderList;
	}

	public void setReturnorderList(List<Returnorder> returnorderList) {
		this.returnorderList = returnorderList;
	}

	public List<Refundorder> getRefundorderList() {
		return refundorderList;
	}

	public void setRefundorderList(List<Refundorder> refundorderList) {
		this.refundorderList = refundorderList;
	}

	public String getExpressName() {
		return expressName;
	}
	
	public ExpressCompany getExpress() {
		return express;
	}

	public void setExpress(ExpressCompany express) {
		this.express = express;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	private String distanceAutomaticConfirm; // 距离自动确认收货时间还有*天*小时
	
	public String getDistanceAutomaticConfirm() {
		return distanceAutomaticConfirm;
	}
	public void setDistanceAutomaticConfirm(String distanceAutomaticConfirm) {
		this.distanceAutomaticConfirm = distanceAutomaticConfirm;
	}
	public List<LogisticsInfo> getListlogInfo() {
		return listlogInfo;
	}
	public void setListlogInfo(List<LogisticsInfo> listlogInfo) {
		this.listlogInfo = listlogInfo;
	}
	public BigDecimal getPointNum() {
		return pointNum;
	}
	public void setPointNum(BigDecimal pointNum) {
		this.pointNum = pointNum;
	}
	public String getShopTel() {
		return shopTel;
	}
	public void setShopTel(String shopTel) {
		this.shopTel = shopTel;
	}
	public String getShopPhone() {
		return shopPhone;
	}
	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<Suborderitem> getSubOrderItems() {
		return subOrderItems;
	}
	public void setSubOrderItems(List<Suborderitem> subOrderItems) {
		this.subOrderItems = subOrderItems;
	}

	public String getSelfDelivery() {
		return selfDelivery;
	}

	public void setSelfDelivery(String selfDelivery) {
		this.selfDelivery = selfDelivery;
	}

	public BigDecimal getCashPay() {
		return cashPay;
	}

	public void setCashPay(BigDecimal cashPay) {
		this.cashPay = cashPay;
	}

	public  boolean getCanUrgedDelivery(){
		 if(this.getUrgeNumber()<1&&TimeUtil.hourBetweenTime(this.getCreateTime(), new Date())<24){
			 return false;
		 }
		return !(this.getUrgeTime() != null && TimeUtil.hourBetweenTime(this.getUrgeTime(), new Date()) < 24);
	}
	
	public String getOrderProductType() {
		if(null != subOrderItems) {
			for (Suborderitem suborderitem : subOrderItems) {
				if(suborderitem.getSaleKbn() == 5 && suborderitem.getCommentFlag() == 0) {
					orderProductType = "1";//判断是否是试用商品，且未评价的
					return orderProductType;
				}
			}
		}
		return orderProductType;
	}
	
}
