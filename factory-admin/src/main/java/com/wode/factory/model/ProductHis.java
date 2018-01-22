package com.wode.factory.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;

import com.wode.common.stereotype.PrimaryKey;

public class ProductHis implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4070048219356397589L;
	@PrimaryKey
    @Column("_id")
    private java.lang.String id;
	private Date createDate;
	private Long productId;
	private Product product;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
