package com.wode.factory.model;

import java.math.BigDecimal;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_product_ladder")
public class ProductLadder  extends BaseModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2708087531156624311L;
	
	
	@PrimaryKey
	@Column(value = "id")
	@Id
	private Long id;
	//商家id
	@Column(value = "supplier_id")
	private Long supplierId;
	//商品id
	@Column(value = "product_id")
	private Long productId;
	//关联的skuid 用","分割
	@Column(value = "skuids")
	private String skuids;
	//数量
	@Column(value = "num")
	private Integer num;
	//价格
	@Column(value = "price")
	private BigDecimal price;
	
	@Column(value = "create_time")
	private java.util.Date createTime;
	@Column(value = "update_time")
	private java.util.Date updateTime;
	@Column(value = "type")
	private Integer type;//0表示正常阶梯价，1表示折扣
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getSkuids() {
		return skuids;
	}
	public void setSkuids(String skuids) {
		this.skuids = skuids;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.util.Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	

}
