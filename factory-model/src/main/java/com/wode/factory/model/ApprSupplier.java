package com.wode.factory.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_appr_supplier")
public class ApprSupplier extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4901091049746711929L;
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
     * 审核状态 0：未提交 1：待审核 2：招商通过 3：法务通过 4：运营通过  -1不通过       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    /**
     * 商户id       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    /**
     * 企业类型 0:生产厂商/1:品牌商/2:代理商       db_column: property
     * 
     * 
     * 
     */ 
    @Column("property")
    private java.lang.String property;
    /**
     * 公司名称       db_column: com_name
     * 
     * 
     * 
     */ 
    @Column("com_name")
    private java.lang.String comName;
    /**
     * 公司营业执照注册号       db_column: com_registernum
     * 
     * 
     * 
     */ 
    @Column("com_registernum")
    private java.lang.String comRegisternum;
    /**
     * 营业执照省       db_column: bus_state
     * 
     * 
     * 
     */ 
    @Column("bus_state")
    private java.lang.String busState;
    /**
     * 营业执照市       db_column: bus_city
     * 
     * 
     * 
     */ 
    @Column("bus_city")
    private java.lang.String busCity;
    /**
     * 营业执照区       db_column: bus_address
     * 
     * 
     * 
     */ 
    @Column("bus_address")
    private java.lang.String busAddress;
    /**
     * 注册资本       db_column: registered_capital
     * 
     * 
     * 
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
     * 
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
    /**
     * 营业执照文件       db_column: bus_img_url
     * 
     * 
     * 
     */ 
    @Column("bus_img_url")
    private java.lang.String busImgUrl;
    /**
     * 组织机构代码证编号       db_column: org_num
     * 
     * 
     * 
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
    /**
     * 组织机构证文件       db_column: org_img_url
     * 
     * 
     * 
     */ 
    @Column("org_img_url")
    private java.lang.String orgImgUrl;
    /**
     * 税务登记证编号       db_column: tax_num
     * 
     * 
     * 
     */ 
    @Column("tax_num")
    private java.lang.String taxNum;
    /**
     * 税务登记证文件       db_column: tax_img_url
     * 
     * 
     * 
     */ 
    @Column("tax_img_url")
    private java.lang.String taxImgUrl;
    /**
     * 是否普通纳税人  0是  1否       db_column: istaxpayer
     * 
     * 
     * 
     */ 
    @Column("istaxpayer")
    private java.lang.Integer istaxpayer;
    /**
     * 一般纳税人资质证明文件       db_column: istaxpayer_img_url
     * 
     * 
     * 
     */ 
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
     * 
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
    /**
     * 法人身份证文件       db_column: cor_img_url
     * 
     * 
     * 
     */ 
    @Column("cor_img_url")
    private java.lang.String corImgUrl;
    /**
     * 开户人       db_column: bank_people
     * 
     * 
     * 
     */ 
    @Column("bank_people")
    private java.lang.String bankPeople;
    /**
     * 开户行       db_column: bankId
     * 
     * 
     * 
     */ 
    @Column("bankId")
    private java.lang.String bankId;
    /**
     * 开户行省       db_column: bank_state
     * 
     * 
     * 
     */ 
    @Column("bank_state")
    private java.lang.String bankState;
    /**
     * 开户行市       db_column: bank_city
     * 
     * 
     * 
     */ 
    @Column("bank_city")
    private java.lang.String bankCity;
    /**
     * 开户行支行名称       db_column: bank_name
     * 
     * 
     * 
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
    /**
     * 银行开户许可证文件       db_column: bank_img_url
     * 
     * 
     * 
     */ 
    @Column("bank_img_url")
    private java.lang.String bankImgUrl;
    /**
     * 省       db_column: com_state
     * 
     * 
     * 
     */ 
    @Column("com_state")
    private java.lang.String comState;
    /**
     * 市       db_column: com_city
     * 
     * 
     * 
     */ 
    @Column("com_city")
    private java.lang.String comCity;
    /**
     * 区       db_column: com_address
     * 
     * 
     * 
     */ 
    @Column("com_address")
    private java.lang.String comAddress;
    /**
     * 后加字段  公司区域       db_column: com_add
     * 
     * 
     * 
     */ 
    @Column("com_add")
    private java.lang.String comAdd;
    /**
     * 公司邮编       db_column: com_pc
     * 
     * 
     * 
     */ 
    @Column("com_pc")
    private java.lang.Long comPc;
    /**
     * 公司固定电话       db_column: com_tel
     * 
     * 
     * 
     */ 
    @Column("com_tel")
    private java.lang.String comTel;
    /**
     * 公司传真       db_column: com_portraiture
     * 
     * 
     * 
     */ 
    @Column("com_portraiture")
    private java.lang.String comPortraiture;
    /**
     * 图片空间       db_column: space
     * 
     * 
     * 
     */ 
    @Column("space")
    private java.lang.Long space;
    /**
     * 推荐人       db_column: referee
     * 
     * 
     * 
     */ 
    @Column("referee")
    private java.lang.String referee;
    /**
     * 行业       db_column: industry
     * 
     * 
     * 
     */ 
    @Column("industry")
    private java.lang.String industry;
    /**
     * 公司人数       db_column: com_personnum
     * 
     * 
     * 
     */ 
    @Column("com_personnum")
    private java.lang.String comPersonnum;
    /**
     * 企业人数 db_column:people_number
     */
    @Column("people_number")
    private java.lang.Integer peopleNumber;
    /**
     * 创建时间       db_column: creat_time
     * 
     * 
     * 
     */ 
    @Column("creat_time")
    private java.util.Date creatTime;
    /**
     * 创建者       db_column: creat_user
     * 
     * 
     * 
     */ 
    @Column("creat_user")
    private java.lang.Long creatUser;
    /**
     * 创建者       db_column: creat_name
     * 
     * 
     * 
     */ 
    @Column("creat_name")
    private java.lang.String creatName;
    /**
     * 创建者       db_column: to_email
     * 
     * 
     * 
     */ 
    @Column("to_email")
    private java.lang.String toEmail;

    /**
     * 入驻状态   1 -5 对应入驻5步       db_column: enter_type
     * 
     * 
     * 
     */ 
    @Column("enter_type")
    private java.lang.Integer enterType;
    /**
     * 保证金       db_column: cashDeposit
     * 
     * 
     * 
     */ 
    @Column("cashDeposit")
    private java.lang.Float cashDeposit;
    /**
     * 平台使用费       db_column: platformUseFee
     * 
     * 
     * 
     */ 
    @Column("platformUseFee")
    private java.lang.Float platformUseFee;
    /**
     * 协议文件名称       db_column: protocol_file
     * 
     * 
     * 
     */ 
    @Column("protocol_file")
    private java.lang.String protocolFile;
    /**
     * 账期       db_column: saleDurationKey
     * 
     * 
     * 
     */ 
    @Column("saleDurationKey")
    private java.lang.String saleDurationKey;
    /**
     * 首次账单生成日       db_column: startTime
     * 
     * 
     * 
     */ 
    @Column("startTime")
    private java.util.Date startTime;
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
    /**
     * 招商审核时间       db_column: manager_chk_time
     * 
     * 
     * 
     */ 
    @Column("manager_chk_time")
    private java.util.Date managerChkTime;
    /**
     * 招商审核人员       db_column: manager_chk_id
     * 
     * 
     * 
     */ 
    @Column("manager_chk_id")
    private java.lang.Long managerChkId;
    /**
     * 招商审核信息       db_column: manager_chk_desc
     * 
     * 
     * 
     */ 
    @Column("manager_chk_desc")
    private java.lang.String managerChkDesc;
    /**
     * 招商审核信息图       db_column: manager_chk_img
     * 
     * 
     * 
     */ 
    @Column("manager_chk_img")
    private java.lang.String managerChkImg;
    /**
     * 法务审核时间       db_column: law_chk_time
     * 
     * 
     * 
     */ 
    @Column("law_chk_time")
    private java.util.Date lawChkTime;
    /**
     * 法务审核人员       db_column: law_chk_id
     * 
     * 
     * 
     */ 
    @Column("law_chk_id")
    private java.lang.Long lawChkId;
    /**
     * 法务审核信息       db_column: law_chk_desc
     * 
     * 
     * 
     */ 
    @Column("law_chk_desc")
    private java.lang.String lawChkDesc;
    /**
     * 法务审核信息图       db_column: law_chk_img
     * 
     * 
     * 
     */ 
    @Column("law_chk_img")
    private java.lang.String lawChkImg;
    /**
     * 运营审核时间       db_column: bus_chk_time
     * 
     * 
     * 
     */ 
    @Column("bus_chk_time")
    private java.util.Date busChkTime;
    /**
     * 运营审核人员       db_column: bus_chk_id
     * 
     * 
     * 
     */ 
    @Column("bus_chk_id")
    private java.lang.Long busChkId;
    /**
     * 运营审核信息       db_column: bus_chk_desc
     * 
     * 
     * 
     */ 
    @Column("bus_chk_desc")
    private java.lang.String busChkDesc;
    /**
     * 运营审核信息图       db_column: bus_chk_img
     * 
     * 
     * 
     */ 
    @Column("bus_chk_img")
    private java.lang.String busChkImg;
    /**
     * 修改时间       db_column: update_time
     * 
     * 
     * 
     */ 
    @Column("update_time")
    private java.util.Date updateTime;
    /**
     * 修改人id       db_column: update_user
     * 
     * 
     * 
     */ 
    @Column("update_user")
    private java.lang.Long updateUser;
    /**
     * 修改人名       db_column: update_name
     * 
     * 
     * 
     */ 
    @Column("update_name")
    private java.lang.String updateName;

    //columns END

	private java.lang.String orgNum1;
	private java.lang.String orgNum2;
	private java.lang.String comTel1;
	private java.lang.String comTel2;
	private java.lang.String comTel3;
	private java.lang.String comPortraiture1;
	private java.lang.String comPortraiture2;
	private java.lang.String comPortraiture3;
    
    
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

	public java.lang.Integer getRegisteredCapitalCurrency() {
		return registeredCapitalCurrency;
	}

	public void setRegisteredCapitalCurrency(java.lang.Integer registeredCapitalCurrency) {
		this.registeredCapitalCurrency = registeredCapitalCurrency;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Status",getStatus())
            .append("SupplierId",getSupplierId())
            .append("Property",getProperty())
            .append("ComName",getComName())
            .append("ComRegisternum",getComRegisternum())
            .append("BusState",getBusState())
            .append("BusCity",getBusCity())
            .append("BusAddress",getBusAddress())
            .append("RegisteredCapital",getRegisteredCapital())
            .append("BusScope",getBusScope())
            .append("BusBegintime",getBusBegintime())
            .append("BusEndtime",getBusEndtime())
            .append("BusImgUrl",getBusImgUrl())
            .append("OrgNum",getOrgNum())
            .append("OrgBegintime",getOrgBegintime())
            .append("OrgEndtime",getOrgEndtime())
            .append("OrgImgUrl",getOrgImgUrl())
            .append("TaxNum",getTaxNum())
            .append("TaxImgUrl",getTaxImgUrl())
            .append("Istaxpayer",getIstaxpayer())
            .append("IstaxpayerImgUrl",getIstaxpayerImgUrl())
            .append("CorCome",getCorCome())
            .append("CorName",getCorName())
            .append("CorNum",getCorNum())
            .append("CorImgUrl",getCorImgUrl())
            .append("BankPeople",getBankPeople())
            .append("BankId",getBankId())
            .append("BankState",getBankState())
            .append("BankCity",getBankCity())
            .append("BankName",getBankName())
            .append("BankNum",getBankNum())
            .append("BankImgUrl",getBankImgUrl())
            .append("ComState",getComState())
            .append("ComCity",getComCity())
            .append("ComAddress",getComAddress())
            .append("ComAdd",getComAdd())
            .append("ComPc",getComPc())
            .append("ComTel",getComTel())
            .append("ComPortraiture",getComPortraiture())
            .append("Space",getSpace())
            .append("Referee",getReferee())
            .append("Industry",getIndustry())
            .append("ComPersonnum",getComPersonnum())
            .append("CreatTime",getCreatTime())
            .append("CreatUser",getCreatUser())
            .append("CreatName",getCreatName())
            .append("EnterType",getEnterType())
            .append("CashDeposit",getCashDeposit())
            .append("PlatformUseFee",getPlatformUseFee())
            .append("ProtocolFile",getProtocolFile())
            .append("SaleDurationKey",getSaleDurationKey())
            .append("StartTime",getStartTime())
            .append("ManagerId",getManagerId())
            .append("ManagerName",getManagerName())
            .append("ManagerChkTime",getManagerChkTime())
            .append("ManagerChkId",getManagerChkId())
            .append("ManagerChkDesc",getManagerChkDesc())
            .append("ManagerChkImg",getManagerChkImg())
            .append("LawChkTime",getLawChkTime())
            .append("LawChkId",getLawChkId())
            .append("LawChkDesc",getLawChkDesc())
            .append("LawChkImg",getLawChkImg())
            .append("BusChkTime",getBusChkTime())
            .append("BusChkId",getBusChkId())
            .append("BusChkDesc",getBusChkDesc())
            .append("BusChkImg",getBusChkImg())
            .append("UpdateTime",getUpdateTime())
            .append("UpdateUser",getUpdateUser())
            .append("UpdateName",getUpdateName())
            .append("peopeleNumber",getPeopleNumber())
            .toString();
    }
	
	public java.lang.Integer getPeopleNumber() {
			return peopleNumber;
		}

	public void setPeopleNumber(java.lang.Integer peopleNumber) {
			this.peopleNumber = peopleNumber;
		}
    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.String getProperty() {
		return property;
	}

	public void setProperty(java.lang.String property) {
		this.property = property;
	}

	public java.lang.String getComName() {
		return comName;
	}

	public void setComName(java.lang.String comName) {
		this.comName = comName;
	}

	public java.lang.String getComRegisternum() {
		return comRegisternum;
	}

	public void setComRegisternum(java.lang.String comRegisternum) {
		this.comRegisternum = comRegisternum;
	}

	public java.lang.String getBusState() {
		return busState;
	}

	public void setBusState(java.lang.String busState) {
		this.busState = busState;
	}

	public java.lang.String getBusCity() {
		return busCity;
	}

	public void setBusCity(java.lang.String busCity) {
		this.busCity = busCity;
	}

	public java.lang.String getBusAddress() {
		return busAddress;
	}

	public void setBusAddress(java.lang.String busAddress) {
		this.busAddress = busAddress;
	}

	public java.lang.String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(java.lang.String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public java.lang.String getBusScope() {
		return busScope;
	}

	public void setBusScope(java.lang.String busScope) {
		this.busScope = busScope;
	}

	public java.util.Date getBusBegintime() {
		return busBegintime;
	}

	public void setBusBegintime(java.util.Date busBegintime) {
		this.busBegintime = busBegintime;
	}

	public java.util.Date getBusEndtime() {
		return busEndtime;
	}

	public void setBusEndtime(java.util.Date busEndtime) {
		this.busEndtime = busEndtime;
	}

	public java.lang.String getBusImgUrl() {
		return busImgUrl;
	}

	public void setBusImgUrl(java.lang.String busImgUrl) {
		this.busImgUrl = busImgUrl;
	}

	public java.lang.String getOrgNum() {
		return orgNum;
	}

	public void setOrgNum(java.lang.String orgNum) {
		this.orgNum = orgNum;
	}

	public java.util.Date getOrgBegintime() {
		return orgBegintime;
	}

	public void setOrgBegintime(java.util.Date orgBegintime) {
		this.orgBegintime = orgBegintime;
	}

	public java.util.Date getOrgEndtime() {
		return orgEndtime;
	}

	public void setOrgEndtime(java.util.Date orgEndtime) {
		this.orgEndtime = orgEndtime;
	}

	public java.lang.String getOrgImgUrl() {
		return orgImgUrl;
	}

	public void setOrgImgUrl(java.lang.String orgImgUrl) {
		this.orgImgUrl = orgImgUrl;
	}

	public java.lang.String getTaxNum() {
		return taxNum;
	}

	public void setTaxNum(java.lang.String taxNum) {
		this.taxNum = taxNum;
	}

	public java.lang.String getTaxImgUrl() {
		return taxImgUrl;
	}

	public void setTaxImgUrl(java.lang.String taxImgUrl) {
		this.taxImgUrl = taxImgUrl;
	}

	public java.lang.Integer getIstaxpayer() {
		return istaxpayer;
	}

	public void setIstaxpayer(java.lang.Integer istaxpayer) {
		this.istaxpayer = istaxpayer;
	}

	public java.lang.String getIstaxpayerImgUrl() {
		return istaxpayerImgUrl;
	}

	public void setIstaxpayerImgUrl(java.lang.String istaxpayerImgUrl) {
		this.istaxpayerImgUrl = istaxpayerImgUrl;
	}

	public java.lang.Integer getCorCome() {
		return corCome;
	}

	public void setCorCome(java.lang.Integer corCome) {
		this.corCome = corCome;
	}

	public java.lang.String getCorName() {
		return corName;
	}

	public void setCorName(java.lang.String corName) {
		this.corName = corName;
	}

	public java.lang.String getCorNum() {
		return corNum;
	}

	public void setCorNum(java.lang.String corNum) {
		this.corNum = corNum;
	}

	public java.lang.String getCorImgUrl() {
		return corImgUrl;
	}

	public void setCorImgUrl(java.lang.String corImgUrl) {
		this.corImgUrl = corImgUrl;
	}

	public java.lang.String getBankPeople() {
		return bankPeople;
	}

	public void setBankPeople(java.lang.String bankPeople) {
		this.bankPeople = bankPeople;
	}

	public java.lang.String getBankId() {
		return bankId;
	}

	public void setBankId(java.lang.String bankId) {
		this.bankId = bankId;
	}

	public java.lang.String getBankState() {
		return bankState;
	}

	public void setBankState(java.lang.String bankState) {
		this.bankState = bankState;
	}

	public java.lang.String getBankCity() {
		return bankCity;
	}

	public void setBankCity(java.lang.String bankCity) {
		this.bankCity = bankCity;
	}

	public java.lang.String getBankName() {
		return bankName;
	}

	public void setBankName(java.lang.String bankName) {
		this.bankName = bankName;
	}

	public java.lang.String getBankNum() {
		return bankNum;
	}

	public void setBankNum(java.lang.String bankNum) {
		this.bankNum = bankNum;
	}

	public java.lang.String getBankImgUrl() {
		return bankImgUrl;
	}

	public void setBankImgUrl(java.lang.String bankImgUrl) {
		this.bankImgUrl = bankImgUrl;
	}

	public java.lang.String getComState() {
		return comState;
	}

	public void setComState(java.lang.String comState) {
		this.comState = comState;
	}

	public java.lang.String getComCity() {
		return comCity;
	}

	public void setComCity(java.lang.String comCity) {
		this.comCity = comCity;
	}

	public java.lang.String getComAddress() {
		return comAddress;
	}

	public void setComAddress(java.lang.String comAddress) {
		this.comAddress = comAddress;
	}

	public java.lang.String getComAdd() {
		return comAdd;
	}

	public void setComAdd(java.lang.String comAdd) {
		this.comAdd = comAdd;
	}

	public java.lang.Long getComPc() {
		return comPc;
	}

	public void setComPc(java.lang.Long comPc) {
		this.comPc = comPc;
	}

	public java.lang.String getComTel() {
		return comTel;
	}

	public void setComTel(java.lang.String comTel) {
		this.comTel = comTel;
	}

	public java.lang.String getComPortraiture() {
		return comPortraiture;
	}

	public void setComPortraiture(java.lang.String comPortraiture) {
		this.comPortraiture = comPortraiture;
	}

	public java.lang.Long getSpace() {
		return space;
	}

	public void setSpace(java.lang.Long space) {
		this.space = space;
	}

	public java.lang.String getReferee() {
		return referee;
	}

	public void setReferee(java.lang.String referee) {
		this.referee = referee;
	}

	public java.lang.String getIndustry() {
		return industry;
	}

	public void setIndustry(java.lang.String industry) {
		this.industry = industry;
	}

	public java.lang.String getComPersonnum() {
		return comPersonnum;
	}

	public void setComPersonnum(java.lang.String comPersonnum) {
		this.comPersonnum = comPersonnum;
	}

	public java.util.Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(java.util.Date creatTime) {
		this.creatTime = creatTime;
	}

	public java.lang.Long getCreatUser() {
		return creatUser;
	}

	public void setCreatUser(java.lang.Long creatUser) {
		this.creatUser = creatUser;
	}

	public java.lang.String getCreatName() {
		return creatName;
	}

	public void setCreatName(java.lang.String creatName) {
		this.creatName = creatName;
	}

	public java.lang.Integer getEnterType() {
		return enterType;
	}

	public void setEnterType(java.lang.Integer enterType) {
		this.enterType = enterType;
	}

	public java.lang.Float getCashDeposit() {
		return cashDeposit;
	}

	public void setCashDeposit(java.lang.Float cashDeposit) {
		this.cashDeposit = cashDeposit;
	}

	public java.lang.Float getPlatformUseFee() {
		return platformUseFee;
	}

	public void setPlatformUseFee(java.lang.Float platformUseFee) {
		this.platformUseFee = platformUseFee;
	}

	public java.lang.String getProtocolFile() {
		return protocolFile;
	}

	public void setProtocolFile(java.lang.String protocolFile) {
		this.protocolFile = protocolFile;
	}

	public java.lang.String getSaleDurationKey() {
		return saleDurationKey;
	}

	public void setSaleDurationKey(java.lang.String saleDurationKey) {
		this.saleDurationKey = saleDurationKey;
	}

	public java.util.Date getStartTime() {
		return startTime;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
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

	public java.util.Date getManagerChkTime() {
		return managerChkTime;
	}

	public void setManagerChkTime(java.util.Date managerChkTime) {
		this.managerChkTime = managerChkTime;
	}

	public java.lang.Long getManagerChkId() {
		return managerChkId;
	}

	public void setManagerChkId(java.lang.Long managerChkId) {
		this.managerChkId = managerChkId;
	}

	public java.lang.String getManagerChkDesc() {
		return managerChkDesc;
	}

	public void setManagerChkDesc(java.lang.String managerChkDesc) {
		this.managerChkDesc = managerChkDesc;
	}

	public java.lang.String getManagerChkImg() {
		return managerChkImg;
	}

	public void setManagerChkImg(java.lang.String managerChkImg) {
		this.managerChkImg = managerChkImg;
	}

	public java.util.Date getLawChkTime() {
		return lawChkTime;
	}

	public void setLawChkTime(java.util.Date lawChkTime) {
		this.lawChkTime = lawChkTime;
	}

	public java.lang.Long getLawChkId() {
		return lawChkId;
	}

	public void setLawChkId(java.lang.Long lawChkId) {
		this.lawChkId = lawChkId;
	}

	public java.lang.String getLawChkDesc() {
		return lawChkDesc;
	}

	public void setLawChkDesc(java.lang.String lawChkDesc) {
		this.lawChkDesc = lawChkDesc;
	}

	public java.lang.String getLawChkImg() {
		return lawChkImg;
	}

	public void setLawChkImg(java.lang.String lawChkImg) {
		this.lawChkImg = lawChkImg;
	}

	public java.util.Date getBusChkTime() {
		return busChkTime;
	}

	public void setBusChkTime(java.util.Date busChkTime) {
		this.busChkTime = busChkTime;
	}

	public java.lang.Long getBusChkId() {
		return busChkId;
	}

	public void setBusChkId(java.lang.Long busChkId) {
		this.busChkId = busChkId;
	}

	public java.lang.String getBusChkDesc() {
		return busChkDesc;
	}

	public void setBusChkDesc(java.lang.String busChkDesc) {
		this.busChkDesc = busChkDesc;
	}

	public java.lang.String getBusChkImg() {
		return busChkImg;
	}

	public void setBusChkImg(java.lang.String busChkImg) {
		this.busChkImg = busChkImg;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(java.lang.Long updateUser) {
		this.updateUser = updateUser;
	}

	public java.lang.String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(java.lang.String updateName) {
		this.updateName = updateName;
	}

	public java.lang.String getToEmail() {
		return toEmail;
	}

	public void setToEmail(java.lang.String toEmail) {
		this.toEmail = toEmail;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }
}
