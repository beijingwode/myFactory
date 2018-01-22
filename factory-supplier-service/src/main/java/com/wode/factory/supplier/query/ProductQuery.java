/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class ProductQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 商品ID */
	private java.lang.Long id;
	/** 商品_品牌ID */
	private java.lang.Long brandId;
	/** 商家分类_ID */
	private java.lang.Long stoId;
	/** 商品母码 */
	private java.lang.String partnumber;
	/** 商品全称 */
	private java.lang.String fullName;
	/** 商品名称 */
	private java.lang.String name;
	/** 促销说明 */
	private java.lang.String promotion;
	/** 关键字 */
	private java.lang.String keyword;
	/** 单位 */
	private java.lang.String unit;
	/** 重量 */
	private Long weight;
	/** 类型（实体、虚拟） */
	private java.lang.String type;
	/** 所属系统（厂、团） */
	private java.lang.String model;
	/** 显示价 */
	private java.lang.String showPrice;
	/** 介绍 */
	private java.lang.String introduction;
	/** 主图 */
	private java.lang.String image;
	/** 1：上架 -1：下架 */
	private java.lang.Boolean isMarketable;
	/** 推荐 */
	private java.lang.Integer isRecomment;
	/** 审核状态 0：待审核  1：通过  -1不通过 */
	private Integer status;
	/** 供应商ID */
	private java.lang.Long supplierId;
	/** 供应商ID */
	private java.lang.Long shopId;
	/** 创建日期 */
	private java.util.Date createDateBegin;
	private java.util.Date createDateEnd;
	/** 更新时间 */
	private java.util.Date updateDateBegin;
	private java.util.Date updateDateEnd;
	/** 商品型号 */
	private java.lang.String marque;
	/** 条形码 */
	private java.lang.String barcode;
	/** 毛重 */
	private java.lang.Double roughWeight;
	/** 净重 */
	private java.lang.Double netWeight;
	/** 长（含外包装） */
	private java.lang.Double length;
	/** 宽（含外包装） */
	private java.lang.Double width;
	/** 高（含外包装） */
	private java.lang.Double height;
	/** 体积 */
	private java.lang.Double bulk;
	/** province */
	private java.lang.Long province;
	/** 市 */
	private java.lang.Long town;
	/** 县 */
	private java.lang.Long county;

    private Double minprice;

    private Double maxprice;

    public Double getMinprice() {
        return minprice;
    }

    public void setMinprice(Double minprice) {
        this.minprice = minprice;
    }

    public Double getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(Double maxprice) {
        this.maxprice = maxprice;
    }

    public java.lang.Long getShopId() {
		return shopId;
	}

	public void setShopId(java.lang.Long shopId) {
		this.shopId = shopId;
	}

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getBrandId() {
		return this.brandId;
	}
	
	public void setBrandId(java.lang.Long value) {
		this.brandId = value;
	}
	
	public java.lang.Long getStoId() {
		return this.stoId;
	}
	
	public void setStoId(java.lang.Long value) {
		this.stoId = value;
	}
	
	public java.lang.String getPartnumber() {
		return this.partnumber;
	}
	
	public void setPartnumber(java.lang.String value) {
		this.partnumber = value;
	}
	
	public java.lang.String getFullName() {
		return this.fullName;
	}
	
	public void setFullName(java.lang.String value) {
		this.fullName = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getPromotion() {
		return this.promotion;
	}
	
	public void setPromotion(java.lang.String value) {
		this.promotion = value;
	}
	
	public java.lang.String getKeyword() {
		return this.keyword;
	}
	
	public void setKeyword(java.lang.String value) {
		this.keyword = value;
	}
	
	public java.lang.String getUnit() {
		return this.unit;
	}
	
	public void setUnit(java.lang.String value) {
		this.unit = value;
	}
	
	public Long getWeight() {
		return this.weight;
	}
	
	public void setWeight(Long value) {
		this.weight = value;
	}
	
	public java.lang.String getType() {
		return this.type;
	}
	
	public void setType(java.lang.String value) {
		this.type = value;
	}
	
	public java.lang.String getModel() {
		return this.model;
	}
	
	public void setModel(java.lang.String value) {
		this.model = value;
	}
	
	public java.lang.String getShowPrice() {
		return this.showPrice;
	}
	
	public void setShowPrice(java.lang.String value) {
		this.showPrice = value;
	}
	
	public java.lang.String getIntroduction() {
		return this.introduction;
	}
	
	public void setIntroduction(java.lang.String value) {
		this.introduction = value;
	}
	
	public java.lang.String getImage() {
		return this.image;
	}
	
	public void setImage(java.lang.String value) {
		this.image = value;
	}
	
	public java.lang.Boolean getIsMarketable() {
		return this.isMarketable;
	}
	
	public void setIsMarketable(java.lang.Boolean value) {
		this.isMarketable = value;
	}
	
	public java.lang.Integer getIsRecomment() {
		return this.isRecomment;
	}
	
	public void setIsRecomment(java.lang.Integer value) {
		this.isRecomment = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.util.Date getCreateDateBegin() {
		return this.createDateBegin;
	}
	
	public void setCreateDateBegin(java.util.Date value) {
		this.createDateBegin = value;
	}	
	
	public java.util.Date getCreateDateEnd() {
		return this.createDateEnd;
	}
	
	public void setCreateDateEnd(java.util.Date value) {
		this.createDateEnd = value;
	}
	
	public java.util.Date getUpdateDateBegin() {
		return this.updateDateBegin;
	}
	
	public void setUpdateDateBegin(java.util.Date value) {
		this.updateDateBegin = value;
	}	
	
	public java.util.Date getUpdateDateEnd() {
		return this.updateDateEnd;
	}
	
	public void setUpdateDateEnd(java.util.Date value) {
		this.updateDateEnd = value;
	}
	
	public java.lang.String getMarque() {
		return this.marque;
	}
	
	public void setMarque(java.lang.String value) {
		this.marque = value;
	}
	
	public java.lang.String getBarcode() {
		return this.barcode;
	}
	
	public void setBarcode(java.lang.String value) {
		this.barcode = value;
	}
	
	public java.lang.Double getRoughWeight() {
		return this.roughWeight;
	}
	
	public void setRoughWeight(java.lang.Double value) {
		this.roughWeight = value;
	}
	
	public java.lang.Double getNetWeight() {
		return this.netWeight;
	}
	
	public void setNetWeight(java.lang.Double value) {
		this.netWeight = value;
	}
	

	
	public java.lang.Double getLength() {
		return length;
	}

	public void setLength(java.lang.Double length) {
		this.length = length;
	}

	public java.lang.Double getWidth() {
		return this.width;
	}
	
	public void setWidth(java.lang.Double value) {
		this.width = value;
	}
	
	public java.lang.Double getHeight() {
		return this.height;
	}
	
	public void setHeight(java.lang.Double value) {
		this.height = value;
	}
	
	public java.lang.Double getBulk() {
		return this.bulk;
	}
	
	public void setBulk(java.lang.Double value) {
		this.bulk = value;
	}
	
	public java.lang.Long getProvince() {
		return this.province;
	}
	
	public void setProvince(java.lang.Long value) {
		this.province = value;
	}
	
	public java.lang.Long getTown() {
		return this.town;
	}
	
	public void setTown(java.lang.Long value) {
		this.town = value;
	}
	
	public java.lang.Long getCounty() {
		return this.county;
	}
	
	public void setCounty(java.lang.Long value) {
		this.county = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

