/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
import com.wode.common.util.StringUtils;

@Table("t_product")
public class Product extends BaseModel implements java.io.Serializable {
	public java.lang.Long getShopId() {
		return shopId;
	}

	public void setShopId(java.lang.Long shopId) {
		this.shopId = shopId;
	}

	private static final long serialVersionUID = 5454155825314635342L;

	//date formats
	public static final String FORMAT_CREATE_DATE = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_DATE = DATE_TIME_FORMAT;

	//columns START
	/**
	 * 商品ID       db_column: id
	 */
	@PrimaryKey
	@Column(value = "id")
	@Id
	private java.lang.Long id;
	/**
	 * 商品_品牌ID       db_column: brand_id
	 */
	@Column(value = "brand_id")
	private java.lang.Long brandId;

	/**
	 * 商品母码       db_column: partnumber
	 *
	 * @Length(max=255)
	 */
	@Column(value = "partnumber")
	private java.lang.String partnumber;
	/**
	 * 商品全称       db_column: full_name
	 *
	 * @Length(max=255)
	 */
	@Column(value = "full_name")
	private java.lang.String fullName;
	/**
	 * 商品名称       db_column: name
	 *
	 * @Length(max=255)
	 */
	@Column(value = "name")
	private java.lang.String name;
	/**
	 * 促销说明       db_column: promotion
	 *
	 * @Length(max=255)
	 */
	@Column(value = "promotion")
	private java.lang.String promotion;
	/**
	 * 关键字       db_column: keyword
	 *
	 * @Length(max=255)
	 */
	@Column(value = "keyword")
	private java.lang.String keyword;
	/**
	 * 单位       db_column: unit
	 *
	 * @Length(max=50)
	 */
	@Column(value = "unit")
	private java.lang.String unit;
	/**
	 * 重量       db_column: weight
	 */
	@Column(value = "weight")
	private Long weight;
	/**
	 * 类型（实体、虚拟）       db_column: type
	 *
	 * @Length(max=100)
	 */
	@Column(value = "type")
	private java.lang.String type;
	/**
	 * 所属系统（厂、团）       db_column: model
	 *
	 * @Length(max=100)
	 */
	@Column(value = "model")
	private java.lang.String model;
	/**
	 * 显示价       db_column: show_price
	 *
	 * @Length(max=100)
	 */
	@Column(value = "show_price")
	private java.lang.String showPrice;
	/**
	 * 介绍       db_column: introduction
	 *
	 * @Length(max=65535)
	 */
	@Column(value = "introduction")
	private java.lang.String introduction;

	/**
	 * 介绍       db_column: introduction
	 *
	 * @Length(max=65535)
	 */
	@Column(value = "introduction_mobile")
	private java.lang.String introductionMobile;
	/**
	 * 主图       db_column: image
	 *
	 * @Length(max=255)
	 */
	@Column(value = "image")
	private java.lang.String image;
	/**
	 * 1：上架 -1：下架       db_column: is_marketable
	 */
	@Column(value = "is_marketable")
	private java.lang.Integer isMarketable;
	/**
	 * 推荐       db_column: is_recomment
	 */
	@Column(value = "is_recomment")
	private java.lang.Integer isRecomment;
	/**
	 * 审核状态 0：待审核  1：通过  -1不通过       db_column: status
	 *
	 * @Max(127)
	 */
	@Column(value = "status")
	private Integer status;
	/**
	 * 供应商ID       db_column: supplier_id
	 */
	@Column(value = "supplier_id")
	private java.lang.Long supplierId;
	/**
	 * 供应商ID       db_column: shop_id
	 */
	@Column(value = "shop_id")
	private java.lang.Long shopId;
	/**
	 * 创建日期       db_column: createDate
	 */
	@Column(value = "createDate")
	private java.util.Date createDate;
	/**
	 * 更新时间       db_column: updateDate
	 */
	@Column(value = "updateDate")
	private java.util.Date updateDate;
	/**
	 * 商品型号       db_column: marque
	 *
	 * @Length(max=255)
	 */
	@Column(value = "marque")
	private java.lang.String marque;
	/**
	 * 条形码       db_column: barcode
	 *
	 * @Length(max=255)
	 */
	@Column(value = "barcode")
	private java.lang.String barcode;
	/**
	 * 毛重       db_column: rough_weight
	 */
	@Column(value = "rough_weight")
	private BigDecimal roughWeight;
	/**
	 * 净重       db_column: net_weight
	 */
	@Column(value = "net_weight")
	private BigDecimal netWeight;
	/**
	 * 长（含外包装）       db_column: long
	 */
	@Column(value = "length")
	private BigDecimal length;
	/**
	 * 宽（含外包装）       db_column: width
	 */
	@Column(value = "width")
	private BigDecimal width;
	/**
	 * 高（含外包装）       db_column: height
	 */
	@Column(value = "height")
	private BigDecimal height;
	/**
	 * 体积       db_column: bulk
	 */
	@Column(value = "bulk")
	private BigDecimal bulk;
	/**
	 * 评论数
	 */
	@Column("comment_count")
	private Long commentCount;

