package com.wode.factory.model;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_product_questionnaire")
public class ProductQuestionnaire extends BaseModel implements java.io.Serializable{

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
     * 商家id       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    /**
     * 商品id       db_column: product_id
     * 
     * 
     * 
     */ 
    @Column("product_id")
    private java.lang.Long productId;
    /**
     * 商品名称       db_column: product_name
     * 
     * 
     * 
     */ 
    @Column("product_name")
    private java.lang.String productName;
    /**
     * 商品主图       db_column: product_img
     * 
     * 
     * 
     */ 
    @Column("product_img")
    private java.lang.String productImg;
    /**
     * 最低价格       db_column: minprice
     * 
     * 
     * 
     */ 
    @Column("minprice")
    private java.math.BigDecimal minprice;
    /**
     * 最高价格       db_column: maxprice
     * 
     * 
     * 
     */ 
    @Column("maxprice")
    private java.math.BigDecimal maxprice;
    /**
     * 试用返现       db_column: emp_price
     * 
     * 
     * 
     */ 
    @Column("emp_price")
    private java.math.BigDecimal empPrice;
    /**
     * 商品数量       db_column: product_cnt
     * 
     * 
     * 
     */ 
    @Column("product_cnt")
    private java.lang.Integer productCnt;
    /**
     * 模板名称       db_column: template_title
     * 
     * 
     * 
     */ 
    @Column("template_title")
    private java.lang.String templateTitle;
    /**
     * 文本输入       db_column: test_flg
     * 
     * 
     * 
     */ 
    @Column("test_flg")
    private java.lang.Integer testFlg;
    /**
     * 创建时间       db_column: create_date
     * 
     * 
     * 
     */ 
    @Column("create_date")
    private java.util.Date createDate;
    /**
     * 创建者       db_column: create_user
     * 
     * 
     * 
     */ 
    @Column("create_user")
    private java.lang.Long createUser;
    /**
     * 状态 1:待审核/2:进行中/3:已结束/-1:删除       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    /**
     * 结束日期       db_column: end_date
     * 
     * 
     * 
     */ 
    @Column("end_date")
    private java.util.Date endDate;
    /**
     * 回答次数       db_column: answer_cnt
     * 
     * 
     * 
     */ 
    @Column("answer_cnt")
    private java.lang.Integer answerCnt;
    /**
     * 模板id       db_column: template_id
     * 
     * 
     * 
     */ 
    @Column("template_id")
    private java.lang.Long templateId;

    //columns END
    private List<QuestionnaireQuestion> listQuestion;

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("SupplierId",getSupplierId())
            .append("ProductId",getProductId())
            .append("ProductName",getProductName())
            .append("ProductImg",getProductImg())
            .append("Minprice",getMinprice())
            .append("Maxprice",getMaxprice())
            .append("EmpPrice",getEmpPrice())
            .append("ProductCnt",getProductCnt())
            .append("TemplateTitle",getTemplateTitle())
            .append("TestFlg",getTestFlg())
            .append("CreateDate",getCreateDate())
            .append("CreateUser",getCreateUser())
            .append("Status",getStatus())
            .append("EndDate",getEndDate())
            .append("AnswerCnt",getAnswerCnt())
            .append("TemplateId",getTemplateId())
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

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.Long getProductId() {
		return productId;
	}

	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public java.lang.String getProductImg() {
		return productImg;
	}

	public void setProductImg(java.lang.String productImg) {
		this.productImg = productImg;
	}

	public java.math.BigDecimal getMinprice() {
		return minprice;
	}

	public void setMinprice(java.math.BigDecimal minprice) {
		this.minprice = minprice;
	}

	public java.math.BigDecimal getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(java.math.BigDecimal maxprice) {
		this.maxprice = maxprice;
	}

	public java.math.BigDecimal getEmpPrice() {
		return empPrice;
	}

	public void setEmpPrice(java.math.BigDecimal empPrice) {
		this.empPrice = empPrice;
	}

	public java.lang.Integer getProductCnt() {
		return productCnt;
	}

	public void setProductCnt(java.lang.Integer productCnt) {
		this.productCnt = productCnt;
	}

	public java.lang.String getTemplateTitle() {
		return templateTitle;
	}

	public void setTemplateTitle(java.lang.String templateTitle) {
		this.templateTitle = templateTitle;
	}

	public java.lang.Integer getTestFlg() {
		return testFlg;
	}

	public void setTestFlg(java.lang.Integer testFlg) {
		this.testFlg = testFlg;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(java.lang.Long createUser) {
		this.createUser = createUser;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public java.lang.Integer getAnswerCnt() {
		return answerCnt;
	}

	public void setAnswerCnt(java.lang.Integer answerCnt) {
		this.answerCnt = answerCnt;
	}

	public java.lang.Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(java.lang.Long templateId) {
		this.templateId = templateId;
	}

	public List<QuestionnaireQuestion> getListQuestion() {
		if(listQuestion==null) {
			listQuestion = new ArrayList<QuestionnaireQuestion>();
		}
		return listQuestion;
	}

	public void setListQuestion(List<QuestionnaireQuestion> listQuestion) {
		this.listQuestion = listQuestion;
	}

}

