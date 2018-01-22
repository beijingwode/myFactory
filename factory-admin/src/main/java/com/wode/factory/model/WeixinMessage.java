package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_weixin_message")
public class WeixinMessage extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 502166975162985860L;
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
     * 微信事件类型 subscribe:关注       db_column: event_type
     * 
     * 
     * 
     */ 
    @Column("event_type")
    private java.lang.String eventType;
    /**
     * 消息类型 1:默认 2:临时       db_column: msg_type
     * 
     * 
     * 
     */ 
    @Column("msg_type")
    private java.lang.String msgType;
    /**
     * 标题       db_column: title
     * 
     * 
     * 
     */ 
    @Column("title")
    private java.lang.String title;
    /**
     * 图片链接       db_column: pic_url
     * 
     * 
     * 
     */ 
    @Column("pic_url")
    private java.lang.String picUrl;
    /**
     * 描述       db_column: description
     * 
     * 
     * 
     */ 
    @Column("description")
    private java.lang.String description;
    /**
     * 点击后链接       db_column: link_url
     * 
     * 
     * 
     */ 
    @Column("link_url")
    private java.lang.String linkUrl;
    /**
     * 停用标志 0:正常 1:停用       db_column: stop_flg
     * 
     * 
     * 
     */ 
    @Column("stop_flg")
    private java.lang.String stopFlg;
    /**
     * 开始日期       db_column: limit_start
     * 
     * 
     * 
     */ 
    @Column("limit_start")
    private java.util.Date limitStart;
    /**
     * 结束日期       db_column: limit_end
     * 
     * 
     * 
     */ 
    @Column("limit_end")
    private java.util.Date limitEnd;
    /**
     * 更新时间       db_column: update_time
     * 
     * 
     * 
     */ 
    @Column("update_time")
    private java.util.Date updateTime;

    //columns END
    private int status;		//VO属性 -1:手动停用 0:未使用 1:使用中
    
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("EventType",getEventType())
            .append("MsgType",getMsgType())
            .append("Title",getTitle())
            .append("PicUrl",getPicUrl())
            .append("Description",getDescription())
            .append("LinkUrl",getLinkUrl())
            .append("StopFlg",getStopFlg())
            .append("LimitStart",getLimitStart())
            .append("LimitEnd",getLimitEnd())
            .append("UpdateTime",getUpdateTime())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getEventType() {
		return eventType;
	}

	public void setEventType(java.lang.String eventType) {
		this.eventType = eventType;
	}

	public java.lang.String getMsgType() {
		return msgType;
	}

	public void setMsgType(java.lang.String msgType) {
		this.msgType = msgType;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(java.lang.String picUrl) {
		this.picUrl = picUrl;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(java.lang.String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public java.lang.String getStopFlg() {
		return stopFlg;
	}

	public void setStopFlg(java.lang.String stopFlg) {
		this.stopFlg = stopFlg;
	}

	public java.util.Date getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(java.util.Date limitStart) {
		this.limitStart = limitStart;
	}

	public java.util.Date getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(java.util.Date limitEnd) {
		this.limitEnd = limitEnd;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

