/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.stereotype.PrimaryKey;

import cn.org.rapid_framework.util.DateConvertUtils;

@Table("t_comments")
public class Comments implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    //date formats
    public static final String FORMAT_CREAT_TIME = "yyyy-MM-dd HH:mm:ss";

    //columns START
    /**
     * id       db_column: id
     */
    @PrimaryKey
    @Column("_id")
    private java.lang.String id;
    /**
     * orderid       db_column: orderid
     *
     * @Length(max=20)
     */
    @Column("subOrderid")
    private java.lang.String subOrderid;
    /**
     * user_id       db_column: user_id
     */
    @Column("user_id")
    private java.lang.Long userId;
    /**
     * supplyid       db_column: supplyid
     */
    @Column("supplyid")
    private java.lang.Long supplyid;
    /**
     * 商品评级       db_column: star1
     */
    @Column("star1")
    private java.lang.Integer star1;
    /**
     * 服务评级       db_column: star2
     */
    @Column("star2")
    private java.lang.Integer star2;
    /**
     * 物流评级       db_column: star3
     */
    @Column("star3")
    private java.lang.Integer star3;
    /**
     * 评论内容       db_column: text
     *
     * @Length(max=500)
     */
    @Column("text")
    private java.lang.String text;
    /**
     * 图片       db_column: pic
     *
     * @Length(max=500)
     */
    @Column("pic")
    private java.lang.String pic;
    /**
     * creat_time       db_column: creat_time
     *
     * @NotNull
     */
    @Column("creat_time")
    private java.util.Date creatTime;
    /**
     * replayId       db_column: replayId
     */
    @Column("replayId")
    private java.lang.Long replayId;
    /**
     * productId       db_column: product_id
     *
     * @NotNull
     */
    @Column("product_id")
    private java.lang.Long productId;
    /**
     * status       db_column: status
     */
    @Column("status")
    private java.lang.Integer status;
    /**
     * attributeJson       db_column: attribute_json
     *
     * @Length(max=255)
     */
    @Column("attribute_json")
    private java.lang.String attributeJson;
    /**
     * 评论度：1：差评；3中评；5好评       db_column: comment_degree
     */
    @Column("comment_degree")
    private java.lang.Integer commentDegree;
    /**
     * 订单项ID
     */
    @Column("subOrderItemId")
    private java.lang.Long subOrderItemId;
    /**
     * 订单项ID
     */
    @Column("anonymous")
    private java.lang.String anonymous;

    private Suborderitem suborderitem;
    private Suborder suborder;
    private Product product;
    private UserFactory user;
    private Supplier supplier;
    //图片集合
    private List images;
    //columns END

    public List getImages() {
		return images;
	}

	public void setImages(List images) {
		this.images = images;
	}

	public Comments() {
    }

    public void setId(java.lang.String value) {
        this.id = value;
    }

    public java.lang.String getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(java.lang.String anonymous) {
		this.anonymous = anonymous;
	}

	public java.lang.String getId() {
        return this.id;
    }

    public String getSubOrderid() {
        return subOrderid;
    }

    public void setSubOrderid(String subOrderid) {
        this.subOrderid = subOrderid;
    }

    public void setUserId(java.lang.Long value) {
        this.userId = value;
    }

    public java.lang.Long getUserId() {
        return this.userId;
    }

    public void setSupplyid(java.lang.Long value) {
        this.supplyid = value;
    }

    public java.lang.Long getSupplyid() {
        return this.supplyid;
    }

    public void setStar1(java.lang.Integer value) {
        this.star1 = value;
    }

    public java.lang.Integer getStar1() {
        return this.star1;
    }

    public void setStar2(java.lang.Integer value) {
        this.star2 = value;
    }

    public java.lang.Integer getStar2() {
        return this.star2;
    }

    public void setStar3(java.lang.Integer value) {
        this.star3 = value;
    }

    public java.lang.Integer getStar3() {
        return this.star3;
    }

    public void setText(java.lang.String value) {
        this.text = value;
    }

    public java.lang.String getText() {
        return this.text;
    }

    public void setPic(java.lang.String value) {
        this.pic = value;
    }

    public java.lang.String getPic() {
        return this.pic;
    }

    public String getCreatTimeString() {
        return DateConvertUtils.format(getCreatTime(), FORMAT_CREAT_TIME);
    }

    public void setCreatTimeString(String value) {
        setCreatTime(DateConvertUtils.parse(value, FORMAT_CREAT_TIME, java.util.Date.class));
    }

    public void setCreatTime(java.util.Date value) {
        this.creatTime = value;
    }

    public java.util.Date getCreatTime() {
        return this.creatTime;
    }

    public void setReplayId(java.lang.Long value) {
        this.replayId = value;
    }

    public java.lang.Long getReplayId() {
        return this.replayId;
    }

    public void setProductId(java.lang.Long value) {
        this.productId = value;
    }

    public java.lang.Long getProductId() {
        return this.productId;
    }

    public void setStatus(java.lang.Integer value) {
        this.status = value;
    }

    public java.lang.Integer getStatus() {
        return this.status;
    }

    public void setAttributeJson(java.lang.String value) {
        this.attributeJson = value;
    }

    public java.lang.String getAttributeJson() {
        return this.attributeJson;
    }

    public void setCommentDegree(java.lang.Integer value) {
        if (value == 3) this.commentDegree = 3;
        if (value >= 4) this.commentDegree = 5;
        if (value <= 2) this.commentDegree = 1;
    }

    public java.lang.Integer getCommentDegree() {
        return this.commentDegree;
    }

    public java.lang.Long getSubOrderItemId() {
        return subOrderItemId;
    }

    public void setSubOrderItemId(java.lang.Long subOrderItemId) {
        this.subOrderItemId = subOrderItemId;
    }

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Id", getId())
                .append("subOrderid", getSubOrderid())
                .append("UserId", getUserId())
                .append("Supplyid", getSupplyid())
                .append("Star1", getStar1())
                .append("Star2", getStar2())
                .append("Star3", getStar3())
                .append("Text", getText())
                .append("Pic", getPic())
                .append("CreatTime", getCreatTime())
                .append("ReplayId", getReplayId())
                .append("ProductId", getProductId())
                .append("Status", getStatus())
                .append("CommentDegree", getCommentDegree())
                .append("Images",getImages())
                .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Comments == false) return false;
        if (this == obj) return true;
        Comments other = (Comments) obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }

	public Suborderitem getSuborderitem() {
		return suborderitem;
	}

	public void setSuborderitem(Suborderitem suborderitem) {
		this.suborderitem = suborderitem;
	}

	public Suborder getSuborder() {
		return suborder;
	}

	public void setSuborder(Suborder suborder) {
		this.suborder = suborder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public UserFactory getUser() {
		return user;
	}

	public void setUser(UserFactory user) {
		this.user = user;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	
}

