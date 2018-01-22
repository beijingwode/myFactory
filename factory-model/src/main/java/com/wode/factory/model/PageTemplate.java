package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_page_template")
public class PageTemplate extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6622829151850231587L;
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
     * 模板文件       db_column: ftl_file
     * 
     * 
     * 
     */ 
    @Column("ftl_file")
    private java.lang.String ftlFile;
    /**
     * 生成路径       db_column: html_path
     * 
     * 
     * 
     */ 
    @Column("html_path")
    private java.lang.String htmlPath;
    /**
     * 备注       db_column: note
     * 
     * 
     * 
     */ 
    @Column("note")
    private java.lang.String note;
    /**
     * 快照 设计稿       db_column: snapshot
     * 
     * 
     * 
     */ 
    @Column("snapshot")
    private java.lang.String snapshot;
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
     * 扩展4名称       db_column: ex4_name
     * 
     * 
     * 
     */ 
    @Column("ex4_name")
    private java.lang.String ex4Name;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Channel",getChannel())
            .append("Title",getTitle())
            .append("FtlFile",getFtlFile())
            .append("HtmlPath",getHtmlPath())
            .append("Note",getNote())
            .append("Snapshot",getSnapshot())
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

	public java.lang.String getFtlFile() {
		return ftlFile;
	}

	public void setFtlFile(java.lang.String ftlFile) {
		this.ftlFile = ftlFile;
	}

	public java.lang.String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(java.lang.String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(java.lang.String snapshot) {
		this.snapshot = snapshot;
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

	public java.lang.String getEx4Name() {
		return ex4Name;
	}

	public void setEx4Name(java.lang.String ex4Name) {
		this.ex4Name = ex4Name;
	}

}

