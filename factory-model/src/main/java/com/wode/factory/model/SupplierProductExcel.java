package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.stereotype.PrimaryKey;

@Table("t_supplier_product_excel")
public class SupplierProductExcel  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Column("id")
	private java.lang.Long id;
    /**
     * excel文件路径       db_column: excel_url  
     * 
     * 
     * @Length(max=255)
     */	
	@Column("excel_url")
	private java.lang.String excelUrl;
    /**
     * createTime       db_column: create_time  
     * 
     * 
     * 
     */	
	@Column("create_time")
	private java.util.Date createTime;
    /**
     * 描述       db_column: introduce  
     * 
     * 
     * @Length(max=255)
     */	
	@Column("introduce")
	private java.lang.String introduce;
    /**
     * 商家id       db_column: supplier_id  
     * 
     * 
     * 
     */	
	@Column("supplier_id")
	private java.lang.Long supplierId;
    /**
     * 状态       db_column: status  
     * 
     * 
     * 
     */	
	@Column("status")
	private java.lang.Integer status;
	/**
	 * 总件数
	 */
	@Column("total_number")
	private java.lang.Integer totalNumber;
	/**
	 * 成功件数
	 */
	@Column("success_number")
	private java.lang.Integer successNumber;
	/**
	 * 处理结果
	 */
	@Column("processing_result")
	private java.lang.String processingResult;
	//columns END

	public SupplierProductExcel(){
	}

	public SupplierProductExcel(
		java.lang.Long id
	){
		this.id = id;
	}

		
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
		
	public void setExcelUrl(java.lang.String value) {
		this.excelUrl = value;
	}
	
	public java.lang.String getExcelUrl() {
		return this.excelUrl;
	}
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	 
	public java.lang.String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(java.lang.String introduce) {
		this.introduce = introduce;
	}

	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
		
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
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

	public java.lang.String getProcessingResult() {
		return processingResult;
	}

	public void setProcessingResult(java.lang.String processingResult) {
		this.processingResult = processingResult;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ExcelUrl",getExcelUrl())
			.append("CreateTime",getCreateTime())
			.append("Introduce",getIntroduce())
			.append("SupplierId",getSupplierId())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SupplierProductExcel == false) return false;
		if(this == obj) return true;
		SupplierProductExcel other = (SupplierProductExcel)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

