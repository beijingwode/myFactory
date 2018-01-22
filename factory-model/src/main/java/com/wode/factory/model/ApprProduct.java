package com.wode.factory.model;

import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.stereotype.PrimaryKey;

@Table("t_appr_product")
public class ApprProduct extends Product implements java.io.Serializable {
	public ApprProduct() {
	}
	
	// date formats
	public static final String FORMAT_CREATE_DATE = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_DATE = DATE_TIME_FORMAT;

	private Integer savestate;
    private String updateDesc;
	

	public String getUpdateDesc() {
		return updateDesc;
	}

	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}

	public Integer getSavestate() {
		return savestate;
	}

	public void setSavestate(Integer savestate) {
		this.savestate = savestate;
	}
	
	private List<ProductShipping> productShipping;//运费模板
	private List<ProductSpecificationsImage> productSpecificationsImage;//商品主图
	private List<Inventory> productInventory;//商品库存
	
	public List<ProductShipping> getProductShipping() {
		return productShipping;
	}
	
	public void setProductShipping(List<ProductShipping> productShipping) {
		this.productShipping = productShipping;
	}

	public List<ProductSpecificationsImage> getProductSpecificationsImage() {
		return productSpecificationsImage;
	}

	public void setProductSpecificationsImage(List<ProductSpecificationsImage> productSpecificationsImage) {
		this.productSpecificationsImage = productSpecificationsImage;
	}

	public List<Inventory> getProductInventory() {
		return productInventory;
	}

	public void setProductInventory(List<Inventory> productInventory) {
		this.productInventory = productInventory;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2576531373512796728L;
	// columns START
	/**
	 * 商品ID       db_column: id
	 */
	@PrimaryKey
	@Column(value = "id")
	@Id
	private java.lang.Long id;
	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	/**
	 * 商品ID db_column: product_id
	 * 
	 * 
	 * 
	 */
	@Column("product_id")
	private java.lang.Long productId;
	/**
	 * 修改时间 db_column: update_time
	 * 
	 * 
	 * 
	 */
	@Column("update_time")
	private java.util.Date updateTime;
	/**
	 * 修改人id db_column: update_user
	 * 
	 * 
	 * 
	 */
	@Column("update_user")
	private java.lang.Long updateUser;
	/**
	 * 修改人名 db_column: update_name
	 * 
	 * 
	 * 
	 */
	@Column("update_name")
	private java.lang.String updateName;

	// columns END

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId())
				.append("ProductId", getProductId()).append("BrandId", getBrandId())
				.append("Partnumber", getPartnumber()).append("FullName", getFullName()).append("Name", getName())
				.append("Promotion", getPromotion()).append("Keyword", getKeyword()).append("Unit", getUnit())
				.append("Weight", getWeight()).append("Type", getType()).append("Model", getModel())
				.append("ShowPrice", getShowPrice()).append("Introduction", getIntroduction())
				.append("Image", getImage()).append("IsMarketable", getIsMarketable())
				.append("IsRecomment", getIsRecomment()).append("Status", getStatus())
				.append("SupplierId", getSupplierId()).append("ShopId", getShopId())
				.append("CreateDate", getCreateDate()).append("UpdateDate", getUpdateDate())
				.append("Marque", getMarque()).append("Barcode", getBarcode()).append("RoughWeight", getRoughWeight())
				.append("NetWeight", getNetWeight()).append("Length", getLength()).append("Width", getWidth())
				.append("Height", getHeight()).append("Bulk", getBulk()).append("HasSku", getHasSku())
				.append("StockLockType", getStockLockType()).append("Province", getProvince()).append("Town", getTown())
				.append("County", getCounty()).append("Produceaddress", getProduceaddress())
				.append("Carriage", getCarriage()).append("StoreId", getStoreId()).append("CategoryId", getCategoryId())
				.append("AfterService", getAfterService()).append("SendProvince", getSendProvince())
				.append("SendTown", getSendTown()).append("SendCounty", getSendCounty())
				.append("SendAddress", getSendAddress()).append("Minprice", getMinprice())
				.append("Maxprice", getMaxprice()).append("Allnum", getAllnum()).append("SellNum", getSellNum())
				.append("CommentCount", getCommentCount()).append("IntroductionMobile", getIntroductionMobile())
				.append("SortNum", getSortNum()).append("LimitCnt", getLimitCnt()).append("AreasName", getAreasName())
				.append("AreasCode", getAreasCode()).append("SaleKbn", getSaleKbn()).append("SaleNote", getSaleNote())
				.append("LimitKbn", getLimitKbn()).append("Locked", getLocked()).append("LockReason", getLockReason())
				.append("WelfarePrice", getWelfarePrice()).append("DivLevel", getDivLevel())
				.append("EmpPrice", getEmpPrice()).append("EmpCash", getEmpCash()).append("EmpLevel", getEmpLevel())
				.append("UpdateTime", getUpdateTime()).append("UpdateUser", getUpdateUser())
				.append("UpdateName", getUpdateName())
		        .append("savestate", getSavestate())
		        .append("updateDesc", getUpdateDesc()).toString();
	}

	public java.lang.Long getProductId() {
		return productId;
	}

	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(java.lang.Long updateUser) {
		this.updateUser = updateUser;
	}

	public java.lang.String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(java.lang.String updateName) {
		this.updateName = updateName;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

}
