package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_page_template_section")
public class PageTemplateSection extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5269171090054026384L;
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
     * 渠道 1:pc 2:微信/app 同模板       db_column: channel
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
     * 名称       db_column: name
     * 
     * 
     * 
     */ 
    @Column("name")
    private java.lang.String name;
    /**
     * 排序       db_column: orders
     * 
     * 
     * 
     */ 
    @Column("orders")
    private java.lang.Integer orders;
    /**
     * 最大数量       db_column: max_num
     * 
     * 
     * 
     */ 
    @Column("max_num")
    private java.lang.Integer maxNum;
    /**
     * 扩展1名称       db_column: ex1_name
     * 
     * 
     * 
     */ 
    @Column("ex1_name")
    private java.lang.String ex1Name;
    /**
     * 扩展2名称       db_column: ex2_name
     * 
     * 
     * 
     */ 
    @Column("ex2_name")
    private java.lang.String ex2Name;
    /**
     * 扩展3名称       db_column: ex3_name
     * 
     * 
     * 
     */ 
    @Column("ex3_name")
    private java.lang.String ex3Name;
    /**
     * 扩展3名称       db_column: ex3_name
     * 
     * 
     * 
     */ 
    @Column("ex4_name")
    private java.lang.String ex4Name;
    /**
     * 扩展3名称       db_column: ex3_name
     * 
     * 
     * 
     */ 
    @Column("ex5_name")
    private java.lang.String ex5Name;
    /**
     * 扩展3名称       db_column: ex3_name
     * 
     * 
     * 
     */ 
    @Column("ex6_name")
    private java.lang.String ex6Name;
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
     * 副标题1名称       db_column: title1_name
     * 
     * 
     * 
     */ 
    @Column("title1_name")
    private java.lang.String title1Name;
    /**
     * 副标题2名称       db_column: title2_name
     * 
     * 
     * 
     */ 
    @Column("title2_name")
    private java.lang.String title2Name;

    //columns END
    
    private String templateTitle;
    
    public String getTemplateTitle() {
		return templateTitle;
	}

	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("TemplateId",getTemplateId())
            .append("Channel",getChannel())
            .append("Title",getTitle())
            .append("Name",getName())
            .append("Orders",getOrders())
            .append("MaxNum",getMaxNum())
            .append("Ex1Name",getEx1Name())
            .append("Ex2Name",getEx2Name())
            .append("Ex3Name",getEx3Name())
            .append("Ex4Name",getEx4Name())
            .append("Ex5Name",getEx5Name())
            .append("Ex6Name",getEx6Name())
            .append("CreateTime",getCreateTime())
            .append("UpdateTime",getUpdateTime())
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

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.Integer getOrders() {
		return orders;
	}

	public void setOrders(java.lang.Integer orders) {
		this.orders = orders;
	}

	public java.lang.Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(java.lang.Integer maxNum) {
		this.maxNum = maxNum;
	}

	public java.lang.String getEx1Name() {
		return ex1Name;
	}

	public void setEx1Name(java.lang.String ex1Name) {
		this.ex1Name = ex1Name;
	}

	public java.lang.String getEx2Name() {
		return ex2Name;
	}

	public void setEx2Name(java.lang.String ex2Name) {
		this.ex2Name = ex2Name;
	}

	public java.lang.String getEx3Name() {
		return ex3Name;
	}

	public void setEx3Name(java.lang.String ex3Name) {
		this.ex3Name = ex3Name;
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

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.String getEx4Name() {
		return ex4Name;
	}

	public void setEx4Name(java.lang.String ex4Name) {
		this.ex4Name = ex4Name;
	}

	public java.lang.String getEx5Name() {
		return ex5Name;
	}

	public void setEx5Name(java.lang.String ex5Name) {
		this.ex5Name = ex5Name;
	}

	public java.lang.String getEx6Name() {
		return ex6Name;
	}

	public void setEx6Name(java.lang.String ex6Name) {
		this.ex6Name = ex6Name;
	}

	public java.lang.String getTitle1Name() {
		return title1Name;
	}

	public void setTitle1Name(java.lang.String title1Name) {
		this.title1Name = title1Name;
	}

	public java.lang.String getTitle2Name() {
		return title2Name;
	}

	public void setTitle2Name(java.lang.String title2Name) {
		this.title2Name = title2Name;
	}
	
}

