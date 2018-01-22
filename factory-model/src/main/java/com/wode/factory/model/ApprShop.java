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

@Table("t_appr_shop")
public class ApprShop extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7631288956105510312L;
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
     * 修改内容 0:店铺/1:修改分类/2:修改品牌'      db_column: appr_type
     * 
     * 
     * 
     */ 
    @Column("appr_type")
    private java.lang.String apprType;
    /**
     * 审核状态0：未提交1：待审核2：招商通过3：法务通过4：运营通过-1不通过       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    /**
     * 店铺id       db_column: shop_id
     * 
     * 
     * 
     */ 
    @Column("shop_id")
    private java.lang.Long shopId;
    /**
     * 店铺id       db_column: old_id
     * 
     * 
     * 
     */ 
    @Column("old_id")
    private java.lang.Long oldId;
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
    /**
     * 创建时间       db_column: creat_time
     * 
     * 
     * 
     */ 
    @Column("creat_time")
    private java.util.Date creatTime;
    /**
     * 创建者       db_column: creat_user
     * 
     * 
     * 
     */ 
    @Column("creat_user")
    private java.lang.Long creatUser;
    /**
     * 创建者       db_column: creat_name
     * 
     * 
     * 
     */ 
    @Column("creat_name")
    private java.lang.String creatName;
    /**
     * to_email       db_column: to_email
     * 
     * 
     * 
     */ 
    @Column("to_email")
    private java.lang.String toEmail;
    /**
     * 招商经理id       db_column: manager_id
     * 
     * 
     * 
     */ 
    @Column("manager_id")
    private java.lang.Long managerId;
    /**
     * 招商经理名       db_column: manager_name
     * 
     * 
     * 
     */ 
    @Column("manager_name")
    private java.lang.String managerName;
    /**
     * 招商审核时间       db_column: manager_chk_time
     * 
     * 
     * 
     */ 
    @Column("manager_chk_time")
    private java.util.Date managerChkTime;
    /**
     * 招商审核人员       db_column: manager_chk_id
     * 
     * 
     * 
     */ 
    @Column("manager_chk_id")
    private java.lang.Long managerChkId;
    /**
     * 招商审核信息       db_column: manager_chk_desc
     * 
     * 
     * 
     */ 
    @Column("manager_chk_desc")
    private java.lang.String managerChkDesc;
    /**
     * 招商审核信息图       db_column: manager_chk_img
     * 
     * 
     * 
     */ 
    @Column("manager_chk_img")
    private java.lang.String managerChkImg;
    /**
     * 法务审核时间       db_column: law_chk_time
     * 
     * 
     * 
     */ 
    @Column("law_chk_time")
    private java.util.Date lawChkTime;
    /**
     * 法务审核人员       db_column: law_chk_id
     * 
     * 
     * 
     */ 
    @Column("law_chk_id")
    private java.lang.Long lawChkId;
    /**
     * 法务审核信息       db_column: law_chk_desc
     * 
     * 
     * 
     */ 
    @Column("law_chk_desc")
    private java.lang.String lawChkDesc;
    /**
     * 法务审核信息图       db_column: law_chk_img
     * 
     * 
     * 
     */ 
    @Column("law_chk_img")
    private java.lang.String lawChkImg;
    /**
     * 运营审核时间       db_column: bus_chk_time
     * 
     * 
     * 
     */ 
    @Column("bus_chk_time")
    private java.util.Date busChkTime;
    /**
     * 运营审核人员       db_column: bus_chk_id
     * 
     * 
     * 
     */ 
    @Column("bus_chk_id")
    private java.lang.Long busChkId;
    /**
     * 运营审核信息       db_column: bus_chk_desc
     * 
     * 
     * 
     */ 
    @Column("bus_chk_desc")
    private java.lang.String busChkDesc;
    /**
     * 运营审核信息图       db_column: bus_chk_img
     * 
     * 
     * 
     */ 
    @Column("bus_chk_img")
    private java.lang.String busChkImg;
    /**
     * 修改时间       db_column: update_time
     * 
     * 
     * 
     */ 
    @Column("update_time")
    private java.util.Date updateTime;
    /**
     * 修改人id       db_column: update_user
     * 
     * 
     * 
     */ 
    @Column("update_user")
    private java.lang.Long updateUser;
    /**
     * 修改人名       db_column: update_name
     * 
     * 
     * 
     */ 
    @Column("update_name")
    private java.lang.String updateName;
    /**
     * 修改内容       db_column: update_desc
     * 
     * 
     * 
     */ 
    @Column("update_desc")
    private java.lang.String updateDesc;

    //columns END

	private java.lang.String shopTel1;
	private java.lang.String shopTel2;
	private java.lang.String shopTel3;
	private java.lang.String cusTel1;
	private java.lang.String cusTel2;
	private java.lang.String cusTel3;
	private java.lang.String serTel1;
	private java.lang.String serTel2;
	private java.lang.String serTel3;
	private java.lang.String comName;
	private List<ProductBrand> productBrandList;//品牌集合
	private List<ProductBrandImage> productBrandImageList;//品牌图片集合
	private List<BrandProducttype> brandProducttypeList;//品类品牌集
	private List<SupplierCategory> supplierCategoryList;//品类集
	private List<Attachment> attachmentList;//资质集

	public List<ProductBrand> getProductBrandList() {
		return productBrandList;
	}
	public void setProductBrandList(List<ProductBrand> productBrandList) {
		this.productBrandList = productBrandList;
	}
	public List<ProductBrandImage> getProductBrandImageList() {
		return productBrandImageList;
	}
	public void setProductBrandImageList(List<ProductBrandImage> productBrandImageList) {
		this.productBrandImageList = productBrandImageList;
	}
	public List<BrandProducttype> getBrandProducttypeList() {
		return brandProducttypeList;
	}
	public void setBrandProducttypeList(List<BrandProducttype> brandProducttypeList) {
		this.brandProducttypeList = brandProducttypeList;
	}
	public List<SupplierCategory> getSupplierCategoryList() {
		return supplierCategoryList;
	}
	public void setSupplierCategoryList(List<SupplierCategory> supplierCategoryList) {
		this.supplierCategoryList = supplierCategoryList;
	}
	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}
	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Status",getStatus())
            .append("ShopId",getShopId())
            .append("OldId",getOldId())
            .append("UserId",getUserId())
            .append("SupplierId",getSupplierId())
            .append("ComName",getComName())
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
            .append("CreatTime",getCreatTime())
            .append("CreatUser",getCreatUser())
            .append("CreatName",getCreatName())
            .append("ToEmail",getToEmail())
            .append("ManagerId",getManagerId())
            .append("ManagerName",getManagerName())
            .append("ManagerChkTime",getManagerChkTime())
            .append("ManagerChkId",getManagerChkId())
            .append("ManagerChkDesc",getManagerChkDesc())
            .append("ManagerChkImg",getManagerChkImg())
            .append("LawChkTime",getLawChkTime())
            .append("LawChkId",getLawChkId())
            .append("LawChkDesc",getLawChkDesc())
            .append("LawChkImg",getLawChkImg())
            .append("BusChkTime",getBusChkTime())
            .append("BusChkId",getBusChkId())
            .append("BusChkDesc",getBusChkDesc())
            .append("BusChkImg",getBusChkImg())
            .append("UpdateTime",getUpdateTime())
            .append("UpdateUser",getUpdateUser())
            .append("UpdateName",getUpdateName())
            .toString();
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

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.Long getShopId() {
		return shopId;
	}

	public void setShopId(java.lang.Long shopId) {
		this.shopId = shopId;
	}

	public java.lang.Long getOldId() {
		return oldId;
	}

	public void setOldId(java.lang.Long oldId) {
		this.oldId = oldId;
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

	public java.util.Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(java.util.Date creatTime) {
		this.creatTime = creatTime;
	}

	public java.lang.Long getCreatUser() {
		return creatUser;
	}

	public void setCreatUser(java.lang.Long creatUser) {
		this.creatUser = creatUser;
	}

	public java.lang.String getCreatName() {
		return creatName;
	}

	public void setCreatName(java.lang.String creatName) {
		this.creatName = creatName;
	}

	public java.lang.String getToEmail() {
		return toEmail;
	}

	public void setToEmail(java.lang.String toEmail) {
		this.toEmail = toEmail;
	}

	public java.lang.Long getManagerId() {
		return managerId;
	}

	public void setManagerId(java.lang.Long managerId) {
		this.managerId = managerId;
	}

	public java.lang.String getManagerName() {
		return managerName;
	}

	public void setManagerName(java.lang.String managerName) {
		this.managerName = managerName;
	}

	public java.util.Date getManagerChkTime() {
		return managerChkTime;
	}

	public void setManagerChkTime(java.util.Date managerChkTime) {
		this.managerChkTime = managerChkTime;
	}

	public java.lang.Long getManagerChkId() {
		return managerChkId;
	}

	public void setManagerChkId(java.lang.Long managerChkId) {
		this.managerChkId = managerChkId;
	}

	public java.lang.String getManagerChkDesc() {
		return managerChkDesc;
	}

	public void setManagerChkDesc(java.lang.String managerChkDesc) {
		this.managerChkDesc = managerChkDesc;
	}

	public java.lang.String getManagerChkImg() {
		return managerChkImg;
	}

	public void setManagerChkImg(java.lang.String managerChkImg) {
		this.managerChkImg = managerChkImg;
	}

	public java.util.Date getLawChkTime() {
		return lawChkTime;
	}

	public void setLawChkTime(java.util.Date lawChkTime) {
		this.lawChkTime = lawChkTime;
	}

	public java.lang.Long getLawChkId() {
		return lawChkId;
	}

	public void setLawChkId(java.lang.Long lawChkId) {
		this.lawChkId = lawChkId;
	}

	public java.lang.String getLawChkDesc() {
		return lawChkDesc;
	}

	public void setLawChkDesc(java.lang.String lawChkDesc) {
		this.lawChkDesc = lawChkDesc;
	}

	public java.lang.String getLawChkImg() {
		return lawChkImg;
	}

	public void setLawChkImg(java.lang.String lawChkImg) {
		this.lawChkImg = lawChkImg;
	}

	public java.util.Date getBusChkTime() {
		return busChkTime;
	}

	public void setBusChkTime(java.util.Date busChkTime) {
		this.busChkTime = busChkTime;
	}

	public java.lang.Long getBusChkId() {
		return busChkId;
	}

	public void setBusChkId(java.lang.Long busChkId) {
		this.busChkId = busChkId;
	}

	public java.lang.String getBusChkDesc() {
		return busChkDesc;
	}

	public void setBusChkDesc(java.lang.String busChkDesc) {
		this.busChkDesc = busChkDesc;
	}

	public java.lang.String getBusChkImg() {
		return busChkImg;
	}

	public void setBusChkImg(java.lang.String busChkImg) {
		this.busChkImg = busChkImg;
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

	public java.lang.String getComName() {
		return comName;
	}

	public void setComName(java.lang.String comName) {
		this.comName = comName;
	}

	public java.lang.String getApprType() {
		return apprType;
	}

	public void setApprType(java.lang.String apprType) {
		this.apprType = apprType;
	}

	public java.lang.String getUpdateDesc() {
		return updateDesc;
	}

	public void setUpdateDesc(java.lang.String updateDesc) {
		this.updateDesc = updateDesc;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }
}

