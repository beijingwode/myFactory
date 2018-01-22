package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_category_week_statistical")
public class CategoryWeekStatistical extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
     * 分类id       db_column: category_id
     * 
     * 
     * 
     */ 
    @Column("category_id")
    private java.lang.Long categoryId;
    /**
     * 统计那一周的数据       db_column: day
     * 
     * 
     * 
     */ 
    @Column("day")
    private java.util.Date day;
    /**
     * 统计时间       db_column: creat_time
     * 
     * 
     * 
     */ 
    @Column("creat_time")
    private java.util.Date creatTime;
    /**
     * 商标注册号      db_column: brandNo
     * 
     * 
     * 
     */ 
    @Column("brandNo")
    private java.lang.String brandNo;
    /**
     * 品牌名称英文       db_column: nameEN
     * 
     * 
     * 
     */ 
    @Column("nameEN")
    private java.lang.String nameEN;
    /**
     * 品牌中文名称       db_column: name
     * 
     * 
     * 
     */ 
    @Column("name")
    private java.lang.String name;
    /**
     * 品牌类型       db_column: natural
     * 
     * 
     * 
     */ 
    @Column("naturall")
    private java.lang.String naturall;
    /**
     * 进口标致       db_column: import_flg
     * 
     * 
     * 
     */ 
    @Column("import_flg")
    private java.lang.String importFlg;
    /**
     * 创建时间       db_column: createDate
     * 
     * 
     * 
     */ 
    @Column("createDate")
    private java.util.Date createDate;
    /**
     * 分类级别       db_column: pbLevel
     * 
     * 
     * 
     */ 
    @Column("pbLevel")
    private java.lang.Long pbLevel;
    /**
     * 商家名称       db_column: supplierName
     * 
     * 
     * 
     */ 
    @Column("supplierName")
    private java.lang.String supplierName;
    /**
     * 企业类型       db_column: property
     * 
     * 
     * 
     */ 
    @Column("property")
    private java.lang.String property;
    /**
     * 招商经理名       db_column: managerName
     * 
     * 
     * 
     */ 
    @Column("managerName")
    private java.lang.String managerName;
    /**
     * 商品折扣       db_column: sale
     * 
     * 
     * 
     */ 
    @Column("sale")
    private java.math.BigDecimal sale;
    /**
     * 上架商品数
     */
    @Column("proCnt")
    private java.lang.Integer proCnt;
    /**
     * 商品首次上传日期      db_column: createDatef
     * 
     * 
     * 
     */ 
    @Column("createDatef")
    private java.util.Date createDatef;
    /**
     * 使用标志 0：表示停用1：表示使用 db_column:use_flg
     */
    @Column("use_flg")
    private java.lang.Integer useFlg;
    /**
     * 扩展属性       db_column: exp1
     * 
     * 
     * 
     */ 
    @Column("exp1")
    private java.lang.String exp1;
    /**
     * 扩展属性2       db_column: exp2
     * 
     * 
     * 
     */ 
    @Column("exp2")
    private java.lang.String exp2;
    
    //columns END


	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("CategoryId",getCategoryId())
            .append("Day",getDay())
            .append("CreatTime",getCreatTime())
            .append("BrandNo",getBrandNo())
            .append("NameEN",getNameEN())
            .append("Name",getName())
            .append("Naturall",getNaturall())
            .append("ImportFlg",getImportFlg())
            .append("CreateDate",getCreateDate())
            .append("PbLevel",getPbLevel())
            .append("SupplierName",getSupplierName())
            .append("Property",getProperty())
            .append("ManagerName",getManagerName())
            .append("Sale",getSale())
            .append("ProCnt" , getProCnt())
            .append("CreateDatef",getCreateDatef())
            .append("UseFlg",getUseFlg())
            .append("Exp1",getExp1())
            .append("Exp2",getExp2())
            .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.util.Date getDay() {
		return day;
	}
	
	public java.lang.Integer getProCnt() {
		return proCnt;
	}

	public void setProCnt(java.lang.Integer proCnt) {
		this.proCnt = proCnt;
	}

	public void setDay(java.util.Date day) {
		this.day = day;
	}

	public java.util.Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(java.util.Date creatTime) {
		this.creatTime = creatTime;
	}

	public java.lang.Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(java.lang.Long categoryId) {
		this.categoryId = categoryId;
	}

	public java.lang.String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(java.lang.String brandNo) {
		this.brandNo = brandNo;
	}

	public java.lang.String getNameEN() {
		return nameEN;
	}

	public void setNameEN(java.lang.String nameEN) {
		this.nameEN = nameEN;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getNaturall() {
		return naturall;
	}

	public void setNatural(java.lang.String naturall) {
		this.naturall = naturall;
	}

	public java.lang.String getImportFlg() {
		return importFlg;
	}

	public void setImportFlg(java.lang.String importFlg) {
		this.importFlg = importFlg;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.Long getPbLevel() {
		return pbLevel;
	}

	public void setPbLevel(java.lang.Long pbLevel) {
		this.pbLevel = pbLevel;
	}

	public java.lang.String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}

	public java.lang.String getProperty() {
		return property;
	}

	public void setProperty(java.lang.String property) {
		this.property = property;
	}

	public java.lang.String getManagerName() {
		return managerName;
	}

	public void setManagerName(java.lang.String managerName) {
		this.managerName = managerName;
	}

	public java.math.BigDecimal getSale() {
		return sale;
	}

	public void setSale(java.math.BigDecimal sale) {
		this.sale = sale;
	}

	public java.util.Date getCreateDatef() {
		return createDatef;
	}

	public void setCreateDatef(java.util.Date createDatef) {
		this.createDatef = createDatef;
	}

	public java.lang.String getExp1() {
		return exp1;
	}

	public void setExp1(java.lang.String exp1) {
		this.exp1 = exp1;
	}

	public java.lang.String getExp2() {
		return exp2;
	}

	public void setExp2(java.lang.String exp2) {
		this.exp2 = exp2;
	}

	public java.lang.Integer getUseFlg() {
		return useFlg;
	}

	public void setUseFlg(java.lang.Integer useFlg) {
		this.useFlg = useFlg;
	}
	
	

}

