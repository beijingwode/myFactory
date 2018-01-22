package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_im_group_member")
public class UserImGroupMember extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7509532309155961773L;
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
     * 系统群组id       db_column: group_id
     * 
     * 
     * 
     */ 
    @Column("group_id")
    private java.lang.Long groupId;
    /**
     * im群组id       db_column: im_group_id
     * 
     * 
     * 
     */ 
    @Column("im_group_id")
    private java.lang.String imGroupId;
    /**
     * 用户id       db_column: user_id
     * 
     * 
     * 
     */ 
    @Column("user_id")
    private java.lang.Long userId;
    /**
     * im用户id       db_column: openim_id
     * 
     * 
     * 
     */ 
    @Column("openim_id")
    private java.lang.String openimId;
    /**
     * 群昵称       db_column: nickname
     * 
     * 
     * 
     */ 
    @Column("nickname")
    private java.lang.String nickname;
    /**
     * 属性1       db_column: op1
     * 
     * 
     * 
     */ 
    @Column("userCard")
    private java.lang.String userCard;
    /**
     * 属性2       db_column: op2
     * 
     * 
     * 
     */ 
    @Column("op2")
    private java.lang.String op2;
    /**
     * 属性3       db_column: op3
     * 
     * 
     * 
     */ 
    @Column("op3")
    private java.lang.String op3;

    //columns END
	//户用头像地址
    private java.lang.String avatar;

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("GroupId",getGroupId())
            .append("ImGroupId",getImGroupId())
            .append("UserId",getUserId())
            .append("OpenimId",getOpenimId())
            .append("Nickname",getNickname())
            .append("userCard",getUserCard())
            .append("Op2",getOp2())
            .append("Op3",getOp3())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getGroupId() {
		return groupId;
	}

	public void setGroupId(java.lang.Long groupId) {
		this.groupId = groupId;
	}

	public java.lang.String getImGroupId() {
		return imGroupId;
	}

	public void setImGroupId(java.lang.String imGroupId) {
		this.imGroupId = imGroupId;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.lang.String getOpenimId() {
		return openimId;
	}

	public void setOpenimId(java.lang.String openimId) {
		this.openimId = openimId;
	}

	public java.lang.String getNickname() {
		return nickname;
	}

	public void setNickname(java.lang.String nickname) {
		this.nickname = nickname;
	}

	public java.lang.String getUserCard() {
		return userCard;
	}

	public void setUserCard(java.lang.String userCard) {
		this.userCard = userCard;
	}

	public java.lang.String getOp2() {
		return op2;
	}

	public void setOp2(java.lang.String op2) {
		this.op2 = op2;
	}

	public java.lang.String getOp3() {
		return op3;
	}

	public void setOp3(java.lang.String op3) {
		this.op3 = op3;
	}

	public java.lang.String getAvatar() {
		return avatar;
	}

	public void setAvatar(java.lang.String avatar) {
		this.avatar = avatar;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

