/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;


import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;


/**
 * @author user
 *
 */
public class Supplier extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Supplier";
	
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
	
	public static final String ALIAS_CREAT_TIME = "创建时间";

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
	@Id
	private java.lang.Long id;
    /**
     * user_id       db_column: user_id  
     * 
     * 
     * 
     */	
	@Column("user_id")
	private java.lang.Long userId;
    /**
     * 公司传真       db_column: com_portraiture  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("com_portraiture")
	private java.lang.String comPortraiture;
    /**
     * 公司固定电话       db_column: com_tel  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("com_tel")
	private java.lang.String comTel;
    /**
     * com_name       db_column: com_name  
     * 
     * 
     * @Length(max=50)
     */
	@Column("com_name")
	private java.lang.String comName;
    /**
     * 公司人数       db_column: com_personnum  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("com_personnum")
	private java.lang.String comPersonnum;
    /**
     * 公司营业执照注册号       db_column: com_registernum  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("com_registernum")
	private java.lang.String comRegisternum;
    /**
     * status       db_column: status  
     * 
     * 
     * 
     */
	@Column
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
	@Column("com_pc")
	private java.lang.Long comPc;
    /**
     * 省       db_column: com_state  
     * 
     * 
     * @Length(max=128)
     */	
	@Column("db_column")
	private java.lang.String comState;
    /**
     * 市       db_column: com_city  
     * 
     * 
     * @Length(max=128)
     */	
	@Column("com_city")
	private java.lang.String comCity;
    /**
     * 区       db_column: com_address  
     * 
     * 
     * @Length(max=128)
     */	
	@Column("com_address")
	private java.lang.String comAddress;
    /**
     * 推荐人       db_column: referee  
     * 
     * 
     * @Length(max=128)
     */	
	@Column
	private java.lang.String referee;
    /**
     * 行业       db_column: industry  
     * 
     * 
     * @Length(max=256)
     */	
	@Column
	private java.lang.String industry;
    /**
     * 性质       db_column: property  
     * 
     * 
     * @Length(max=256)
     */	
	@Column
	private java.lang.String property;
    /**
     * 营业执照省       db_column: bus_state  
     * 
     * 
     * @Length(max=128)
     */	
	@Column("bus_state")
	private java.lang.String busState;
    /**
     * 营业执照市       db_column: bus_city  
     * 
     * 
     * @Length(max=128)
     */	
	@Column("bus_city")
	private java.lang.String busCity;
    /**
     * busAddress       db_column: bus_address  
     * 
     * 
     * @Length(max=128)
     */
	@Column("bus_address")
	private java.lang.String busAddress;
    /**
     * 注册资本       db_column: registered_capital  
     * 
     * 
     * @Length(max=128)
     */	
	@Column("registered_capital")
	private java.lang.String registeredCapital;
	 /**
     * 注册币种  db_column registered_capital_currency
     * 
     * 
     * 
     */
    @Column("registered_capital_currency")
    private java.lang.Integer registeredCapitalCurrency;
    /**
     * 营业范围       db_column: bus_scope  
     * 
     * 
     * @Length(max=2000)
     */	
	@Column("bus_scope")
	private java.lang.String busScope;
    /**
     * 营业执照开始时间       db_column: bus_begintime  
     * 
     * 
     * 
     */	
	@Column("bus_begintime")
	private java.util.Date busBegintime;
    /**
     * 营业执照结束时间       db_column: bus_endtime  
     * 
     * 
     * 
     */	
	@Column("bus_endtime")
	private java.util.Date busEndtime;

	@Column("bus_img_url")
	private java.lang.String busImgUrl;
	
    /**
     * 组织机构代码证编号          db_column: org_num  
     * 
     * 
     * @Length(max=100)
     */	
	@Column("org_num")
	private java.lang.String orgNum;
    /**
     * 组织机构证开始时间       db_column: org_begintime  
     * 
     * 
     * 
     */	
	@Column("org_begintime")
	private java.util.Date orgBegintime;
    /**
     * 组织机构证结束时间       db_column: org_endtime  
     * 
     * 
     * 
     */
	@Column("org_endtime")
	private java.util.Date orgEndtime;
	
	@Column("org_img_url")
	private java.lang.String orgImgUrl;
    /**
     * 税务登记证编号       db_column: tax_num  
     * 
     * 
     * @Length(max=100)
     */
	@Column("tax_num")
	private java.lang.String taxNum;
	
	@Column("tax_img_url")
	private java.lang.String taxImgUrl;
	
	@Column("protocol_file")
	private java.lang.String protocolFile;
	/**
	 * 企业id
	 */
	@Column("enterprise_id")
	private Long enterpriseId;

	public Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Float getPlatformUseFee() {
		return platformUseFee;
	}

	public void setPlatformUseFee(Float platformUseFee) {
		this.platformUseFee = platformUseFee;
	}

	public Float getCashDeposit() {
		return cashDeposit;
	}

	public void setCashDeposit(Float cashDeposit) {
		this.cashDeposit = cashDeposit;
	}

	@Column("platformUseFee")
	private Float platformUseFee;
	@Column("cashDeposit")
	private Float cashDeposit;
    /**
     * 是否普通纳税人  0是  1否       db_column: istaxpayer  
     * 
     * 
     * 
     */
	private java.lang.Integer istaxpayer;
	
	@Column("istaxpayer_img_url")
	private java.lang.String istaxpayerImgUrl;
	
    /**
     * 法人归属地  0中国大陆  1港澳  2台湾  3外籍       db_column: cor_come  
     * 
     * 
     * 
     */	
	@Column("cor_come")
	private java.lang.Integer corCome;
    /**
     * 法人代表姓名       db_column: cor_name  
     * 
     * 
     * @Length(max=50)
     */
	@Column("cor_name")
	private java.lang.String corName;
    /**
     * 身份证       db_column: cor_num  
     * 
     * 
     * 
     */
	@Column("cor_num")
	private java.lang.String corNum;

	@Column("cor_num")
	private java.lang.String corImgUrl;
	
    /**
     * 开户人或公司       db_column: bank_people  
     * 
     * 
     * @Length(max=100)
     */	
	@Column("bank_people")
	private java.lang.String bankPeople;
    /**
     * bankId       db_column: bankId  
     * 
     * 
     * 
     */	
	@Column
	private java.lang.String bankId;
    /**
     * 开户行省       db_column: bank_state  
     * 
     * 
     * @Length(max=128)
     */	
	@Column("bank_state")
	private java.lang.String bankState;
    /**
     * 开户行市       db_column: bank_city  
     * 
     * 
     * @Length(max=128)
     */	
	@Column("bank_city")
	private java.lang.String bankCity;
    /**
     * 开户行支行名称       db_column: bank_name  
     * 
     * 
     * @Length(max=128)
     */	
	@Column("bank_name")
	private java.lang.String bankName;
    /**
     * 银行账号       db_column: bank_num  
     * 
     * 
     * 
     */	
	@Column("bank_num")
	private java.lang.String bankNum;
	
	@Column("bank_img_url")
	private java.lang.String bankImgUrl;
	
    /**
     * 创建时间       db_column: creat_time  
     * 
     * 
     * 
     */	
	@Column("creat_time")
	private java.util.Date creatTime;
	
	/**
     * 入驻状态      db_column: enter_type  
     * 
     * 
     * 
     */	
	@Column("enter_type")
	private  java.lang.Integer enterType;


	/**
	 * 佣金比例
	 */
	@Column("commission_rate")
	private BigDecimal commissionRate;

	/**
     * 公司区域      db_column: com_add  
     * 
     * 
     * 
     */	
	@Column("com_add")
	private  java.lang.String comAdd;

    /**
     * 招商经理id       db_column: manager_id
     * 
     * 
     * 
     */ 
    @Column("manager_id")
    private java.lang.Long managerId;
    /**
     * 招商经理名       db_column: manager_name
     * 
     * 
     * 
     */ 
    @Column("manager_name")
    private java.lang.String managerName;
    
   @Column("shipping_free")
   private double shippingFree;			//全场包邮设置
	
	@Column("people_number")
	private java.lang.Integer peopleNumber;
	/**
	 * 企业logo db_column: firm_logo
	 */
	@Column("firm_logo")
	private java.lang.String firmLogo;
	/**
	 * 企业昵称 db_column: nick_name
	 */
	@Column("nick_name")
	private java.lang.String nickName;
	
	private List<Shop> shopList;
	//columns END
	
	//tranfer BEGIN
	private java.lang.String orgNum1;
	private java.lang.String orgNum2;
	private java.lang.String comTel1;
	private java.lang.String comTel2;
	private java.lang.String comTel3;
	private java.lang.String comPortraiture1;
	private java.lang.String comPortraiture2;
	private java.lang.String comPortraiture3;
	private java.lang.String shopTel1;
	private java.lang.String shopTel2;
	private java.lang.String shopTel3;
	private java.lang.String cusTel1;
	private java.lang.String cusTel2;
	private java.lang.String cusTel3;
	private java.lang.String serTel1;
	private java.lang.String serTel2;
	private java.lang.String serTel3;
	private java.lang.Long qq;
	private java.lang.String serContact;
	private java.lang.String serEmail;
	private java.lang.Long serPhone;
	//logo业务字段
	private java.lang.String queryLogo;
	//tranfer END


	public Supplier(){
	}

	public Supplier(
		java.lang.Long id
	){
		this.id = id;
	}
	
	public java.lang.String getNickName() {
		return nickName;
	}

	public void setNickName(java.lang.String nickName) {
		this.nickName = nickName;
	}

	public java.lang.Integer getPeopleNumber() {
		return peopleNumber;
	}
	public void setQueryLogo(java.lang.String value){
		this.queryLogo=value;
	}
	public java.lang.String getQueryLogo(){
		return this.queryLogo;
	}
	public void setPeopleNumber(java.lang.Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	
	public void setFirmLogo(java.lang.String value){
		this.firmLogo = value;
	}
	public java.lang.String getFirmLogo(){
		return this.firmLogo;
	}
	public java.util.Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(java.util.Date creatTime) {
		this.creatTime = creatTime;
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
	
	public void setBusBegintime(java.util.Date value) {
		this.busBegintime = value;
	}
	
	public java.util.Date getBusBegintime() {
		return this.busBegintime;
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
	
	public void setOrgBegintime(java.util.Date value) {
		this.orgBegintime = value;
	}
	
	public java.util.Date getOrgBegintime() {
		return this.orgBegintime;
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
	public void setCorNum(java.lang.String value) {
		this.corNum = value;
	}
	
	public java.lang.String getCorNum() {
		return this.corNum;
	}
	public void setBankPeople(java.lang.String value) {
		this.bankPeople = value;
	}
	
	public java.lang.String getBankPeople() {
		return this.bankPeople;
	}
	public void setBankId(java.lang.String value) {
		this.bankId = value;
	}
	
	public java.lang.String getBankId() {
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
	public java.lang.String getBankNum() {
		return bankNum;
	}

	public void setBankNum(java.lang.String bankNum) {
		this.bankNum = bankNum;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	
	public java.lang.String getOrgNum1() {
		return orgNum1;
	}

	public void setOrgNum1(java.lang.String orgNum1) {
		this.orgNum1 = orgNum1;
	}

	public java.lang.String getOrgNum2() {
		return orgNum2;
	}

	public void setOrgNum2(java.lang.String orgNum2) {
		this.orgNum2 = orgNum2;
	}

	public java.lang.String getComTel1() {
		return comTel1;
	}

	public void setComTel1(java.lang.String comTel1) {
		this.comTel1 = comTel1;
	}

	public java.lang.String getComTel2() {
		return comTel2;
	}

	public void setComTel2(java.lang.String comTel2) {
		this.comTel2 = comTel2;
	}

	public java.lang.String getComTel3() {
		return comTel3;
	}

	public void setComTel3(java.lang.String comTel3) {
		this.comTel3 = comTel3;
	}

	public java.lang.String getComPortraiture1() {
		return comPortraiture1;
	}

	public void setComPortraiture1(java.lang.String comPortraiture1) {
		this.comPortraiture1 = comPortraiture1;
	}

	public java.lang.String getComPortraiture2() {
		return comPortraiture2;
	}

	public void setComPortraiture2(java.lang.String comPortraiture2) {
		this.comPortraiture2 = comPortraiture2;
	}

	public java.lang.String getComPortraiture3() {
		return comPortraiture3;
	}

	public void setComPortraiture3(java.lang.String comPortraiture3) {
		this.comPortraiture3 = comPortraiture3;
	}

	public java.lang.String getShopTel1() {
		return shopTel1;
	}

	public void setShopTel1(java.lang.String shopTel1) {
		this.shopTel1 = shopTel1;
	}

	public java.lang.String getShopTel2() {
		return shopTel2;
	}

	public void setShopTel2(java.lang.String shopTel2) {
		this.shopTel2 = shopTel2;
	}

	public java.lang.String getShopTel3() {
		return shopTel3;
	}

	public void setShopTel3(java.lang.String shopTel3) {
		this.shopTel3 = shopTel3;
	}

	public java.lang.String getCusTel1() {
		return cusTel1;
	}

	public void setCusTel1(java.lang.String cusTel1) {
		this.cusTel1 = cusTel1;
	}

	public java.lang.String getCusTel2() {
		return cusTel2;
	}

	public void setCusTel2(java.lang.String cusTel2) {
		this.cusTel2 = cusTel2;
	}

	public java.lang.String getCusTel3() {
		return cusTel3;
	}

	public void setCusTel3(java.lang.String cusTel3) {
		this.cusTel3 = cusTel3;
	}

	public java.lang.String getSerTel1() {
		return serTel1;
	}

	public void setSerTel1(java.lang.String serTel1) {
		this.serTel1 = serTel1;
	}

	public java.lang.String getSerTel2() {
		return serTel2;
	}

	public void setSerTel2(java.lang.String serTel2) {
		this.serTel2 = serTel2;
	}

	public java.lang.String getSerTel3() {
		return serTel3;
	}

	public void setSerTel3(java.lang.String serTel3) {
		this.serTel3 = serTel3;
	}
	
	public java.lang.Long getQq() {
		return qq;
	}

	public void setQq(java.lang.Long qq) {
		this.qq = qq;
	}
	
	public java.lang.String getSerContact() {
		return serContact;
	}

	public void setSerContact(java.lang.String serContact) {
		this.serContact = serContact;
	}

	public java.lang.String getSerEmail() {
		return serEmail;
	}

	public void setSerEmail(java.lang.String serEmail) {
		this.serEmail = serEmail;
	}

	public java.lang.Long getSerPhone() {
		return serPhone;
	}

	public void setSerPhone(java.lang.Long serPhone) {
		this.serPhone = serPhone;
	}
	 
	public java.lang.Integer getEnterType() {
		return enterType;
	}

	public void setEnterType(java.lang.Integer enterType) {
		this.enterType = enterType;
	}
	
	public java.lang.String getComAdd() {
		return comAdd;
	}

	public void setComAdd(java.lang.String comAdd) {
		this.comAdd = comAdd;
	}

	public BigDecimal getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}

	public java.lang.String getProtocolFile() {
		return protocolFile;
	}

	public void setProtocolFile(java.lang.String protocolFile) {
		this.protocolFile = protocolFile;
	}

	
	
	public java.lang.String getBusImgUrl() {
		return busImgUrl;
	}

	public void setBusImgUrl(java.lang.String busImgUrl) {
		this.busImgUrl = busImgUrl;
	}

	public java.lang.String getOrgImgUrl() {
		return orgImgUrl;
	}

	public void setOrgImgUrl(java.lang.String orgImgUrl) {
		this.orgImgUrl = orgImgUrl;
	}

	public java.lang.String getTaxImgUrl() {
		return taxImgUrl;
	}

	public void setTaxImgUrl(java.lang.String taxImgUrl) {
		this.taxImgUrl = taxImgUrl;
	}

	public java.lang.String getIstaxpayerImgUrl() {
		return istaxpayerImgUrl;
	}

	public void setIstaxpayerImgUrl(java.lang.String istaxpayerImgUrl) {
		this.istaxpayerImgUrl = istaxpayerImgUrl;
	}

	public java.lang.String getCorImgUrl() {
		return corImgUrl;
	}

	public void setCorImgUrl(java.lang.String corImgUrl) {
		this.corImgUrl = corImgUrl;
	}

	public java.lang.String getBankImgUrl() {
		return bankImgUrl;
	}

	public void setBankImgUrl(java.lang.String bankImgUrl) {
		this.bankImgUrl = bankImgUrl;
	}

	public java.lang.Long getManagerId() {
		return managerId;
	}

	public void setManagerId(java.lang.Long managerId) {
		this.managerId = managerId;
	}

	public java.lang.String getManagerName() {
		return managerName;
	}

	public void setManagerName(java.lang.String managerName) {
		this.managerName = managerName;
	}

	public double getShippingFree() {
		return shippingFree;
	}

	public void setShippingFree(double shippingFree) {
		this.shippingFree = shippingFree;
	}
	
	public java.lang.Integer getRegisteredCapitalCurrency() {
		return registeredCapitalCurrency;
	}

	public void setRegisteredCapitalCurrency(java.lang.Integer registeredCapitalCurrency) {
		this.registeredCapitalCurrency = registeredCapitalCurrency;
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
			.append("CommissionRate", getCommissionRate().toString())
			.append("PeopleNumber", getPeopleNumber())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Supplier == false) return false;
		if(this == obj) return true;
		Supplier other = (Supplier)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