	@Column
	private BigDecimal carriage;//运费
	@Column(value = "has_sku")
	private Integer hasSku;//是否含有sku（是否含有规格） ：1、商品只对于一种sku，即：不包含生产sku的规格  2、商品含有大于一种的sku信息

	//private Integer skuLockType;

	private java.lang.String shopname;
	
	@Column(value = "self_type")
	private Integer selfType;
	
	@Column(value = "self_time")
	private java.util.Date selfTime;
	

	public java.lang.String getShopname() {
		return shopname;
	}

	public void setShopname(java.lang.String shopname) {
		this.shopname = shopname;
	}

	private Long province;//产地：省id
	private Long town;//产地：市
	private Long county;//产地：县
	@Column(value = "produceaddress")
	private String produceaddress;//产地：中文地址

	private Long storeId;//仓库id

	private Long sendProvince;//发送地：省id
	private Long sendTown;//发送地：市
	private Long sendCounty;//发送地：县
	@Column(value = "sendAddress")
	private String sendAddress;//发送地：中文地址
	@Column(value = "categoryId")
	private Long categoryId;//产品类型：对应t_product_category表id
	@Column
	private String afterService;//售后服务
	@Column("stock_lock_type")
	private Integer stockLockType;//商品锁定类型   1:订单锁定 2：购物车锁定
	@Column
	private BigDecimal minprice;//sku的最小价格
	@Column
	private BigDecimal maxprice;//sku的最大价格
	@Column
	private Integer allnum;//sku所有商品的数量之和

	@Column(value = "sell_num")
	private Integer sellNum;//sku所有商品的所以售出数量之和

	private Integer sortNum;//商品展示排位（优先级）（sortNum）,（1：优先级最高，即排在第一位；2，3，……以此类推）

	@Column(value = "limit_cnt")
	private Integer limitCnt;//限购数量
	
	@Column("areas_name")
    private String areasName;

	@Column("areas_code")
    private String areasCode;

	@Column(value = "sale_kbn")
	private Integer saleKbn;	//销售区分 0：普通/1:特省',
	
	@Column("sale_note")
    private String saleNote;	//让利原因
	
	@Column("limit_kbn")
    private Integer limitKbn;	//限购区分 0:不限购/1:仅vip购/2:仅员工购3:企业级员工'

	@Column("locked")
    private Integer locked;	//锁定 0:未锁定/1:锁定',
	@Column("lock_reason")
    private String lockReason; //0:不限购/1:仅vip购/2:仅员工购'
	@Column("welfare_price")
    private BigDecimal welfarePrice;	//限购区分 0:不限购/1:仅vip购/2:仅员工购'

	@Column("div_level")
    private Integer divLevel;	//分配级别 -1:全部/x:各级别
	@Column("emp_price")
    private BigDecimal empPrice; //本企业员工购买价格
	@Column("emp_cash")
    private Integer empCash;	//员工特价区分 1:员工获得差价/0:员工不获得差价
	@Column("emp_level")
    private Integer empLevel;	//员工特价级别 -1:全部/x:各级别
	/**
	 * 问卷id       db_column: questionnaire_id
	 */
	@Column(value = "questionnaire_id")
	private java.lang.Long questionnaireId;
	
	@Column("outer_id")
	private String outerId;//商品外部编码
	
	//columns END

	/**
	 * ********************以下字段只供显示******************
	 */
	private BigDecimal marketPrice;//电商价只用于显示
	
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	private String brandName;//品牌名称

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Integer getLimitCnt() {
		return limitCnt;
	}

	public void setLimitCnt(Integer limitCnt) {
		this.limitCnt = limitCnt;
	}

