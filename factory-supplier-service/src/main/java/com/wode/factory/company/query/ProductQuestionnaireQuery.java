package com.wode.factory.company.query;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;

import com.wode.common.frame.base.BaseQuery;

public class ProductQuestionnaireQuery extends BaseQuery implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3801168520313045991L;

	@Column("supplier_id")
    private java.lang.Long supplierId;
   
    @Column("product_name")
    private java.lang.String productName;
    @Column("status")
    private java.lang.Integer status;
   
    @Column("startDate")
    private java.lang.String startDate;

    @Column("endDate")
    private java.lang.String endDate;

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public java.lang.String getStartDate() {
		return startDate;
	}

	public void setStartDate(java.lang.String startDate) {
		this.startDate = startDate;
	}

	public java.lang.String getEndDate() {
		return endDate;
	}

	public void setEndDate(java.lang.String endDate) {
		this.endDate = endDate;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
    

    //columns END

}
