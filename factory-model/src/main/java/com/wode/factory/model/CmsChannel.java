/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_cms_channel")
public class CmsChannel extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	
	//date formats
	
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
     * 栏目名称       db_column: name  
     * 
     * 
     * @NotBlank @Length(max=100)
     */	
	@Column("name")
	private java.lang.String name;
    /**
     * 模型ID       db_column: modelid  
     * 
     * 
     * 
     */	
	@Column("modelid")
	private java.lang.Integer modelid;
    /**
     * 站点ID       db_column: siteid  
     * 
     * 
     * 
     */	
	@Column("siteid")
	private java.lang.Integer siteid;
    /**
     * 是否显示。1：显示；0不显示       db_column: display  
     * 
     * 
     * @NotNull 
     */	
	@Column("display")
	private java.lang.Boolean display;
    /**
     * 1:首页 2：详情页左侧，3：前2个地方都显示       db_column: show_position  
     * 
     * 
     * @NotNull 
     */	
	@Column("show_position")
	private java.lang.Boolean showPosition;
    /**
     * 叶子       db_column: LEAF  
     * 
     * 
     * @NotNull 
     */	
	@Column("LEAF")
	private java.lang.Boolean leaf;
    /**
     * sortno       db_column: sortno  
     * 
     * 
     * 
     */	
	@Column("sortno")
	private java.lang.Integer sortno;
    /**
     * 1:是导航       db_column: nav  
     * 
     * 
     * 
     */	
	@Column("nav")
	private java.lang.Boolean nav;
    /**
     * path       db_column: path  
     * 
     * 
     * @Length(max=30)
     */	
	@Column("path")
	private java.lang.String path;
    /**
     * parentId       db_column: parent_id  
     * 
     * 
     * 
     */	
	@Column("parent_id")
	private java.lang.Long parentId;
    /**
     * status       db_column: status  
     * 
     * 
     * 
     */	
	@Column("status")
	private int status;
	//columns END
	
	
	@Many(target = CmsContent.class, field = "channelid")
	private List<CmsContent> contents;
	
	private List<CmsChannel> child;
	
	public CmsChannel(){
	}

	public CmsChannel(
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
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setModelid(java.lang.Integer value) {
		this.modelid = value;
	}
	
	public java.lang.Integer getModelid() {
		return this.modelid;
	}
	public void setSiteid(java.lang.Integer value) {
		this.siteid = value;
	}
	
	public java.lang.Integer getSiteid() {
		return this.siteid;
	}
	public void setDisplay(java.lang.Boolean value) {
		this.display = value;
	}
	
	public java.lang.Boolean getDisplay() {
		return this.display;
	}
	public void setShowPosition(java.lang.Boolean value) {
		this.showPosition = value;
	}
	
	public java.lang.Boolean getShowPosition() {
		return this.showPosition;
	}
	public void setLeaf(java.lang.Boolean value) {
		this.leaf = value;
	}
	
	public java.lang.Boolean getLeaf() {
		return this.leaf;
	}
	public void setSortno(java.lang.Integer value) {
		this.sortno = value;
	}
	
	public java.lang.Integer getSortno() {
		return this.sortno;
	}
	public void setNav(java.lang.Boolean value) {
		this.nav = value;
	}
	
	public java.lang.Boolean getNav() {
		return this.nav;
	}
	public void setPath(java.lang.String value) {
		this.path = value;
	}
	
	public java.lang.String getPath() {
		return this.path;
	}
	public void setParentId(java.lang.Long value) {
		this.parentId = value;
	}
	
	public java.lang.Long getParentId() {
		return this.parentId;
	}
	public void setStatus(int value) {
		this.status = value;
	}
	
	public int getStatus() {
		return this.status;
	}

	public List<CmsContent> getContents() {
		return contents;
	}

	public void setContents(List<CmsContent> contents) {
		this.contents = contents;
	}

	public List<CmsChannel> getChild() {
		return child;
	}

	public void setChild(List<CmsChannel> child) {
		this.child = child;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Modelid",getModelid())
			.append("Siteid",getSiteid())
			.append("Display",getDisplay())
			.append("ShowPosition",getShowPosition())
			.append("Leaf",getLeaf())
			.append("Sortno",getSortno())
			.append("Nav",getNav())
			.append("Path",getPath())
			.append("ParentId",getParentId())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CmsChannel == false) return false;
		if(this == obj) return true;
		CmsChannel other = (CmsChannel)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

