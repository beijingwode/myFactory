package com.wode.factory.user.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_contacts_appr")
public class UserContactsAppr extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3169924288989496798L;
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
     * 用户关键字，手机或邮箱       db_column: user_key
     * 
     * 
     * 
     */ 
    @Column("user_key")
    private java.lang.String userKey;
    /**
     * 用户昵称       db_column: user_nickname
     * 
     * 
     * 
     */ 
    @Column("user_nickname")
    private java.lang.String userNickname;
    /**
     * 申请验证       db_column: appr_text
     * 
     * 
     * 
     */ 
    @Column("appr_text")
    private java.lang.String apprText;
    /**
     * 为朋友设置备注       db_column: friend_memo
     * 
     * 
     * 
     */ 
    @Column("friend_memo")
    private java.lang.String friendMemo;
    /**
     * 朋友id       db_column: firend_id
     * 
     * 
     * 
     */ 
    @Column("firend_id")
    private java.lang.Long firendId;
    /**
     * 朋友关键字，手机或邮箱       db_column: friend_key
     * 
     * 
     * 
     */ 
    @Column("friend_key")
    private java.lang.String friendKey;
    /**
     * 朋友昵称       db_column: friend_nickname
     * 
     * 
     * 
     */ 
    @Column("friend_nickname")
    private java.lang.String friendNickname;
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
     * 申请状态 1:等待审核 2:审核通过       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.String status;
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

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("UserId",getUserId())
            .append("UserKey",getUserKey())
            .append("UserNickname",getUserNickname())
            .append("ApprText",getApprText())
            .append("FriendMemo",getFriendMemo())
            .append("FirendId",getFirendId())
            .append("FriendKey",getFriendKey())
            .append("FriendNickname",getFriendNickname())
            .append("OptEx1",getOptEx1())
            .append("OptEx2",getOptEx2())
            .append("OptEx3",getOptEx3())
            .append("Status",getStatus())
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

	public java.lang.String getUserKey() {
		return userKey;
	}

	public void setUserKey(java.lang.String userKey) {
		this.userKey = userKey;
	}

	public java.lang.String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(java.lang.String userNickname) {
		this.userNickname = userNickname;
	}

	public java.lang.String getApprText() {
		return apprText;
	}

	public void setApprText(java.lang.String apprText) {
		this.apprText = apprText;
	}

	public java.lang.String getFriendMemo() {
		return friendMemo;
	}

	public void setFriendMemo(java.lang.String friendMemo) {
		this.friendMemo = friendMemo;
	}

	public java.lang.Long getFirendId() {
		return firendId;
	}

	public void setFirendId(java.lang.Long firendId) {
		this.firendId = firendId;
	}

	public java.lang.String getFriendKey() {
		return friendKey;
	}

	public void setFriendKey(java.lang.String friendKey) {
		this.friendKey = friendKey;
	}

	public java.lang.String getFriendNickname() {
		return friendNickname;
	}

	public void setFriendNickname(java.lang.String friendNickname) {
		this.friendNickname = friendNickname;
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

	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
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

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
