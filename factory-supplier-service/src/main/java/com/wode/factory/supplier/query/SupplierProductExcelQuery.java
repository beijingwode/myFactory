package com.wode.factory.supplier.query;

import com.wode.common.frame.base.BaseQuery;

public class SupplierProductExcelQuery extends BaseQuery {

	private static final long serialVersionUID = 2127130307400584363L;
	private java.lang.Long id;
	private java.lang.String excelUrl;
	private java.util.Date createTime;
	private java.lang.String introduce;
	private java.lang.Long supplierId;
	private java.lang.Integer status;
	private java.lang.Integer totalNumber;
	private java.lang.Integer successNumber;
	private java.lang.Integer processingResult;
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.String getExcelUrl() {
		return excelUrl;
	}
	public void setExcelUrl(java.lang.String excelUrl) {
		this.excelUrl = excelUrl;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.lang.String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(java.lang.String introduce) {
		this.introduce = introduce;
	}
	public java.lang.Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}
	public java.lang.Integer getStatus() {
		return status;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	public java.lang.Integer getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(java.lang.Integer totalNumber) {
		this.totalNumber = totalNumber;
	}
	public java.lang.Integer getSuccessNumber() {
		return successNumber;
	}
	public void setSuccessNumber(java.lang.Integer successNumber) {
		this.successNumber = successNumber;
	}
	public java.lang.Integer getProcessingResult() {
		return processingResult;
	}
	public void setProcessingResult(java.lang.Integer processingResult) {
		this.processingResult = processingResult;
	}
}
