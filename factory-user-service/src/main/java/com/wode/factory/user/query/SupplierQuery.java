/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class SupplierQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** user_id */
	private java.lang.Long userId;
	/** 公司传真 */
	private java.lang.String comPortraiture;
	/** 公司固定电话 */
	private java.lang.String comTel;
	/** com_name */
	private java.lang.String comName;
	/** 公司人数 */
	private java.lang.String comPersonnum;
	/** 公司营业执照注册号 */
	private java.lang.String comRegisternum;
	/** 状态：1审核通过，0审核没通过,2未审核 */
	private java.lang.Integer status;
	/** 图片空间 */
	private java.lang.Long space;
	/** 公司邮编 */
	private java.lang.Long comPc;
	/** 省 */
	private java.lang.String comState;
	/** 市 */
	private java.lang.String comCity;
	/** 区 */
	private java.lang.String comAddress;
	/** 推荐人 */
	private java.lang.String referee;
	/** 行业 */
	private java.lang.String industry;
	/** 性质 */
	private java.lang.String property;
	/** 营业执照省 */
	private java.lang.String busState;
	/** 营业执照市 */
	private java.lang.String busCity;
	/** busAddress */
	private java.lang.String busAddress;
	/** 注册资本 */
	private java.lang.String registeredCapital;
	/** 营业范围 */
	private java.lang.String busScope;
	/** 营业执照开始时间 */
	private java.util.Date busBegintime;
	/** 营业执照结束时间 */
	private java.util.Date busEndtime;
	/** 组织机构代码证编号    */
	private java.lang.String orgNum;
	/** 组织机构证开始时间 */
	private java.util.Date orgBegintime;
	/** 组织机构证结束时间 */
	private java.util.Date orgEndtime;
	/** 税务登记证编号 */
	private java.lang.String taxNum;
	/** 是否普通纳税人  0是  1否 */
	private java.lang.Integer istaxpayer;
	/** 法人归属地  0中国大陆  1港澳  2台湾  3外籍 */
	private java.lang.Integer corCome;
	/** 法人代表姓名 */
	private java.lang.String corName;
	/** 身份证 */
	private java.lang.Long corNum;
	/** 开户人 */
	private java.lang.String bankPeople;
	/** bankId */
	private java.lang.Long bankId;
	/** 开户行省 */
	private java.lang.String bankState;
	/** 开户行市 */
	private java.lang.String bankCity;
	/** 开户行支行名称 */
	private java.lang.String bankName;
	/** 银行账号 */
	private java.lang.String bankNum;
	/** 售后公司固定电话 */
	private java.lang.String cusTel;
	/** 售后公司手机 */
	private java.lang.String cusPhone;
	/** 售后公司邮箱 */
	private java.lang.String cusEmail;
	/** 售后联系人 */
	private java.lang.String cusContact;
	/** 客服固定电话 */
	private java.lang.String serTel;
	/** 客服手机 */
	private java.lang.String serPhone;
	/** 客服邮箱 */
	private java.lang.String serEmail;
	/** 客服联系人 */
	private java.lang.String serContact;
	/** 店铺电话 */
	private java.lang.String shopTel;
	/** 店铺手机 */
	private java.lang.String shopPhone;
	/** 店铺邮箱 */
	private java.lang.String shopEmail;
	/** 店铺联络人 */
	private java.lang.String shopContact;
	/** 创建时间 */
	private java.util.Date creatTimeBegin;
	private java.util.Date creatTimeEnd;
	
	private  java.lang.String comAdd;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.String getComPortraiture() {
		return this.comPortraiture;
	}
	
	public void setComPortraiture(java.lang.String value) {
		this.comPortraiture = value;
	}
	
	public java.lang.String getComTel() {
		return this.comTel;
	}
	
	public void setComTel(java.lang.String value) {
		this.comTel = value;
	}
	
	public java.lang.String getComName() {
		return this.comName;
	}
	
	public void setComName(java.lang.String value) {
		this.comName = value;
	}
	
	public java.lang.String getComPersonnum() {
		return this.comPersonnum;
	}
	
	public void setComPersonnum(java.lang.String value) {
		this.comPersonnum = value;
	}
	
	public java.lang.String getComRegisternum() {
		return this.comRegisternum;
	}
	
	public void setComRegisternum(java.lang.String value) {
		this.comRegisternum = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Long getSpace() {
		return this.space;
	}
	
	public void setSpace(java.lang.Long value) {
		this.space = value;
	}
	
	public java.lang.Long getComPc() {
		return this.comPc;
	}
	
	public void setComPc(java.lang.Long value) {
		this.comPc = value;
	}
	
	public java.lang.String getComState() {
		return this.comState;
	}
	
	public void setComState(java.lang.String value) {
		this.comState = value;
	}
	
	public java.lang.String getComCity() {
		return this.comCity;
	}
	
	public void setComCity(java.lang.String value) {
		this.comCity = value;
	}
	
	public java.lang.String getComAddress() {
		return this.comAddress;
	}
	
	public void setComAddress(java.lang.String value) {
		this.comAddress = value;
	}
	
	public java.lang.String getReferee() {
		return this.referee;
	}
	
	public void setReferee(java.lang.String value) {
		this.referee = value;
	}
	
	public java.lang.String getIndustry() {
		return this.industry;
	}
	
	public void setIndustry(java.lang.String value) {
		this.industry = value;
	}
	
	public java.lang.String getProperty() {
		return this.property;
	}
	
	public void setProperty(java.lang.String value) {
		this.property = value;
	}
	
	public java.lang.String getBusState() {
		return this.busState;
	}
	
	public void setBusState(java.lang.String value) {
		this.busState = value;
	}
	
	public java.lang.String getBusCity() {
		return this.busCity;
	}
	
	public void setBusCity(java.lang.String value) {
		this.busCity = value;
	}
	
	public java.lang.String getBusAddress() {
		return this.busAddress;
	}
	
	public void setBusAddress(java.lang.String value) {
		this.busAddress = value;
	}
	
	public java.lang.String getRegisteredCapital() {
		return this.registeredCapital;
	}
	
	public void setRegisteredCapital(java.lang.String value) {
		this.registeredCapital = value;
	}
	
	public java.lang.String getBusScope() {
		return this.busScope;
	}
	
	public void setBusScope(java.lang.String value) {
		this.busScope = value;
	}
	
	public java.util.Date getBusBegintime() {
		return this.busBegintime;
	}
	
	public void setBusBegintime(java.util.Date value) {
		this.busBegintime = value;
	}
	
	public java.util.Date getBusEndtime() {
		return this.busEndtime;
	}
	
	public void setBusEndtime(java.util.Date value) {
		this.busEndtime = value;
	}
	
	public java.lang.String getOrgNum() {
		return this.orgNum;
	}
	
	public void setOrgNum(java.lang.String value) {
		this.orgNum = value;
	}
	
	public java.util.Date getOrgBegintime() {
		return this.orgBegintime;
	}
	
	public void setOrgBegintime(java.util.Date value) {
		this.orgBegintime = value;
	}
	
	public java.util.Date getOrgEndtime() {
		return this.orgEndtime;
	}
	
	public void setOrgEndtime(java.util.Date value) {
		this.orgEndtime = value;
	}
	
	public java.lang.String getTaxNum() {
		return this.taxNum;
	}
	
	public void setTaxNum(java.lang.String value) {
		this.taxNum = value;
	}
	
	public java.lang.Integer getIstaxpayer() {
		return this.istaxpayer;
	}
	
	public void setIstaxpayer(java.lang.Integer value) {
		this.istaxpayer = value;
	}
	
	public java.lang.Integer getCorCome() {
		return this.corCome;
	}
	
	public void setCorCome(java.lang.Integer value) {
		this.corCome = value;
	}
	
	public java.lang.String getCorName() {
		return this.corName;
	}
	
	public void setCorName(java.lang.String value) {
		this.corName = value;
	}
	
	public java.lang.Long getCorNum() {
		return this.corNum;
	}
	
	public void setCorNum(java.lang.Long value) {
		this.corNum = value;
	}
	
	public java.lang.String getBankPeople() {
		return this.bankPeople;
	}
	
	public void setBankPeople(java.lang.String value) {
		this.bankPeople = value;
	}
	
	public java.lang.Long getBankId() {
		return this.bankId;
	}
	
	public void setBankId(java.lang.Long value) {
		this.bankId = value;
	}
	
	public java.lang.String getBankState() {
		return this.bankState;
	}
	
	public void setBankState(java.lang.String value) {
		this.bankState = value;
	}
	
	public java.lang.String getBankCity() {
		return this.bankCity;
	}
	
	public void setBankCity(java.lang.String value) {
		this.bankCity = value;
	}
	
	public java.lang.String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(java.lang.String value) {
		this.bankName = value;
	}
	
	public java.lang.String getBankNum() {
		return bankNum;
	}

	public void setBankNum(java.lang.String bankNum) {
		this.bankNum = bankNum;
	}

	public java.lang.String getCusTel() {
		return this.cusTel;
	}
	
	public void setCusTel(java.lang.String value) {
		this.cusTel = value;
	}
	
	public java.lang.String getCusPhone() {
		return this.cusPhone;
	}
	
	public void setCusPhone(java.lang.String value) {
		this.cusPhone = value;
	}
	
	public java.lang.String getCusEmail() {
		return this.cusEmail;
	}
	
	public void setCusEmail(java.lang.String value) {
		this.cusEmail = value;
	}
	
	public java.lang.String getCusContact() {
		return this.cusContact;
	}
	
	public void setCusContact(java.lang.String value) {
		this.cusContact = value;
	}
	
	public java.lang.String getSerTel() {
		return this.serTel;
	}
	
	public void setSerTel(java.lang.String value) {
		this.serTel = value;
	}
	
	public java.lang.String getSerPhone() {
		return this.serPhone;
	}
	
	public void setSerPhone(java.lang.String value) {
		this.serPhone = value;
	}
	
	public java.lang.String getSerEmail() {
		return this.serEmail;
	}
	
	public void setSerEmail(java.lang.String value) {
		this.serEmail = value;
	}
	
	public java.lang.String getSerContact() {
		return this.serContact;
	}
	
	public void setSerContact(java.lang.String value) {
		this.serContact = value;
	}
	
	public java.lang.String getShopTel() {
		return this.shopTel;
	}
	
	public void setShopTel(java.lang.String value) {
		this.shopTel = value;
	}
	
	public java.lang.String getShopPhone() {
		return this.shopPhone;
	}
	
	public void setShopPhone(java.lang.String value) {
		this.shopPhone = value;
	}
	
	public java.lang.String getShopEmail() {
		return this.shopEmail;
	}
	
	public void setShopEmail(java.lang.String value) {
		this.shopEmail = value;
	}
	
	public java.lang.String getShopContact() {
		return this.shopContact;
	}
	
	public void setShopContact(java.lang.String value) {
		this.shopContact = value;
	}
	
	public java.util.Date getCreatTimeBegin() {
		return this.creatTimeBegin;
	}
	
	public void setCreatTimeBegin(java.util.Date value) {
		this.creatTimeBegin = value;
	}	
	
	public java.util.Date getCreatTimeEnd() {
		return this.creatTimeEnd;
	}
	
	public void setCreatTimeEnd(java.util.Date value) {
		this.creatTimeEnd = value;
	}
	
	public java.lang.String getComAdd() {
		return comAdd;
	}

	public void setComAdd(java.lang.String comAdd) {
		this.comAdd = comAdd;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

