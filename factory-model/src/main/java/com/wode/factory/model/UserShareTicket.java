package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_share_ticket")
public class UserShareTicket extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6835302088866995113L;
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
     * 企业id       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    /**
     * 企业券id       db_column: ticket_id
     * 
     * 
     * 
     */ 
    @Column("ticket_id")
    private java.lang.Long ticketId;
    /**
     * 特卖区分 3：换领       db_column: sale_kbn
     * 
     * 
     * 
     */ 
    @Column("sale_kbn")
    private java.lang.Integer saleKbn;
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
    /**
     * 生成用户       db_column: create_user_name
     * 
     * 
     * 
     */ 
    @Column("create_user_name")
    private java.lang.String createUserName;
    /**
     * 生成时间       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private java.util.Date createTime;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("ShareId",getShareId())
            .append("SupplierId",getSupplierId())
            .append("TicketId",getTicketId())
            .append("SaleKbn",getSaleKbn())
            .append("ShareType",getShareType())
            .append("WxTempQrUrl",getWxTempQrUrl())
            .append("WxTempLimitEnd",getWxTempLimitEnd())
            .append("CreateUserName",getCreateUserName())
            .append("CreateTime",getCreateTime())
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

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(java.lang.Long ticketId) {
		this.ticketId = ticketId;
	}

	public java.lang.Integer getSaleKbn() {
		return saleKbn;
	}

	public void setSaleKbn(java.lang.Integer saleKbn) {
		this.saleKbn = saleKbn;
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

	public java.lang.String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(java.lang.String createUserName) {
		this.createUserName = createUserName;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

