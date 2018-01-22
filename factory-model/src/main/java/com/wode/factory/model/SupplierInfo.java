/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;


public class SupplierInfo extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SupplierInfo";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_USER_ID = "user_id";
	
	public static final String ALIAS_COM_PORTRAITURE = "公司传真";
	
	public static final String ALIAS_COM_TEL = "公司固定电话";
	
	public static final String ALIAS_COM_NAME = "com_name";
	
	public static final String ALIAS_COM_PERSONNUM = "公司人数";
	
	public static final String ALIAS_COM_REGISTERNUM = "公司营业执照注册号";
	
	public static final String ALIAS_STATUS = "status";
	
	public static final String ALIAS_SPACE = "图片空间";
	
	public static final String ALIAS_COM_PC = "公司邮编";
	
	public static final String ALIAS_COM_STATE = "省";
	
	public static final String ALIAS_COM_CITY = "市";
	
	public static final String ALIAS_COM_ADDRESS = "区";
	
	public static final String ALIAS_REFEREE = "推荐人";
	
	public static final String ALIAS_INDUSTRY = "行业";
	
	public static final String ALIAS_PROPERTY = "性质";
	
	public static final String ALIAS_BUS_STATE = "营业执照省";
	
	public static final String ALIAS_BUS_CITY = "营业执照市";
	
	public static final String ALIAS_BUS_ADDRESS = "busAddress";
	
	public static final String ALIAS_REGISTERED_CAPITAL = "注册资本";
	
	public static final String ALIAS_BUS_SCOPE = "营业范围";
	
	public static final String ALIAS_BUS_BEGINTIME = "营业执照开始时间";
	
	public static final String ALIAS_BUS_ENDTIME = "营业执照结束时间";
	
	public static final String ALIAS_ORG_NUM = "组织机构代码证编号   ";
	
	public static final String ALIAS_ORG_BEGINTIME = "组织机构证开始时间";
	
	public static final String ALIAS_ORG_ENDTIME = "组织机构证结束时间";
	
	public static final String ALIAS_TAX_NUM = "税务登记证编号";
	
	public static final String ALIAS_ISTAXPAYER = "是否普通纳税人  0是  1否";
	
	public static final String ALIAS_COR_COME = "法人归属地  0中国大陆  1港澳  2台湾  3外籍";
	
	public static final String ALIAS_COR_NAME = "法人代表姓名";
	
	public static final String ALIAS_COR_NUM = "身份证";
	
	public static final String ALIAS_BANK_PEOPLE = "开户人";
	
	public static final String ALIAS_BANK_ID = "bankId";
	
	public static final String ALIAS_BANK_STATE = "开户行省";
	
	public static final String ALIAS_BANK_CITY = "开户行市";
	
	public static final String ALIAS_BANK_NAME = "开户行支行名称";
	
	public static final String ALIAS_BANK_NUM = "银行账号";
	
	public static final String ALIAS_CUS_TEL = "售后公司固定电话";
	
	public static final String ALIAS_CUS_PHONE = "售后公司手机";
	
	public static final String ALIAS_CUS_EMAIL = "售后公司邮箱";
	
	public static final String ALIAS_CUS_CONTACT = "售后联系人";
	
	public static final String ALIAS_SER_TEL = "客服固定电话";
	
	public static final String ALIAS_SER_PHONE = "客服手机";
	
	public static final String ALIAS_SER_EMAIL = "客服邮箱";
	
	public static final String ALIAS_SER_CONTACT = "客服联系人";
	
	public static final String ALIAS_SHOP_TEL = "店铺电话";
	
	public static final String ALIAS_SHOP_PHONE = "店铺手机";
	
	public static final String ALIAS_SHOP_EMAIL = "店铺邮箱";
	
	public static final String ALIAS_SHOP_CONTACT = "店铺联络人";
	
	//date formats
	public static final String FORMAT_BUS_BEGINTIME = DATE_TIME_FORMAT;
	public static final String FORMAT_BUS_ENDTIME = DATE_TIME_FORMAT;
	public static final String FORMAT_ORG_BEGINTIME = DATE_TIME_FORMAT;
	public static final String FORMAT_ORG_ENDTIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * user_id       db_column: user_id  
     * 
     * 
     * 
     */	
	private java.lang.Long userId;
    /**
     * 公司传真       db_column: com_portraiture  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String comPortraiture;
    /**
     * 公司固定电话       db_column: com_tel  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String comTel;
    /**
     * com_name       db_column: com_name  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String comName;
    /**
     * 公司人数       db_column: com_personnum  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String comPersonnum;
    /**
     * 公司营业执照注册号       db_column: com_registernum  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String comRegisternum;
    /**
     * status       db_column: status  
     * 
     * 
     * 
     */	
	private java.lang.Integer status;
    /**
     * 图片空间       db_column: space  
     * 
     * 
     * 
     */	
	private java.lang.Long space;
    /**
     * 公司邮编       db_column: com_pc  
     * 
     * 
     * 
     */	
	private java.lang.Long comPc;
    /**
     * 省       db_column: com_state  
     * 
     * 
     * @Length(max=128)
     */	
	private java.lang.String comState;
    /**
     * 市       db_column: com_city  
     * 
     * 
     * @Length(max=128)
     */	
	private java.lang.String comCity;
    /**
     * 区       db_column: com_address  
     * 
     * 
     * @Length(max=128)
     */	
	private java.lang.String comAddress;
    /**
     * 推荐人       db_column: referee  
     * 
     * 
     * @Length(max=128)
     */	
	private java.lang.String referee;
    /**
     * 行业       db_column: industry  
     * 
     * 
     * @Length(max=256)
     */	
	private java.lang.String industry;
    /**
     * 性质       db_column: property  
     * 
     * 
     * @Length(max=256)
     */	
	private java.lang.String property;
    /**
     * 营业执照省       db_column: bus_state  
     * 
     * 
     * @Length(max=128)
     */	
	private java.lang.String busState;
    /**
     * 营业执照市       db_column: bus_city  
     * 
     * 
     * @Length(max=128)
     */	
	private java.lang.String busCity;
    /**
     * busAddress       db_column: bus_address  
     * 
     * 
     * @Length(max=128)
     */	
	private java.lang.String busAddress;
    /**
     * 注册资本       db_column: registered_capital  
     * 
     * 
     * @Length(max=128)
     */	
	private java.lang.String registeredCapital;
    /**
     * 营业范围       db_column: bus_scope  
     * 
     * 
     * @Length(max=2000)
     */	
	private java.lang.String busScope;
    /**
     * 营业执照开始时间       db_column: bus_begintime  
     * 
     * 
     * 
     */	
	private java.util.Date busBegintime;
    /**
     * 营业执照结束时间       db_column: bus_endtime  
     * 
     * 
     * 
     */	
	private java.util.Date busEndtime;
    /**
     * 组织机构代码证编号          db_column: org_num  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String orgNum;
    /**
     * 组织机构证开始时间       db_column: org_begintime  
     * 
     * 
     * 
     */	
	private java.util.Date orgBegintime;
    /**
     * 组织机构证结束时间       db_column: org_endtime  
     * 
     * 
     * 
     */	
	private java.util.Date orgEndtime;
    /**
     * 税务登记证编号       db_column: tax_num  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String taxNum;
    /**
     * 是否普通纳税人  0是  1否       db_column: istaxpayer  
     * 
     * 
     * 
     */	
	private java.lang.Integer istaxpayer;
    /**
     * 法人归属地  0中国大陆  1港澳  2台湾  3外籍       db_column: cor_come  
     * 
     * 
     * 
     */	
	private java.lang.Integer corCome;
    /**
     * 法人代表姓名       db_column: cor_name  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String corName;
    /**
     * 身份证       db_column: cor_num  
     * 
     * 
     * 
     */	
	private java.lang.Long corNum;
    /**
     * 开户人       db_column: bank_people  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String bankPeople;
    /**
     * bankId       db_column: bankId  
     * 
     * 
     * 
     */	
	private java.lang.Long bankId;
    /**
     * 开户行省       db_column: bank_state  
     * 
     * 
     * @Length(max=128)
     */	
	private java.lang.String bankState;
    /**
     * 开户行市       db_column: bank_city  
     * 
     * 
     * @Length(max=128)
     */	
	private java.lang.String bankCity;
    /**
     * 开户行支行名称       db_column: bank_name  
     * 
     * 
     * @Length(max=128)
     */	
	private java.lang.String bankName;
    /**
     * 银行账号       db_column: bank_num  
     * 
     * 
     * 
     */	
	private java.lang.Long bankNum;
    /**
     * 售后公司固定电话       db_column: cus_tel  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String cusTel;
    /**
     * 售后公司手机       db_column: cus_phone  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String cusPhone;
    /**
     * 售后公司邮箱       db_column: cus_email  
     * 
     * 
     * @Email @Length(max=50)
     */	
	private java.lang.String cusEmail;
    /**
     * 售后联系人       db_column: cus_contact  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String cusContact;
    /**
     * 客服固定电话       db_column: ser_tel  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String serTel;
    /**
     * 客服手机       db_column: ser_phone  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String serPhone;
    /**
     * 客服邮箱       db_column: ser_email  
     * 
     * 
     * @Email @Length(max=50)
     */	
	private java.lang.String serEmail;
    /**
     * 客服联系人       db_column: ser_contact  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String serContact;
    /**
     * 店铺电话       db_column: shop_tel  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String shopTel;
    /**
     * 店铺手机       db_column: shop_phone  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String shopPhone;
    /**
     * 店铺邮箱       db_column: shop_email  
     * 
     * 
     * @Email @Length(max=50)
     */	
	private java.lang.String shopEmail;
    /**
     * 店铺联络人       db_column: shop_contact  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String shopContact;
	//columns END

	public SupplierInfo(){
	}

	public SupplierInfo(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setComPortraiture(java.lang.String value) {
		this.comPortraiture = value;
	}
	
	public java.lang.String getComPortraiture() {
		return this.comPortraiture;
	}
	public void setComTel(java.lang.String value) {
		this.comTel = value;
	}
	
	public java.lang.String getComTel() {
		return this.comTel;
	}
	public void setComName(java.lang.String value) {
		this.comName = value;
	}
	
	public java.lang.String getComName() {
		return this.comName;
	}
	public void setComPersonnum(java.lang.String value) {
		this.comPersonnum = value;
	}
	
	public java.lang.String getComPersonnum() {
		return this.comPersonnum;
	}
	public void setComRegisternum(java.lang.String value) {
		this.comRegisternum = value;
	}
	
	public java.lang.String getComRegisternum() {
		return this.comRegisternum;
	}
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setSpace(java.lang.Long value) {
		this.space = value;
	}
	
	public java.lang.Long getSpace() {
		return this.space;
	}
	public void setComPc(java.lang.Long value) {
		this.comPc = value;
	}
	
	public java.lang.Long getComPc() {
		return this.comPc;
	}
	public void setComState(java.lang.String value) {
		this.comState = value;
	}
	
	public java.lang.String getComState() {
		return this.comState;
	}
	public void setComCity(java.lang.String value) {
		this.comCity = value;
	}
	
	public java.lang.String getComCity() {
		return this.comCity;
	}
	public void setComAddress(java.lang.String value) {
		this.comAddress = value;
	}
	
	public java.lang.String getComAddress() {
		return this.comAddress;
	}
	public void setReferee(java.lang.String value) {
		this.referee = value;
	}
	
	public java.lang.String getReferee() {
		return this.referee;
	}
	public void setIndustry(java.lang.String value) {
		this.industry = value;
	}
	
	public java.lang.String getIndustry() {
		return this.industry;
	}
	public void setProperty(java.lang.String value) {
		this.property = value;
	}
	
	public java.lang.String getProperty() {
		return this.property;
	}
	public void setBusState(java.lang.String value) {
		this.busState = value;
	}
	
	public java.lang.String getBusState() {
		return this.busState;
	}
	public void setBusCity(java.lang.String value) {
		this.busCity = value;
	}
	
	public java.lang.String getBusCity() {
		return this.busCity;
	}
	public void setBusAddress(java.lang.String value) {
		this.busAddress = value;
	}
	
	public java.lang.String getBusAddress() {
		return this.busAddress;
	}
	public void setRegisteredCapital(java.lang.String value) {
		this.registeredCapital = value;
	}
	
	public java.lang.String getRegisteredCapital() {
		return this.registeredCapital;
	}
	public void setBusScope(java.lang.String value) {
		this.busScope = value;
	}
	
	public java.lang.String getBusScope() {
		return this.busScope;
	}
	public String getBusBegintimeString() {
		return DateConvertUtils.format(getBusBegintime(), FORMAT_BUS_BEGINTIME);
	}
	public void setBusBegintimeString(String value) {
		setBusBegintime(DateConvertUtils.parse(value, FORMAT_BUS_BEGINTIME,java.util.Date.class));
	}
	
	public void setBusBegintime(java.util.Date value) {
		this.busBegintime = value;
	}
	
	public java.util.Date getBusBegintime() {
		return this.busBegintime;
	}
	public String getBusEndtimeString() {
		return DateConvertUtils.format(getBusEndtime(), FORMAT_BUS_ENDTIME);
	}
	public void setBusEndtimeString(String value) {
		setBusEndtime(DateConvertUtils.parse(value, FORMAT_BUS_ENDTIME,java.util.Date.class));
	}
	
	public void setBusEndtime(java.util.Date value) {
		this.busEndtime = value;
	}
	
	public java.util.Date getBusEndtime() {
		return this.busEndtime;
	}
	public void setOrgNum(java.lang.String value) {
		this.orgNum = value;
	}
	
	public java.lang.String getOrgNum() {
		return this.orgNum;
	}
	public String getOrgBegintimeString() {
		return DateConvertUtils.format(getOrgBegintime(), FORMAT_ORG_BEGINTIME);
	}
	public void setOrgBegintimeString(String value) {
		setOrgBegintime(DateConvertUtils.parse(value, FORMAT_ORG_BEGINTIME,java.util.Date.class));
	}
	
	public void setOrgBegintime(java.util.Date value) {
		this.orgBegintime = value;
	}
	
	public java.util.Date getOrgBegintime() {
		return this.orgBegintime;
	}
	public String getOrgEndtimeString() {
		return DateConvertUtils.format(getOrgEndtime(), FORMAT_ORG_ENDTIME);
	}
	public void setOrgEndtimeString(String value) {
		setOrgEndtime(DateConvertUtils.parse(value, FORMAT_ORG_ENDTIME,java.util.Date.class));
	}
	
	public void setOrgEndtime(java.util.Date value) {
		this.orgEndtime = value;
	}
	
	public java.util.Date getOrgEndtime() {
		return this.orgEndtime;
	}
	public void setTaxNum(java.lang.String value) {
		this.taxNum = value;
	}
	
	public java.lang.String getTaxNum() {
		return this.taxNum;
	}
	public void setIstaxpayer(java.lang.Integer value) {
		this.istaxpayer = value;
	}
	
	public java.lang.Integer getIstaxpayer() {
		return this.istaxpayer;
	}
	public void setCorCome(java.lang.Integer value) {
		this.corCome = value;
	}
	
	public java.lang.Integer getCorCome() {
		return this.corCome;
	}
	public void setCorName(java.lang.String value) {
		this.corName = value;
	}
	
	public java.lang.String getCorName() {
		return this.corName;
	}
	public void setCorNum(java.lang.Long value) {
		this.corNum = value;
	}
	
	public java.lang.Long getCorNum() {
		return this.corNum;
	}
	public void setBankPeople(java.lang.String value) {
		this.bankPeople = value;
	}
	
	public java.lang.String getBankPeople() {
		return this.bankPeople;
	}
	public void setBankId(java.lang.Long value) {
		this.bankId = value;
	}
	
	public java.lang.Long getBankId() {
		return this.bankId;
	}
	public void setBankState(java.lang.String value) {
		this.bankState = value;
	}
	
	public java.lang.String getBankState() {
		return this.bankState;
	}
	public void setBankCity(java.lang.String value) {
		this.bankCity = value;
	}
	
	public java.lang.String getBankCity() {
		return this.bankCity;
	}
	public void setBankName(java.lang.String value) {
		this.bankName = value;
	}
	
	public java.lang.String getBankName() {
		return this.bankName;
	}
	public void setBankNum(java.lang.Long value) {
		this.bankNum = value;
	}
	
	public java.lang.Long getBankNum() {
		return this.bankNum;
	}
	public void setCusTel(java.lang.String value) {
		this.cusTel = value;
	}
	
	public java.lang.String getCusTel() {
		return this.cusTel;
	}
	public void setCusPhone(java.lang.String value) {
		this.cusPhone = value;
	}
	
	public java.lang.String getCusPhone() {
		return this.cusPhone;
	}
	public void setCusEmail(java.lang.String value) {
		this.cusEmail = value;
	}
	
	public java.lang.String getCusEmail() {
		return this.cusEmail;
	}
	public void setCusContact(java.lang.String value) {
		this.cusContact = value;
	}
	
	public java.lang.String getCusContact() {
		return this.cusContact;
	}
	public void setSerTel(java.lang.String value) {
		this.serTel = value;
	}
	
	public java.lang.String getSerTel() {
		return this.serTel;
	}
	public void setSerPhone(java.lang.String value) {
		this.serPhone = value;
	}
	
	public java.lang.String getSerPhone() {
		return this.serPhone;
	}
	public void setSerEmail(java.lang.String value) {
		this.serEmail = value;
	}
	
	public java.lang.String getSerEmail() {
		return this.serEmail;
	}
	public void setSerContact(java.lang.String value) {
		this.serContact = value;
	}
	
	public java.lang.String getSerContact() {
		return this.serContact;
	}
	public void setShopTel(java.lang.String value) {
		this.shopTel = value;
	}
	
	public java.lang.String getShopTel() {
		return this.shopTel;
	}
	public void setShopPhone(java.lang.String value) {
		this.shopPhone = value;
	}
	
	public java.lang.String getShopPhone() {
		return this.shopPhone;
	}
	public void setShopEmail(java.lang.String value) {
		this.shopEmail = value;
	}
	
	public java.lang.String getShopEmail() {
		return this.shopEmail;
	}
	public void setShopContact(java.lang.String value) {
		this.shopContact = value;
	}
	
	public java.lang.String getShopContact() {
		return this.shopContact;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("ComPortraiture",getComPortraiture())
			.append("ComTel",getComTel())
			.append("ComName",getComName())
			.append("ComPersonnum",getComPersonnum())
			.append("ComRegisternum",getComRegisternum())
			.append("Status",getStatus())
			.append("Space",getSpace())
			.append("ComPc",getComPc())
			.append("ComState",getComState())
			.append("ComCity",getComCity())
			.append("ComAddress",getComAddress())
			.append("Referee",getReferee())
			.append("Industry",getIndustry())
			.append("Property",getProperty())
			.append("BusState",getBusState())
			.append("BusCity",getBusCity())
			.append("BusAddress",getBusAddress())
			.append("RegisteredCapital",getRegisteredCapital())
			.append("BusScope",getBusScope())
			.append("BusBegintime",getBusBegintime())
			.append("BusEndtime",getBusEndtime())
			.append("OrgNum",getOrgNum())
			.append("OrgBegintime",getOrgBegintime())
			.append("OrgEndtime",getOrgEndtime())
			.append("TaxNum",getTaxNum())
			.append("Istaxpayer",getIstaxpayer())
			.append("CorCome",getCorCome())
			.append("CorName",getCorName())
			.append("CorNum",getCorNum())
			.append("BankPeople",getBankPeople())
			.append("BankId",getBankId())
			.append("BankState",getBankState())
			.append("BankCity",getBankCity())
			.append("BankName",getBankName())
			.append("BankNum",getBankNum())
			.append("CusTel",getCusTel())
			.append("CusPhone",getCusPhone())
			.append("CusEmail",getCusEmail())
			.append("CusContact",getCusContact())
			.append("SerTel",getSerTel())
			.append("SerPhone",getSerPhone())
			.append("SerEmail",getSerEmail())
			.append("SerContact",getSerContact())
			.append("ShopTel",getShopTel())
			.append("ShopPhone",getShopPhone())
			.append("ShopEmail",getShopEmail())
			.append("ShopContact",getShopContact())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SupplierInfo == false) return false;
		if(this == obj) return true;
		SupplierInfo other = (SupplierInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

