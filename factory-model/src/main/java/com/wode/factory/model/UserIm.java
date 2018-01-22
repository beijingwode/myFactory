package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_im")
public class UserIm extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
     * im id       db_column: openim_id
     * 
     * 
     * 
     */ 
    @Column("openim_id")
    private java.lang.String openimId;
    /**
     * im 密码       db_column: openim_pw
     * 
     * 
     * 
     */ 
    @Column("openim_pw")
    private java.lang.String openimPw;
    /**
     * 创建时间       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private java.lang.String createTime;
    /**
     * app 类型       db_column: app_type
     * 
     * 
     * 
     */ 
    @Column("app_type")
    private java.lang.String appType;
    /**
     * app key       db_column: app_key
     * 
     * 
     * 
     */ 
    @Column("app_key")
    private java.lang.String appKey;

    //columns END

    
    
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("UserId",getUserId())
            .append("OpenimId",getOpenimId())
            .append("OpenimPw",getOpenimPw())
            .append("CreateTime",getCreateTime())
            .append("AppType",getAppType())
            .append("AppKey",getAppKey())
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

	public java.lang.String getOpenimId() {
		return openimId;
	}

	public void setOpenimId(java.lang.String openimId) {
		this.openimId = openimId;
	}

	public java.lang.String getOpenimPw() {
		return openimPw;
	}

	public void setOpenimPw(java.lang.String openimPw) {
		this.openimPw = openimPw;
	}

	public java.lang.String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getAppType() {
		return appType;
	}

	public void setAppType(java.lang.String appType) {
		this.appType = appType;
	}

	public java.lang.String getAppKey() {
		return appKey;
	}

	public void setAppKey(java.lang.String appKey) {
		this.appKey = appKey;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

