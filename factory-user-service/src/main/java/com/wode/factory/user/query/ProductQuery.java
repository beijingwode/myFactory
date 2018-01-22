/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.query;

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
	/** 类型（1、实体、2、虚拟） */
	private java.lang.String type;
	/** 所属系统（1、厂、2团） */
	private java.lang.String model;
	/** 显示价 */
	private java.lang.String showPrice;
	/** 介绍 */
	private java.lang.String introduction;
	/** 主图 */
	private java.lang.String image;
	/** 0:暂存 1：上架 -1：下架 (售完下架) -2:下架（手动下架）  -10:已删除（大于-10的均为未删除） */
	private java.lang.Integer isMarketable;
	/** 推荐 */
	private java.lang.Integer isRecomment;
	/** 审核状态 0：未提交审核，1：待审核 2：通过  -1不通过  */
	private Integer status;
	/** 供应商ID */
	private java.lang.Long supplierId;
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
	/** 是否含有规格 ：1、商品只对于一种sku即，不包含规格  2、商品含有大于一种的sku信息 */
	private java.lang.Integer hasSku;
	/** 1:订单锁定 2：购物车锁定 */
	private Integer stockLockType;
	/** 产地：省 */
	private java.lang.Long province;
	/** 产地：市 */
	private java.lang.Long town;
	/** 产地：县 */
	private java.lang.Long county;
	/** 产地：例如：北京市朝阳区草场地 */
	private java.lang.String produceaddress;
	/** 运费 */
	private java.lang.Double carriage;
	/** 关联仓库id：为了找到发货路径 */
	private java.lang.Long storeId;
	/** 商品类目id */
	private java.lang.Long categoryId;
	/** 售后服务 */
	private java.lang.String afterService;
	/** 发送地址：省 */
	private java.lang.Long sendProvince;
	/** 发送地址：市 */
	private java.lang.Long sendTown;
	/** 发送地址：县 */
	private java.lang.Long sendCounty;
	/** 发送地址 */
	private java.lang.String sendAddress;
	/** 本商品包含的sku的最小的价格 */
	private java.lang.Double minprice;
	/** 本商品包含的sku的最大的价格 */
	private java.lang.Double maxprice;
	/** 本商品对应的所有库存的数量之和 */
	private java.lang.Integer allnum;

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
	
	public java.lang.Integer getIsMarketable() {
		return this.isMarketable;
	}
	
	public void setIsMarketable(java.lang.Integer value) {
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
		return this.length;
	}
	
	public void setLength(java.lang.Double value) {
		this.length = value;
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
	
	public java.lang.Integer getHasSku() {
		return this.hasSku;
	}
	
	public void setHasSku(java.lang.Integer value) {
		this.hasSku = value;
	}
	
	public Integer getStockLockType() {
		return this.stockLockType;
	}
	
	public void setStockLockType(Integer value) {
		this.stockLockType = value;
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
	
	public java.lang.String getProduceaddress() {
		return this.produceaddress;
	}
	
	public void setProduceaddress(java.lang.String value) {
		this.produceaddress = value;
	}
	
	public java.lang.Double getCarriage() {
		return this.carriage;
	}
	
	public void setCarriage(java.lang.Double value) {
		this.carriage = value;
	}
	
	public java.lang.Long getStoreId() {
		return this.storeId;
	}
	
	public void setStoreId(java.lang.Long value) {
		this.storeId = value;
	}
	
	public java.lang.Long getCategoryId() {
		return this.categoryId;
	}
	
	public void setCategoryId(java.lang.Long value) {
		this.categoryId = value;
	}
	
	public java.lang.String getAfterService() {
		return this.afterService;
	}
	
	public void setAfterService(java.lang.String value) {
		this.afterService = value;
	}
	
	public java.lang.Long getSendProvince() {
		return this.sendProvince;
	}
	
	public void setSendProvince(java.lang.Long value) {
		this.sendProvince = value;
	}
	
	public java.lang.Long getSendTown() {
		return this.sendTown;
	}
	
	public void setSendTown(java.lang.Long value) {
		this.sendTown = value;
	}
	
	public java.lang.Long getSendCounty() {
		return this.sendCounty;
	}
	
	public void setSendCounty(java.lang.Long value) {
		this.sendCounty = value;
	}
	
	public java.lang.String getSendAddress() {
		return this.sendAddress;
	}
	
	public void setSendAddress(java.lang.String value) {
		this.sendAddress = value;
	}
	
	public java.lang.Double getMinprice() {
		return this.minprice;
	}
	
	public void setMinprice(java.lang.Double value) {
		this.minprice = value;
	}
	
	public java.lang.Double getMaxprice() {
		return this.maxprice;
	}
	
	public void setMaxprice(java.lang.Double value) {
		this.maxprice = value;
	}
	
	public java.lang.Integer getAllnum() {
		return this.allnum;
	}
	
	public void setAllnum(java.lang.Integer value) {
		this.allnum = value;
	}
	

	public java.lang.Long getShopId() {
		return shopId;
	}

	public void setShopId(java.lang.Long shopId) {
		this.shopId = shopId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

