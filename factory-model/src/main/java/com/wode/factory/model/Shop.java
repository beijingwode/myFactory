package com.wode.factory.model;


import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_shop")
public class Shop extends BaseModel implements java.io.Serializable{

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
     * user_id       db_column: user_id
     * 
     * 
     * 
     */ 
    @Column("user_id")
    private java.lang.Long userId;
    /**
     * 商户ID       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    /**
     * logo       db_column: logo
     * 
     * 
     * 
     */ 
    @Column("logo")
    private java.lang.String logo;
    /**
     * 店铺名称       db_column: shopname
     * 
     * 
     * 
     */ 
    @Column("shopname")
    private java.lang.String shopname;
    /**
     * 宣传标语       db_column: introduce
     * 
     * 
     * 
     */ 
    @Column("introduce")
    private java.lang.String introduce;
    /**
     * 导航下的图片       db_column: banner
     * 
     * 
     * 
     */ 
    @Column("banner")
    private java.lang.String banner;
    /**
     * 商铺类型0:专营店/1:专卖店/2:旗舰店       db_column: type
     * 
     * 
     * 
     */ 
    @Column("type")
    private java.lang.Integer type;
    /**
     * 店招图片       db_column: top_image
     * 
     * 
     * 
     */ 
    @Column("top_image")
    private java.lang.String topImage;
    /**
     * 品牌介绍       db_column: brand_introduce
     * 
     * 
     * 
     */ 
    @Column("brand_introduce")
    private java.lang.String brandIntroduce;
    /**
     * 品牌介绍图片       db_column: intro_image
     * 
     * 
     * 
     */ 
    @Column("intro_image")
    private java.lang.String introImage;
    /**
     * 售后公司固定电话       db_column: cus_tel
     * 
     * 
     * 
     */ 
    @Column("cus_tel")
    private java.lang.String cusTel;
    /**
     * 售后公司手机       db_column: cus_phone
     * 
     * 
     * 
     */ 
    @Column("cus_phone")
    private java.lang.String cusPhone;
    /**
     * 售后公司邮箱       db_column: cus_email
     * 
     * 
     * 
     */ 
    @Column("cus_email")
    private java.lang.String cusEmail;
    /**
     * 售后联系人       db_column: cus_contact
     * 
     * 
     * 
     */ 
    @Column("cus_contact")
    private java.lang.String cusContact;
    /**
     * 客服固定电话       db_column: ser_tel
     * 
     * 
     * 
     */ 
    @Column("ser_tel")
    private java.lang.String serTel;
    /**
     * 客服手机       db_column: ser_phone
     * 
     * 
     * 
     */ 
    @Column("ser_phone")
    private java.lang.String serPhone;
    /**
     * 客服邮箱       db_column: ser_email
     * 
     * 
     * 
     */ 
    @Column("ser_email")
    private java.lang.String serEmail;
    /**
     * 客服联系人       db_column: ser_contact
     * 
     * 
     * 
     */ 
    @Column("ser_contact")
    private java.lang.String serContact;
    /**
     * 店铺电话       db_column: shop_tel
     * 
     * 
     * 
     */ 
    @Column("shop_tel")
    private java.lang.String shopTel;
    /**
     * 店铺手机       db_column: shop_phone
     * 
     * 
     * 
     */ 
    @Column("shop_phone")
    private java.lang.String shopPhone;
    /**
     * 店铺邮箱       db_column: shop_email
     * 
     * 
     * 
     */ 
    @Column("shop_email")
    private java.lang.String shopEmail;
    /**
     * 店铺联络人       db_column: shop_contact
     * 
     * 
     * 
     */ 
    @Column("shop_contact")
    private java.lang.String shopContact;
    /**
     * 客服QQ       db_column: qq
     * 
     * 
     * 
     */ 
    @Column("qq")
    private java.lang.Long qq;

    //columns END

    private Double shippingFree;			//全场包邮设置
	private java.lang.String shopTel1;
	private java.lang.String shopTel2;
	private java.lang.String shopTel3;
	private java.lang.String cusTel1;
	private java.lang.String cusTel2;
	private java.lang.String cusTel3;
	private java.lang.String serTel1;
	private java.lang.String serTel2;
	private java.lang.String serTel3;
	//经营类目信息
	private List<ProductCategory> productCategoryList;
	//商家资质信息
	private List<Attachment> attachmentList;
	//品牌信息
	private List<ProductBrand> productBrandList;
	
    //粉丝数
    private Integer collectionNum;
    
    
    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.String getLogo() {
		return logo;
	}

	public void setLogo(java.lang.String logo) {
		this.logo = logo;
	}

	public java.lang.String getShopname() {
		return shopname;
	}

	public void setShopname(java.lang.String shopname) {
		this.shopname = shopname;
	}

	public java.lang.String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(java.lang.String introduce) {
		this.introduce = introduce;
	}

	public java.lang.String getBanner() {
		return banner;
	}

