package com.wode.factory.model;
/**
 *	快递公司信息
 */
public class ExpressCompany {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String pinYin;//拼音
	private String id;//拼音
	private String janeSpell;//简拼
	private String name;//名称
	private String abbreviation;//简称
	private String phone;//电话
	private Integer status;//状态
	
	public String getJaneSpell() {
		return janeSpell;
	}
	public void setJaneSpell(String janeSpell) {
		this.janeSpell = janeSpell;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPinYin() {
		return pinYin;
	}
	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
