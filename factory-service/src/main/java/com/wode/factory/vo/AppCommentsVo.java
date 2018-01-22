package com.wode.factory.vo;

import java.io.Serializable;

import com.wode.factory.model.Comments;

public class AppCommentsVo extends Comments implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userNickName;

	public String getUserNickName() {
		return userNickName;
	}

	
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	
	
}