	public void setBanner(java.lang.String banner) {
		this.banner = banner;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.lang.String getTopImage() {
		return topImage;
	}

	public void setTopImage(java.lang.String topImage) {
		this.topImage = topImage;
	}

	public java.lang.String getBrandIntroduce() {
		return brandIntroduce;
	}

	public void setBrandIntroduce(java.lang.String brandIntroduce) {
		this.brandIntroduce = brandIntroduce;
	}

	public java.lang.String getIntroImage() {
		return introImage;
	}

	public void setIntroImage(java.lang.String introImage) {
		this.introImage = introImage;
	}

	public java.lang.String getCusTel() {
		return cusTel;
	}

	public void setCusTel(java.lang.String cusTel) {
		this.cusTel = cusTel;
	}

	public java.lang.String getCusPhone() {
		return cusPhone;
	}

	public void setCusPhone(java.lang.String cusPhone) {
		this.cusPhone = cusPhone;
	}

	public java.lang.String getCusEmail() {
		return cusEmail;
	}

	public void setCusEmail(java.lang.String cusEmail) {
		this.cusEmail = cusEmail;
	}

	public java.lang.String getCusContact() {
		return cusContact;
	}

	public void setCusContact(java.lang.String cusContact) {
		this.cusContact = cusContact;
	}

	public java.lang.String getSerTel() {
		return serTel;
	}

	public void setSerTel(java.lang.String serTel) {
		this.serTel = serTel;
	}

	public java.lang.String getSerPhone() {
		return serPhone;
	}

	public void setSerPhone(java.lang.String serPhone) {
		this.serPhone = serPhone;
	}

	public java.lang.String getSerEmail() {
		return serEmail;
	}

	public void setSerEmail(java.lang.String serEmail) {
		this.serEmail = serEmail;
	}

	public java.lang.String getSerContact() {
		return serContact;
	}

	public void setSerContact(java.lang.String serContact) {
		this.serContact = serContact;
	}

	public java.lang.String getShopTel() {
		return shopTel;
	}

	public void setShopTel(java.lang.String shopTel) {
		this.shopTel = shopTel;
	}

	public java.lang.String getShopPhone() {
		return shopPhone;
	}

	public void setShopPhone(java.lang.String shopPhone) {
		this.shopPhone = shopPhone;
	}

	public java.lang.String getShopEmail() {
		return shopEmail;
	}

	public void setShopEmail(java.lang.String shopEmail) {
		this.shopEmail = shopEmail;
	}

	public java.lang.String getShopContact() {
		return shopContact;
	}

	public void setShopContact(java.lang.String shopContact) {
		this.shopContact = shopContact;
	}

	public java.lang.Long getQq() {
		return qq;
	}

	public void setQq(java.lang.Long qq) {
		this.qq = qq;
	}

	public Integer getCollectionNum() {
		return collectionNum;
	}

	public void setCollectionNum(Integer collectionNum) {
		this.collectionNum = collectionNum;
	}

	public java.lang.String getShopTel1() {
		return shopTel1;
	}

	public void setShopTel1(java.lang.String shopTel1) {
		this.shopTel1 = shopTel1;
	}

	public java.lang.String getShopTel2() {
		return shopTel2;
	}

	public void setShopTel2(java.lang.String shopTel2) {
		this.shopTel2 = shopTel2;
	}

	public java.lang.String getShopTel3() {
		return shopTel3;
	}

	public void setShopTel3(java.lang.String shopTel3) {
		this.shopTel3 = shopTel3;
	}

	public java.lang.String getCusTel1() {
		return cusTel1;
	}

	public void setCusTel1(java.lang.String cusTel1) {
		this.cusTel1 = cusTel1;
	}

	public java.lang.String getCusTel2() {
		return cusTel2;
	}

	public void setCusTel2(java.lang.String cusTel2) {
		this.cusTel2 = cusTel2;
	}

	public java.lang.String getCusTel3() {
		return cusTel3;
	}

	public void setCusTel3(java.lang.String cusTel3) {
		this.cusTel3 = cusTel3;
	}

	public java.lang.String getSerTel1() {
		return serTel1;
	}

	public void setSerTel1(java.lang.String serTel1) {
		this.serTel1 = serTel1;
	}

	public java.lang.String getSerTel2() {
		return serTel2;
	}

	public void setSerTel2(java.lang.String serTel2) {
		this.serTel2 = serTel2;
	}

	public java.lang.String getSerTel3() {
		return serTel3;
	}

	public void setSerTel3(java.lang.String serTel3) {
		this.serTel3 = serTel3;
	}

	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}

	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<ProductBrand> getProductBrandList() {
		return productBrandList;
	}

	public void setProductBrandList(List<ProductBrand> productBrandList) {
		this.productBrandList = productBrandList;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("UserId",getUserId())
            .append("SupplierId",getSupplierId())
            .append("Logo",getLogo())
            .append("Shopname",getShopname())
            .append("Introduce",getIntroduce())
            .append("Banner",getBanner())
            .append("Type",getType())
            .append("TopImage",getTopImage())
            .append("BrandIntroduce",getBrandIntroduce())
            .append("IntroImage",getIntroImage())
            .append("CusTel",getCusTel())
            .append("CusPhone",getCusPhone())
            .append("CusEmail",getCusEmail())
            .append("CusContact",getCusContact())
            .append("SerTel",getSerTel())
            .append("SerPhone",getSerPhone())
            .append("SerEmail",getSerEmail())
            .append("SerContact",getSerContact())
            .append("ShopTel",getShopTel())
            .append("ShopPhone",getShopPhone())
            .append("ShopEmail",getShopEmail())
            .append("ShopContact",getShopContact())
            .append("Qq",getQq())
            .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

    public Double getShippingFree() {
		return shippingFree;
	}

	public void setShippingFree(Double shippingFree) {
		this.shippingFree = shippingFree;
	}
}

