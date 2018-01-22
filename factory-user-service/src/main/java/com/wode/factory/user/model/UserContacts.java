package com.wode.factory.user.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_contacts")
public class UserContacts extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6927939166335640245L;
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
     * 用户id       db_column: user_id
     * 
     * 
     * 
     */ 
    @Column("user_id")
    private java.lang.Long userId;
    /**
     * 联系人id       db_column: contacts_id
     * 
     * 
     * 
     */ 
    @Column("contacts_id")
    private java.lang.Long contactsId;
    /**
     * 联系人备注       db_column: contacts_memo
     * 
     * 
     * 
     */ 
    @Column("contacts_memo")
    private java.lang.String contactsMemo;
    /**
     * 联系人描述       db_column: contacts_desc
     * 
     * 
     * 
     */ 
    @Column("contacts_desc")
    private java.lang.String contactsDesc;
    /**
     * 联系人标签       db_column: contacts_tags
     * 
     * 
     * 
     */ 
    @Column("contacts_tags")
    private java.lang.String contactsTags;
    /**
     * 联系人图片       db_column: contacts_img
     * 
     * 
     * 
     */ 
    @Column("contacts_img")
    private java.lang.String contactsImg;
    /**
     * 扩展属性1       db_column: opt_ex1
     * 
     * 
     * 
     */ 
    @Column("opt_ex1")
    private java.lang.String optEx1;
    /**
     * 扩展属性2       db_column: opt_ex2
     * 
     * 
     * 
     */ 
    @Column("opt_ex2")
    private java.lang.String optEx2;
    /**
     * 扩展属性3       db_column: opt_ex3
     * 
     * 
     * 
     */ 
    @Column("opt_ex3")
    private java.lang.String optEx3;
    /**
     * 创建时间       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private java.util.Date createTime;
    /**
     * 申请来源 手机号，app同事 团购 微信 等       db_column: appr_from
     * 
     * 
     * 
     */ 
    @Column("appr_from")
    private java.lang.String apprFrom;
    /**
     * 联系人imId       db_column: contacts_im_id
     * 
     * 
     * 
     */ 
    @Column("contacts_im_id")
    private java.lang.String contactsImId;  
    

    /**
     * 好友类型 0:非好友，1:好友       db_column: firend_type
     * 
     * 
     * 
     */ 
    @Column("firend_type")
    private java.lang.String firendType;  
    
    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("UserId",getUserId())
            .append("ContactsId",getContactsId())
            .append("ContactsMemo",getContactsMemo())
            .append("ContactsDesc",getContactsDesc())
            .append("ContactsTags",getContactsTags())
            .append("ContactsImg",getContactsImg())
            .append("OptEx1",getOptEx1())
            .append("OptEx2",getOptEx2())
            .append("OptEx3",getOptEx3())
            .append("CreateTime",getCreateTime())
            .append("ApprFrom",getApprFrom())
            .toString();
    }

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

	public java.lang.Long getContactsId() {
		return contactsId;
	}

	public void setContactsId(java.lang.Long contactsId) {
		this.contactsId = contactsId;
	}

	public java.lang.String getContactsMemo() {
		return contactsMemo;
	}

	public void setContactsMemo(java.lang.String contactsMemo) {
		this.contactsMemo = contactsMemo;
	}

	public java.lang.String getContactsDesc() {
		return contactsDesc;
	}

	public void setContactsDesc(java.lang.String contactsDesc) {
		this.contactsDesc = contactsDesc;
	}

	public java.lang.String getContactsTags() {
		return contactsTags;
	}

	public void setContactsTags(java.lang.String contactsTags) {
		this.contactsTags = contactsTags;
	}

	public java.lang.String getContactsImg() {
		return contactsImg;
	}

	public void setContactsImg(java.lang.String contactsImg) {
		this.contactsImg = contactsImg;
	}

	public java.lang.String getOptEx1() {
		return optEx1;
	}

	public void setOptEx1(java.lang.String optEx1) {
		this.optEx1 = optEx1;
	}

	public java.lang.String getOptEx2() {
		return optEx2;
	}

	public void setOptEx2(java.lang.String optEx2) {
		this.optEx2 = optEx2;
	}

	public java.lang.String getOptEx3() {
		return optEx3;
	}

	public void setOptEx3(java.lang.String optEx3) {
		this.optEx3 = optEx3;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getApprFrom() {
		return apprFrom;
	}

	public void setApprFrom(java.lang.String apprFrom) {
		this.apprFrom = apprFrom;
	}

	public java.lang.String getContactsImId() {
		return contactsImId;
	}

	public void setContactsImId(java.lang.String contactsImId) {
		this.contactsImId = contactsImId;
	}

	public java.lang.String getFirendType() {
		return firendType;
	}

	public void setFirendType(java.lang.String firendType) {
		this.firendType = firendType;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

