package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_share_item")
public class UserShareItem extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7131125986136858668L;
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
     * 分享id       db_column: share_id
     * 
     * 
     * 
     */ 
    @Column("share_id")
    private java.lang.Long shareId;
    /**
     * userId       db_column: user_id
     * 
     * 
     * 
     */ 
    @Column("user_id")
    private java.lang.Long userId;
    /**
     * 排序       db_column: orders
     * 
     * 
     * 
     */ 
    @Column("orders")
    private java.lang.Integer orders;
    /**
     * 关键字1       db_column: key1
     * 
     * 
     * 
     */ 
    @Column("key1")
    private java.lang.Long key1;
    /**
     * 关键字2       db_column: key2
     * 
     * 
     * 
     */ 
    @Column("key2")
    private java.lang.Long key2;
    /**
     * 关键字3       db_column: key3
     * 
     * 
     * 
     */ 
    @Column("key3")
    private java.lang.String key3;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("ShareId",getShareId())
            .append("UserId",getUserId())
            .append("Orders",getOrders())
            .append("Key1",getKey1())
            .append("Key2",getKey2())
            .append("Key3",getKey3())
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

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.lang.Integer getOrders() {
		return orders;
	}

	public void setOrders(java.lang.Integer orders) {
		this.orders = orders;
	}

	public java.lang.Long getKey1() {
		return key1;
	}

	public void setKey1(java.lang.Long key1) {
		this.key1 = key1;
	}

	public java.lang.Long getKey2() {
		return key2;
	}

	public void setKey2(java.lang.Long key2) {
		this.key2 = key2;
	}

	public java.lang.String getKey3() {
		return key3;
	}

	public void setKey3(java.lang.String key3) {
		this.key3 = key3;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

