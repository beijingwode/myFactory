package com.wode.factory.vo;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ProductSpecifications;

public class ProductTrialLimitItemProductVo{

    private java.lang.Long id;
    private java.lang.Long productId;//商品id
    private java.lang.Long groupId;//分组id
    private String name;//商品标题
    private String groupOperator;//分组负责人
    private String supplierName;//所属供应商
    private List<ProductLadder> productLadderlist;//商品阶梯价格集合
    private List<ProductSpecifications> productSpecificationslist;
    private String image;//商品主图
    private String saleKbn;//标签
    private Integer isMarketable; //上架下架
    private Integer locked; //锁定
    private Date createDate; //创建时间
    private String groupName;//分组名称
    
    
    
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<ProductSpecifications> getProductSpecificationslist() {
		return productSpecificationslist;
	}
	public void setProductSpecificationslist(List<ProductSpecifications> productSpecificationslist) {
		this.productSpecificationslist = productSpecificationslist;
	}
	public List<ProductLadder> getProductLadderlist() {
		return productLadderlist;
	}
	public void setProductLadderlist(List<ProductLadder> productLadderlist) {
		this.productLadderlist = productLadderlist;
	}
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.Long getProductId() {
		return productId;
	}
	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}
	public java.lang.Long getGroupId() {
		return groupId;
	}
	public void setGroupId(java.lang.Long groupId) {
		this.groupId = groupId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroupOperator() {
		return groupOperator;
	}
	public void setGroupOperator(String groupOperator) {
		this.groupOperator = groupOperator;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSaleKbn() {
		return saleKbn;
	}
	public void setSaleKbn(String saleKbn) {
		this.saleKbn = saleKbn;
	}
	public Integer getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(Integer isMarketable) {
		this.isMarketable = isMarketable;
	}
	public Integer getLocked() {
		return locked;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    

}
