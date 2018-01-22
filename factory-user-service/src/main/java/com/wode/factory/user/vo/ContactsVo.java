package com.wode.factory.user.vo;

import com.wode.factory.model.UserIm;

public class ContactsVo extends UserIm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//用户ID
    private java.lang.Long userId;
	//昵称
	private String nickname;
	//用户类型  0:群/1:普通/2:买家/3:员工
	private Integer userType;
	//关系类型  0:临时/1:亲友/2:好友/3:同事
	private Integer relationType;
	//ImUser
    private java.lang.String imUser;
	//app 类型 user/shop
    private java.lang.String appType;
	//app Key
    private java.lang.String appKey;
	//户用头像地址
    private java.lang.String avatar;
	//户用头像地址
    private java.util.Date birthDay;
    //用户名
    private String username;
    //性别
    private String gender;
    //备注
    private String userNote;
    //职务
    private String duty;
    //手机
    private String phone;
    //工号（员工姓名）
    private String workName;
    //隐藏信息(1.隐藏手机2.隐藏姓名）
    private int hideInfo;
    
	public int getHideInfo() {
		return hideInfo;
	}
	public void setHideInfo(int hideInfo) {
		this.hideInfo = hideInfo;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public String getUserNote() {
		return userNote;
	}
	public void setUserNote(String userNote) {
		this.userNote = userNote;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public java.lang.Long getUserId() {
		return userId;
	}
	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getRelationType() {
		return relationType;
	}
	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}
	public java.lang.String getImUser() {
		return imUser;
	}
	public void setImUser(java.lang.String imUser) {
		this.imUser = imUser;
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
	public java.lang.String getAvatar() {
		return avatar;
	}
	public void setAvatar(java.lang.String avatar) {
		this.avatar = avatar;
	}
	public java.util.Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(java.util.Date birthDay) {
		this.birthDay = birthDay;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
