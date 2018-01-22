package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_page_setting")
public class PageSetting extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4590875739079062649L;
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
     * 模板id       db_column: template_id
     * 
     * 
     * 
     */ 
    @Column("template_id")
    private java.lang.Long templateId;
    /**
     * 模板名称       db_column: template_title
     * 
     * 
     * 
     */ 
    @Column("template_title")
    private java.lang.String templateTitle;
    /**
     * 渠道 1:pc 2:微信/app       db_column: channel
     * 
     * 
     * 
     */ 
    @Column("channel")
    private java.lang.Integer channel;
    /**
     * 标题       db_column: title
     * 
     * 
     * 
     */ 
    @Column("title")
    private java.lang.String title;
    /**
     * 关键字 统计用       db_column: page_key
     * 
     * 
     * 
     */ 
    @Column("page_key")
    private java.lang.String pageKey;
    /**
     * 商家id       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    /**
     * 商家名称       db_column: supplier_name
     * 
     * 
     * 
     */ 
    @Column("supplier_name")
    private java.lang.String supplierName;
    /**
     * 创建者id       db_column: create_user_id
     * 
     * 
     * 
     */ 
    @Column("create_user_id")
    private java.lang.Long createUserId;
    /**
     * 创建者名       db_column: create_user_name
     * 
     * 
     * 
     */ 
    @Column("create_user_name")
    private java.lang.String createUserName;
    /**
     * 备注       db_column: note
     * 
     * 
     * 
     */ 
    @Column("note")
    private java.lang.String note;
    /**
     * 创建时间       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private java.util.Date createTime;
    /**
     * 修改时间       db_column: update_time
     * 
     * 
     * 
     */ 
    @Column("update_time")
    private java.util.Date updateTime;

    /**
     * url       db_column: url
     * 
     * 
     * 
     */ 
    @Column("url")
    private java.lang.String url;
    
    /**
     * 扩展1名称       db_column: ex1_name
     * 
     */ 
    @Column("ex1_value")
    private java.lang.String ex1Value;
    
    /**
     * 扩展2名称       db_column: ex1_name
     * 
     */ 
    @Column("ex2_value")
    private java.lang.String ex2Value;
    
    /**
     * 扩展3名称       db_column: ex1_name
     * 
     */ 
    @Column("ex3_value")
    private java.lang.String ex3Value;
    
    /**
     * 扩展4名称       db_column: ex4_name
     * 
     */ 
    @Column("ex4_value")
    private java.lang.String ex4Value;
    
    //columns END

    private String ftlFile;
    
    private Integer status;//状态 0已生效1已作废
    
    
    
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("TemplateId",getTemplateId())
            .append("TemplateTitle",getTemplateTitle())
            .append("Channel",getChannel())
            .append("Title",getTitle())
            .append("PageKey",getPageKey())
            .append("SupplierId",getSupplierId())
            .append("SupplierName",getSupplierName())
            .append("CreateUserId",getCreateUserId())
            .append("CreateUserName",getCreateUserName())
            .append("Note",getNote())
            .append("CreateTime",getCreateTime())
            .append("UpdateTime",getUpdateTime())
            .append("Ex1Value",getEx1Value())
            .append("Ex2Value",getEx2Value())
            .append("Ex3Value",getEx3Value())
            .append("Ex4Value",getEx4Value())
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

	public java.lang.Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(java.lang.Long templateId) {
		this.templateId = templateId;
	}

	public java.lang.String getTemplateTitle() {
		return templateTitle;
	}

	public void setTemplateTitle(java.lang.String templateTitle) {
		this.templateTitle = templateTitle;
	}

	public java.lang.Integer getChannel() {
		return channel;
	}

	public void setChannel(java.lang.Integer channel) {
		this.channel = channel;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getPageKey() {
		return pageKey;
	}

	public void setPageKey(java.lang.String pageKey) {
		this.pageKey = pageKey;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}

	public java.lang.Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(java.lang.Long createUserId) {
		this.createUserId = createUserId;
	}

	public java.lang.String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(java.lang.String createUserName) {
		this.createUserName = createUserName;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}
	

	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFtlFile() {
		return ftlFile;
	}

	public void setFtlFile(String ftlFile) {
		this.ftlFile = ftlFile;
	}

	public java.lang.String getEx1Value() {
		return ex1Value;
	}

	public void setEx1Value(java.lang.String ex1Value) {
		this.ex1Value = ex1Value;
	}

	public java.lang.String getEx2Value() {
		return ex2Value;
	}

	public void setEx2Value(java.lang.String ex2Value) {
		this.ex2Value = ex2Value;
	}

	public java.lang.String getEx3Value() {
		return ex3Value;
	}

	public void setEx3Value(java.lang.String ex3Value) {
		this.ex3Value = ex3Value;
	}

	public java.lang.String getEx4Value() {
		return ex4Value;
	}

	public void setEx4Value(java.lang.String ex4Value) {
		this.ex4Value = ex4Value;
	}
	
}

