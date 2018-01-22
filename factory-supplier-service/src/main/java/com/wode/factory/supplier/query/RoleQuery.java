/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.query;

import java.io.Serializable;
import java.util.List;

import com.wode.common.frame.base.BaseQuery;
import com.wode.factory.model.UserFactory;


/**
 * @author user
 *
 */
public class RoleQuery extends BaseQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3807409874614550827L;
	//角色id
	private Integer id;
	//角色名称
	private String name;
	//角色描述
	private String description;
	//企业id
	private Long supplierId;
	//用户id
	private Long userId;
	//用户账号
	private String userName;
	//用户手机号
	private String phone;
	//用户邮箱
	private String email;
	//用户昵称
	private String nickName;
	//用户真是姓名
	private String realName;
	//密码
	private String passWord;
	
	//用户集合
	private List<UserFactory> user;
	
	public List<UserFactory> getUser() {
		return user;
	}
	public void setUser(List<UserFactory> user) {
		this.user = user;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}

