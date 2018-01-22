package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_product_e_card")
public class ProductECard extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2388705536740514549L;
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
     * 商品id       db_column: product_id
     * 
     * 
     * 
     */ 
    @Column("product_id")
    private java.lang.Long productId;
    /**
     * 商品名称       db_column: product_name
     * 
     * 
     * 
     */ 
    @Column("product_name")
    private java.lang.String productName;
    /**
     * 返货类型 1:自动发货 2:手动发货       db_column: send_type
     * 
     * 
     * 
     */ 
    @Column("send_type")
    private java.lang.String sendType;
    /**
     * 卡券 链接       db_column: card_page
     * 
     * 
     * 
     */ 
    @Column("card_page")
    private java.lang.String cardPage;
    /**
     * 卡券密码 多个已回车分隔       db_column: card_pws
     * 
     * 
     * 
     */ 
    @Column("card_pws")
    private java.lang.String cardPws;
    /**
     * 创建时间       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private java.util.Date createTime;
    /**
     * 创建者       db_column: create_by
     * 
     * 
     * 
     */ 
    @Column("create_by")
    private java.lang.String createBy;

    //columns END

    
    
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("ProductId",getProductId())
            .append("ProductName",getProductName())
            .append("SendType",getSendType())
            .append("CardPage",getCardPage())
            .append("CardPws",getCardPws())
            .append("CreateTime",getCreateTime())
            .append("CreateBy",getCreateBy())
            .toString();
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

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public java.lang.String getSendType() {
		return sendType;
	}

	public void setSendType(java.lang.String sendType) {
		this.sendType = sendType;
	}

	public java.lang.String getCardPage() {
		return cardPage;
	}

	public void setCardPage(java.lang.String cardPage) {
		this.cardPage = cardPage;
	}

	public java.lang.String getCardPws() {
		return cardPws;
	}

	public void setCardPws(java.lang.String cardPws) {
		this.cardPws = cardPws;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

