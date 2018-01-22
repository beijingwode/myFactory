package com.wode.factory.user.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_share_auto_buy")
public class UserShareAutoBuy extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3829652353165422235L;
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
     * 分享表id       db_column: share_id
     * 
     * 
     * 
     */ 
    @Column("share_id")
    private java.lang.Long shareId;
    
    /**
     * product_id       db_column: product_id
     * 
     * 
     * 
     */ 
    @Column("product_id")
    private java.lang.Long productId;
    /**
     * sku_id       db_column: sku_id
     * 
     * 
     * 
     */ 
    @Column("sku_id")
    private java.lang.Long skuId;
    /**
     * sku 数量       db_column: sku_num
     * 
     * 
     * 
     */ 
    @Column("sku_num")
    private java.lang.Integer skuNum;
    /**
     * sku 主图       db_column: image
     * 
     * 
     * 
     */ 
    @Column("image")
    private java.lang.String image;
    /**
     * 商品名称       db_column: product_name
     * 
     * 
     * 
     */ 
    @Column("product_name")
    private java.lang.String productName;
    /**
     * 规格值       db_column: itemValues
     * 
     * 
     * 
     */ 
    @Column("itemValues")
    private java.lang.String itemValues;
    /**
     * 分享类型 0:亲友分享 1:好友分享 2:商品分享 6:团购商品分享 7:订单分享 8:邀请好友       db_column: share_type
     * 
     * 
     * 
     */ 
    @Column("share_type")
    private java.lang.Integer shareType;
    /**
     * 微信带参数二维码 关注用临时       db_column: wx_temp_qr_url
     * 
     * 
     * 
     */ 
    @Column("wx_temp_qr_url")
    private java.lang.String wxTempQrUrl;
    /**
     * 微信临时二维码有效期限       db_column: wx_temp_limit_end
     * 
     * 
     * 
     */ 
    @Column("wx_temp_limit_end")
    private java.util.Date wxTempLimitEnd;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("ShareId",getShareId())
            .append("SkuId",getSkuId())
            .append("SkuNum",getSkuNum())
            .append("Image",getImage())
            .append("ProductName",getProductName())
            .append("ItemValues",getItemValues())
            .append("ShareType",getShareType())
            .append("WxTempQrUrl",getWxTempQrUrl())
            .append("WxTempLimitEnd",getWxTempLimitEnd())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getShareId() {
		return shareId;
	}

	public void setShareId(java.lang.Long shareId) {
		this.shareId = shareId;
	}

	public java.lang.Long getProductId() {
		return productId;
	}

	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}

	public java.lang.Long getSkuId() {
		return skuId;
	}

	public void setSkuId(java.lang.Long skuId) {
		this.skuId = skuId;
	}

	public java.lang.Integer getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(java.lang.Integer skuNum) {
		this.skuNum = skuNum;
	}

	public java.lang.String getImage() {
		return image;
	}

	public void setImage(java.lang.String image) {
		this.image = image;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public java.lang.String getItemValues() {
		return itemValues;
	}

	public void setItemValues(java.lang.String itemValues) {
		this.itemValues = itemValues;
	}

	public java.lang.Integer getShareType() {
		return shareType;
	}

	public void setShareType(java.lang.Integer shareType) {
		this.shareType = shareType;
	}

	public java.lang.String getWxTempQrUrl() {
		return wxTempQrUrl;
	}

	public void setWxTempQrUrl(java.lang.String wxTempQrUrl) {
		this.wxTempQrUrl = wxTempQrUrl;
	}

	public java.util.Date getWxTempLimitEnd() {
		return wxTempLimitEnd;
	}

	public void setWxTempLimitEnd(java.util.Date wxTempLimitEnd) {
		this.wxTempLimitEnd = wxTempLimitEnd;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
