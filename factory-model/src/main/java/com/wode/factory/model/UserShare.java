package com.wode.factory.model;


import java.util.Date;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;

@Table("t_user_share")
public class UserShare extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4561071031071116227L;
	//columns START
    /**
     * id       db_column: id
     * 
     * 
     * 
     */ 
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
     * 用户类型       db_column: user_type
     * 
     * 
     * 
     */ 
    @Column("user_type")
    private java.lang.Integer userType;
    /**
     * 用户昵称       db_column: user_nick
     * 
     * 
     * 
     */ 
    @Column("user_nick")
    private java.lang.String userNick;
    /**
     * 用户头像       db_column: user_avatar
     * 
     * 
     * 
     */ 
    @Column("user_avatar")
    private java.lang.String userAvatar;
    /**
     * 分享类型 0:亲友分享 1:好友分享 2:商品分享       db_column: share_type
     * 
     * 
     * 
     */ 
    @Column("share_type")
    private java.lang.Integer shareType;
    /**
     * 分享标题       db_column: share_title
     * 
     * 
     * 
     */ 
    @Column("share_title")
    private java.lang.String shareTitle;
    /**
     * 分享信息1       db_column: share_msg1
     * 
     * 
     * 
     */ 
    @Column("share_msg1")
    private java.lang.String shareMsg1;
    /**
     * 分享信息2       db_column: share_msg2
     * 
     * 
     * 
     */ 
    @Column("share_msg2")
    private java.lang.String shareMsg2;
    /**
     * 分享信息3       db_column: share_msg3
     * 
     * 
     * 
     */ 
    @Column("share_msg3")
    private java.lang.String shareMsg3;
    /**
     * 分享底部信息1       db_column: share_footer1
     * 
     * 
     * 
     */ 
    @Column("share_footer1")
    private java.lang.String shareFooter1;
    /**
     * 分享底部信息2       db_column: share_footer2
     * 
     * 
     * 
     */ 
    @Column("share_footer2")
    private java.lang.String shareFooter2;
    /**
     * 分享底部信息3       db_column: share_footer3
     * 
     * 
     * 
     */ 
    @Column("share_footer3")
    private java.lang.String shareFooter3;
    /**
     * 分享内容数量       db_column: share_item_cnt
     * 
     * 
     * 
     */ 
    @Column("share_item_cnt")
    private java.lang.Integer shareItemCnt;
    /**
     * 创建时间       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private Date createTime;
    /**
     * 分享url       db_column: share_url
     * 
     * 
     * 
     */ 
    @Column("share_url")
    private java.lang.String shareUrl;
    /**
     * 下一步操作       db_column: next_action
     * 
     * 
     * 
     */ 
    @Column("next_action")
    private java.lang.String nextAction;
    /**
     * 缩略图url       db_column: img_url
     * 
     * 
     * 
     */ 
    @Column("img_url")
    private java.lang.String imgUrl;
    
    @Column("group_id")
    private java.lang.Long groupId;
    /**
     * 微信带参数二维码 关注用临时       db_column: wx_temp_qr_url
     * 
     * 
     * 
     */ 
    @Column("wx_temp_qr_url")
    private java.lang.String wxTempQrUrl;
    /**
     * 微信临时二维码有效期限       db_column: `wx_temp_limit_end`
     * 
     * 
     * 
     */ 
    @Column("wx_temp_limit_end")
    private Date wxTempLimitEnd;
    /**
     * 扫码后打开连接       db_column: target_action_url
     * 
     * 
     * 
     */ 
    @Column("target_action_url")
    private java.lang.String targetActionUrl;
    //columns END
    
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("UserId",getUserId())
            .append("UserType",getUserType())
            .append("UserNick",getUserNick())
            .append("UserAvatar",getUserAvatar())
            .append("ShareType",getShareType())
            .append("ShareTitle",getShareTitle())
            .append("ShareMsg1",getShareMsg1())
            .append("ShareMsg2",getShareMsg2())
            .append("ShareMsg3",getShareMsg3())
            .append("ShareFooter1",getShareFooter1())
            .append("ShareFooter2",getShareFooter2())
            .append("ShareFooter3",getShareFooter3())
            .append("ShareItemCnt",getShareItemCnt())
            .append("CreateTime",getCreateTime())
            .append("ShareUrl",getShareUrl())
            .append("NextAction",getNextAction())
            .append("GroupId",getGroupId())
            .toString();
    }

   

	public java.lang.Long getGroupId() {
		return groupId;
	}



	public void setGroupId(java.lang.Long groupId) {
		this.groupId = groupId;
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

	public java.lang.Integer getUserType() {
		return userType;
	}

	public void setUserType(java.lang.Integer userType) {
		this.userType = userType;
	}

	public java.lang.String getUserNick() {
		return userNick;
	}

	public void setUserNick(java.lang.String userNick) {
		this.userNick = userNick;
	}

	public java.lang.String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(java.lang.String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public java.lang.Integer getShareType() {
		return shareType;
	}

	public void setShareType(java.lang.Integer shareType) {
		this.shareType = shareType;
	}

	public java.lang.String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(java.lang.String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public java.lang.String getShareMsg1() {
		return shareMsg1;
	}

	public void setShareMsg1(java.lang.String shareMsg1) {
		this.shareMsg1 = shareMsg1;
	}

	public java.lang.String getShareMsg2() {
		return shareMsg2;
	}

	public void setShareMsg2(java.lang.String shareMsg2) {
		this.shareMsg2 = shareMsg2;
	}

	public java.lang.String getShareMsg3() {
		return shareMsg3;
	}

	public void setShareMsg3(java.lang.String shareMsg3) {
		this.shareMsg3 = shareMsg3;
	}

	public java.lang.String getShareFooter1() {
		return shareFooter1;
	}

	public void setShareFooter1(java.lang.String shareFooter1) {
		this.shareFooter1 = shareFooter1;
	}

	public java.lang.String getShareFooter2() {
		return shareFooter2;
	}

	public void setShareFooter2(java.lang.String shareFooter2) {
		this.shareFooter2 = shareFooter2;
	}

	public java.lang.String getShareFooter3() {
		return shareFooter3;
	}

	public void setShareFooter3(java.lang.String shareFooter3) {
		this.shareFooter3 = shareFooter3;
	}

	public java.lang.Integer getShareItemCnt() {
		return shareItemCnt;
	}

	public void setShareItemCnt(java.lang.Integer shareItemCnt) {
		this.shareItemCnt = shareItemCnt;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(java.lang.String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public java.lang.String getNextAction() {
		return nextAction;
	}

	public void setNextAction(java.lang.String nextAction) {
		this.nextAction = nextAction;
	}
	
	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public java.lang.String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(java.lang.String imgUrl) {
		this.imgUrl = imgUrl;
	}



	public java.lang.String getWxTempQrUrl() {
		return wxTempQrUrl;
	}



	public void setWxTempQrUrl(java.lang.String wxTempQrUrl) {
		this.wxTempQrUrl = wxTempQrUrl;
	}



	public Date getWxTempLimitEnd() {
		return wxTempLimitEnd;
	}



	public void setWxTempLimitEnd(Date wxTempLimitEnd) {
		this.wxTempLimitEnd = wxTempLimitEnd;
	}



	public java.lang.String getTargetActionUrl() {
		return targetActionUrl;
	}



	public void setTargetActionUrl(java.lang.String targetActionUrl) {
		this.targetActionUrl = targetActionUrl;
	}

}

