package com.wode.factory.user.vo;

import java.io.Serializable;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;

import com.wode.factory.model.ProductSpecificationValue;

public class ProductSpeValVo implements Serializable {
	
	private String name;
	
	private String speName;
	
	private String speNameVal;
	
	private Long ProductId;
	
	public String getSpeNameVal() {
		return speNameVal;
	}
	
	public Long getProductId() {
		return ProductId;
	}
	public void setProductId(Long productId) {
		ProductId = productId;
	}
	public void setSpeNameVal(String speNameVal) {
		this.speNameVal = speNameVal;
	}
	public ProductSpeValVo() {
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpeName() {
		return speName;
	}
	public void setSpeName(String speName) {
		this.speName = speName;
	}
	private Date createTimeStart;
	
	private Date createTimeEnd;
	
	public Date getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
}
