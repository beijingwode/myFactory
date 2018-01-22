package com.wode.factory.user.vo;

import java.util.List;

import com.wode.factory.model.UserShare;

public class UserShareVo  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -659227925056258377L;
	private UserShare share;
	private List<UserShareProductItemVo> items;
	private Integer number;
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public UserShare getShare() {
		return share;
	}

	public void setShare(UserShare share) {
		this.share = share;
	}

	public List<UserShareProductItemVo> getItems() {
		return items;
	}

	public void setItems(List<UserShareProductItemVo> items) {
		this.items = items;
	}
	
}