	public String getAreasName() {
		return areasName;
	}

	public void setAreasName(String areasName) {
		this.areasName = areasName;
	}

	public String getAreasCode() {
		return areasCode;
	}

	public void setAreasCode(String areasCode) {
		this.areasCode = areasCode;
	}

	private List<ProductSpecifications> productSpecificationslist;

	private List<ProductSpecificationValue> productSpecificationValuelist;
	@Many(target = ProductAttribute.class, field = "productId")
	private List<ProductAttribute> productAttributelist;
	@Many(target = ProductParameterValue.class, field = "productId")
	private List<ProductParameterValue> productParameterValuelist;

	private List<ProductDetailList> productDetaillist;//清单list

	/**
	 * 商家分类
	 */
	private List<StoreCategory> storeCategories;

	private String storeCategoryName;//自定义商品类型

	private String storeCategoryId;  //自定义商品分类ID

	private String specificationsStr;

	private String categoryName;

	private String opinion;// 审批未通过原因
	
	private List<ProductLadder> productLadderlist;//商品阶梯价格集合
	
	public List<ProductLadder> getProductLadderlist() {
		return productLadderlist;
	}

	public void setProductLadderList(List<ProductLadder> productLadderlist) {
		this.productLadderlist = productLadderlist;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public List<StoreCategory> getStoreCategories() {
		return storeCategories;
	}

	public void setStoreCategories(List<StoreCategory> storeCategories) {
		this.storeCategories = storeCategories;
	}


	public Product() {
	}

	public Product(
			java.lang.Long id
	) {
		this.id = id;
	}

	public String getStoreCategoryName() {
		if (storeCategories != null && storeCategories.size() > 0) {
			StringBuffer storeCategoryName = new StringBuffer();
			for (StoreCategory sc : storeCategories) {
				storeCategoryName.append(sc.getName()).append(" ");
			}
			return storeCategoryName.toString();
		}
		return "";
	}

	public String getStoreCategoryId() {
		if (StringUtils.isEmpty(storeCategoryId)) {
			if (storeCategories != null && storeCategories.size() > 0) {
				StringBuffer storeCategoryIds = new StringBuffer();
				for (StoreCategory sc : storeCategories) {
					storeCategoryIds.append(sc.getId()).append(" ");
				}
				storeCategoryId = storeCategoryIds.toString();
			}
		}
		return storeCategoryId;
	}

	public void setStoreCategoryId(String value) {
		storeCategoryId= value;
	}
	
	public void setStoreCategoryName(String storeCategoryName) {
		this.storeCategoryName = storeCategoryName;
	}

	public String getSpecificationsStr() {
		return specificationsStr;
	}

	public void setSpecificationsStr(String specificationsStr) {
		this.specificationsStr = specificationsStr;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public List<ProductDetailList> getProductDetaillist() {
		return productDetaillist;
	}

	public void setProductDetaillist(List<ProductDetailList> productDetaillist) {
		this.productDetaillist = productDetaillist;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


	public List<ProductSpecificationValue> getProductSpecificationValuelist() {
		return productSpecificationValuelist;
	}

	public void setProductSpecificationValuelist(List<ProductSpecificationValue> productSpecificationValuelist) {
		this.productSpecificationValuelist = productSpecificationValuelist;
	}

	public List<ProductSpecifications> getProductSpecificationslist() {
		return productSpecificationslist;
	}

	public void setProductSpecificationslist(List<ProductSpecifications> productSpecificationslist) {
		this.productSpecificationslist = productSpecificationslist;
	}


	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public BigDecimal getCarriage() {
		return carriage;
	}

	public BigDecimal getMinprice() {
		return minprice;
	}

	public void setMinprice(BigDecimal minprice) {
		this.minprice = minprice;
	}

	public BigDecimal getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(BigDecimal maxprice) {
		this.maxprice = maxprice;
	}

	public Integer getAllnum() {
		return allnum;
	}

	public void setAllnum(Integer allnum) {
		this.allnum = allnum;
	}

	public Long getSendProvince() {
		return sendProvince;
	}

	public void setSendProvince(Long sendProvince) {
		this.sendProvince = sendProvince;
	}

	public Long getSendTown() {
		return sendTown;
	}

	public void setSendTown(Long sendTown) {
		this.sendTown = sendTown;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public Long getSendCounty() {
		return sendCounty;
	}

	public void setSendCounty(Long sendCounty) {
		this.sendCounty = sendCounty;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public Integer getStockLockType() {
		return stockLockType;
	}

	public void setStockLockType(Integer stockLockType) {
		this.stockLockType = stockLockType;
	}

	public String getAfterService() {
		return afterService;
	}

	public void setAfterService(String afterService) {
		this.afterService = afterService;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getProvince() {
		return province;
	}

	public void setProvince(Long province) {
		this.province = province;
	}

	public Long getTown() {
		return town;
	}

	public void setTown(Long town) {
		this.town = town;
	}

	public Long getCounty() {
		return county;
	}

	public void setCounty(Long county) {
		this.county = county;
	}

	public String getProduceaddress() {
		return produceaddress;
	}

	public void setProduceaddress(String produceaddress) {
		this.produceaddress = produceaddress;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Integer getHasSku() {
		return hasSku;
	}

	public void setHasSku(Integer hasSku) {
		this.hasSku = hasSku;
	}

	public void setCarriage(BigDecimal carriage) {
		this.carriage = carriage;
	}


	public void setId(java.lang.Long value) {
		this.id = value;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public void setBrandId(java.lang.Long value) {
		this.brandId = value;
	}

	public java.lang.Long getBrandId() {
		return this.brandId;
	}

	public void setPartnumber(java.lang.String value) {
		this.partnumber = value;
	}

	public java.lang.String getPartnumber() {
		return this.partnumber;
	}

	public void setFullName(java.lang.String value) {
		this.fullName = value;
	}

	public java.lang.String getFullName() {
		return this.fullName;
	}

	public void setName(java.lang.String value) {
		this.name = value;
	}

	public java.lang.String getName() {
		return this.name;
	}

	public void setPromotion(java.lang.String value) {
		this.promotion = value;
	}

	public java.lang.String getPromotion() {
		return this.promotion;
	}

	public void setKeyword(java.lang.String value) {
		this.keyword = value;
	}

	public java.lang.String getKeyword() {
		return this.keyword;
	}

	public void setUnit(java.lang.String value) {
		this.unit = value;
	}

	public java.lang.String getUnit() {
		return this.unit;
	}

	public void setWeight(Long value) {
		this.weight = value;
	}

	public Long getWeight() {
		return this.weight;
	}

	public void setType(java.lang.String value) {
		this.type = value;
	}

	public java.lang.String getType() {
		return this.type;
	}

	public void setModel(java.lang.String value) {
		this.model = value;
	}

	public java.lang.String getModel() {
		return this.model;
	}

	public void setShowPrice(java.lang.String value) {
		this.showPrice = value;
	}

	public java.lang.String getShowPrice() {
		return this.showPrice;
	}

	public void setIntroduction(java.lang.String value) {
		this.introduction = value;
	}

	public java.lang.String getIntroduction() {
		return this.introduction;
	}

	public void setImage(java.lang.String value) {
		this.image = value;
	}

	public java.lang.String getImage() {
		return this.image;
	}

	public java.lang.Integer getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(java.lang.Integer isMarketable) {
		this.isMarketable = isMarketable;
	}

	public void setIsRecomment(java.lang.Integer value) {
		this.isRecomment = value;
	}

	public java.lang.Integer getIsRecomment() {
		return this.isRecomment;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}

	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}


	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}

	public java.util.Date getCreateDate() {
		return this.createDate;
	}


	public void setUpdateDate(java.util.Date value) {
		this.updateDate = value;
	}

	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	public void setMarque(java.lang.String value) {
		this.marque = value;
	}

	public java.lang.String getMarque() {
		return this.marque;
	}

	public void setBarcode(java.lang.String value) {
		this.barcode = value;
	}

	public java.lang.String getBarcode() {
		return this.barcode;
	}

	public void setRoughWeight(BigDecimal value) {
		this.roughWeight = value;
	}

	public BigDecimal getRoughWeight() {
		return this.roughWeight;
	}

	public void setNetWeight(BigDecimal value) {
		this.netWeight = value;
	}

	public BigDecimal getNetWeight() {
		return this.netWeight;
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public void setWidth(BigDecimal value) {
		this.width = value;
	}

	public BigDecimal getWidth() {
		return this.width;
	}

	public void setHeight(BigDecimal value) {
		this.height = value;
	}

	public BigDecimal getHeight() {
		return this.height;
	}

	public void setBulk(BigDecimal value) {
		this.bulk = value;
	}

	public BigDecimal getBulk() {
		return this.bulk;
	}

//    public Integer getSkuLockType() {
//        return skuLockType;
//    }
//
//    public void setSkuLockType(Integer skuLockType) {
//        this.skuLockType = skuLockType;
//    }

	public Integer getSaleKbn() {
		return saleKbn;
	}

	public void setSaleKbn(Integer saleKbn) {
		this.saleKbn = saleKbn;
	}

	public String getSaleNote() {
		return saleNote;
	}

	public void setSaleNote(String saleNote) {
		this.saleNote = saleNote;
	}

	public Integer getLimitKbn() {
		return limitKbn;
	}

	public void setLimitKbn(Integer limitKbn) {
		this.limitKbn = limitKbn;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public String getLockReason() {
		return lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}

	public BigDecimal getWelfarePrice() {
		return welfarePrice;
	}

	public void setWelfarePrice(BigDecimal welfarePrice) {
		this.welfarePrice = welfarePrice;
	}

	public java.lang.String getIntroductionMobile() {
		return introductionMobile;
	}

	public void setIntroductionMobile(java.lang.String introductionMobile) {
		this.introductionMobile = introductionMobile;
	}

	public List<ProductAttribute> getProductAttributelist() {
		return productAttributelist;
	}

	public void setProductAttributelist(List<ProductAttribute> productAttributelist) {
		this.productAttributelist = productAttributelist;
	}

	public List<ProductParameterValue> getProductParameterValuelist() {
		return productParameterValuelist;
	}

	public void setProductParameterValuelist(
			List<ProductParameterValue> productParameterValuelist) {
		this.productParameterValuelist = productParameterValuelist;
	}

	public String getLink() {
		return "/" + this.id + ".html";
	}

	public Integer getDivLevel() {
		return divLevel;
	}

	public void setDivLevel(Integer divLevel) {
		this.divLevel = divLevel;
	}

	public BigDecimal getEmpPrice() {
		return empPrice;
	}

	public void setEmpPrice(BigDecimal empPrice) {
		this.empPrice = empPrice;
	}

	public Integer getEmpCash() {
		return empCash;
	}

	public void setEmpCash(Integer empCash) {
		this.empCash = empCash;
	}

	public Integer getEmpLevel() {
		return empLevel;
	}

	public void setEmpLevel(Integer empLevel) {
		this.empLevel = empLevel;
	}

	public java.lang.Long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(java.lang.Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", getId())
				.append("BrandId", getBrandId())
				.append("Partnumber", getPartnumber())
				.append("FullName", getFullName())
				.append("Name", getName())
				.append("Promotion", getPromotion())
				.append("Keyword", getKeyword())
				.append("Unit", getUnit())
				.append("Weight", getWeight())
				.append("Type", getType())
				.append("Model", getModel())
				.append("ShowPrice", getShowPrice())
				.append("Introduction", getIntroduction())
				.append("Image", getImage())
				.append("IsMarketable", getIsMarketable())
				.append("IsRecomment", getIsRecomment())
				.append("Status", getStatus())
				.append("SupplierId", getSupplierId())
				.append("ShopId", getShopId())
				.append("CreateDate", getCreateDate())
				.append("UpdateDate", getUpdateDate())
				.append("Marque", getMarque())
				.append("Barcode", getBarcode())
				.append("RoughWeight", getRoughWeight())
				.append("NetWeight", getNetWeight())
				.append("Length", getLength())
				.append("Width", getWidth())
				.append("Height", getHeight())
				.append("Bulk", getBulk())
				.append("LimitCnt", getLimitCnt())
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getId())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof Product == false) return false;
		if (this == obj) return true;
		Product other = (Product) obj;
		return new EqualsBuilder()
				.append(getId(), other.getId())
				.isEquals();
	}

	public String getOuterId() {
		return outerId == null ? this.barcode:outerId;
	}

	public void setOuterId(String outerId) {
		if(StringUtils.isEmpty(outerId)){
			this.outerId = this.barcode;
		}else{
			this.outerId = outerId;
		}
	}

	public Integer getSelfType() {
		return selfType;
	}

	public void setSelfType(Integer selfType) {
		this.selfType = selfType;
	}

	public java.util.Date getSelfTime() {
		return selfTime;
	}

	public void setSelfTime(java.util.Date selfTime) {
		this.selfTime = selfTime;
	}
	
}

