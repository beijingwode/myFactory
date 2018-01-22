package com.wode.factory.user.model;

import java.io.Serializable;
import java.util.Date;

public class CommentsVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4671365152842179089L;
	private Long commentsId;
	private String showUserName;
	private String attributeJson;
	private Date creatTime;
	private String content;
	private int userLevel;
	
	
	
	public Long getCommentsId() {
		return commentsId;
	}
	public void setCommentsId(Long commentsId) {
		this.commentsId = commentsId;
	}
	public String getShowUserName() {
		return showUserName;
	}
	public void setShowUserName(String showUserName) {
		this.showUserName = showUserName;
	}
	public String getAttributeJson() {
		return attributeJson;
	}
	public void setAttributeJson(String attributeJson) {
		this.attributeJson = attributeJson;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}
	
}
