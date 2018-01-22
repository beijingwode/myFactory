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
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
import com.wode.factory.annotation.DefaultValue;

@Table("t_cms_content")
public class CmsContent extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	//date formats
	public static final String FORMAT_CREATDATE = DATE_TIME_FORMAT;
	
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
     * 栏目ID       db_column: channelid  
     * 
     * 
     * @NotNull 
     */	
	@Column("channelid")
	private java.lang.Long channelid;
    /**
     * 用户ID       db_column: creatorid  
     * 
     * 
     * @NotBlank @Length(max=100)
     */	
	@Column("creatorid")
	@DefaultValue(StringValue="inneruserid()")
	private java.lang.String creatorid;
    /**
     * 属性ID       db_column: typeid  
     * 
     * 
     * @NotNull 
     */	
	@Column("typeid")
	private java.lang.Integer typeid;
    /**
     * 创建日期       db_column: creatdate  
     * 
     * 
     * @NotNull 
     */	
	@Column("creatdate")
	@DefaultValue(dateValue="now()")
	private java.util.Date creatdate;
    /**
     * 标题       db_column: title  
     * 
     * 
     * @NotBlank @Length(max=50)
     */	
	@Column("title")
	private java.lang.String title;
    /**
     * 来源       db_column: source  
     * 
     * 
     * @Length(max=200)
     */	
	@Column("source")
	private java.lang.String source;
    /**
     * 作者       db_column: author  
     * 
     * 
     * @Length(max=20)
     */	
	@Column("author")
	private java.lang.String author;
    /**
     * 内容       db_column: txt  
     * 
     * 
     * @NotBlank @Length(max=2147483647)
     */	
	@Column("txt")
	private java.lang.String txt;
    /**
     * 固顶级别       db_column: topLevel  
     * 
     * 
     * @NotNull @Max(127)
     */	
	@Column("topLevel")
	private Integer topLevel;
    /**
     * 是否有标题图       db_column: image  
     * 
     * 
     * @Length(max=200)
     */	
	@Column("image")
	private java.lang.String image;
    /**
     * 是否推荐       db_column: isRecommend  
     * 
     * 
     * @NotNull 
     */	
	@Column("isRecommend")
	private java.lang.Boolean isRecommend;
    /**
     * 0:不允许评论；1：登陆可评论；2：任何用户可评论       db_column: allowcomment  
     * 
     * 
     * 
     */	
	@Column("allowcomment")
	private java.lang.Boolean allowcomment;
    /**
     * 状态(0:草稿;1:审核中;2:审核通过;3:审核不通过;4:回收站)       db_column: status  
     * 
     * 
     * @NotNull @Max(127)
     */	
	@Column("status")
	private Integer status;
    /**
     * 访问数       db_column: views  
     * 
     * 
     * @NotNull 
     */	
	@Column("views")
	private java.lang.Integer views;
    /**
     * 踩数       db_column: downs  
     * 
     * 
     * @NotNull 
     */	
	@Column("downs")
	private java.lang.Integer downs;
    /**
     * 顶数       db_column: ups  
     * 
     * 
     * @NotNull 
     */	
	@Column("ups")
	private java.lang.Integer ups;
    /**
     * ranking       db_column: ranking  
     * 
     * 
     * @NotNull 
     */	
	@Column("ranking")
	private java.lang.Integer ranking;
	//columns END

	
	@One(target=CmsChannel.class, field ="channelid")
	private CmsChannel channel;
	
	
	
	public CmsContent(){
	}

	public CmsContent(
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
	public void setChannelid(java.lang.Long value) {
		this.channelid = value;
	}
	
	public java.lang.Long getChannelid() {
		return this.channelid;
	}
	public void setCreatorid(java.lang.String value) {
		this.creatorid = value;
	}
	
	public java.lang.String getCreatorid() {
		return this.creatorid;
	}
	public void setTypeid(java.lang.Integer value) {
		this.typeid = value;
	}
	
	public java.lang.Integer getTypeid() {
		return this.typeid;
	}
	public String getCreatdateString() {
		return DateConvertUtils.format(getCreatdate(), FORMAT_CREATDATE);
	}
	public void setCreatdateString(String value) {
		setCreatdate(DateConvertUtils.parse(value, FORMAT_CREATDATE,java.util.Date.class));
	}
	
	public void setCreatdate(java.util.Date value) {
		this.creatdate = value;
	}
	
	public java.util.Date getCreatdate() {
		return this.creatdate;
	}
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setSource(java.lang.String value) {
		this.source = value;
	}
	
	public java.lang.String getSource() {
		return this.source;
	}
	public void setAuthor(java.lang.String value) {
		this.author = value;
	}
	
	public java.lang.String getAuthor() {
		return this.author;
	}
	public void setTxt(java.lang.String value) {
		this.txt = value;
	}
	
	public java.lang.String getTxt() {
		return this.txt;
	}
	public void setTopLevel(Integer value) {
		this.topLevel = value;
	}
	
	public Integer getTopLevel() {
		return this.topLevel;
	}
	public void setImage(java.lang.String value) {
		this.image = value;
	}
	
	public java.lang.String getImage() {
		return this.image;
	}
	public void setIsRecommend(java.lang.Boolean value) {
		this.isRecommend = value;
	}
	
	public java.lang.Boolean getIsRecommend() {
		return this.isRecommend;
	}
	public void setAllowcomment(java.lang.Boolean value) {
		this.allowcomment = value;
	}
	
	public java.lang.Boolean getAllowcomment() {
		return this.allowcomment;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setViews(java.lang.Integer value) {
		this.views = value;
	}
	
	public java.lang.Integer getViews() {
		return this.views;
	}
	public void setDowns(java.lang.Integer value) {
		this.downs = value;
	}
	
	public java.lang.Integer getDowns() {
		return this.downs;
	}
	public void setUps(java.lang.Integer value) {
		this.ups = value;
	}
	
	public java.lang.Integer getUps() {
		return this.ups;
	}
	public void setRanking(java.lang.Integer value) {
		this.ranking = value;
	}
	
	public java.lang.Integer getRanking() {
		return this.ranking;
	}

	public CmsChannel getChannel() {
		return channel;
	}

	public void setChannel(CmsChannel channel) {
		this.channel = channel;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Channelid",getChannelid())
			.append("Creatorid",getCreatorid())
			.append("Typeid",getTypeid())
			.append("Creatdate",getCreatdate())
			.append("Title",getTitle())
			.append("Source",getSource())
			.append("Author",getAuthor())
			.append("Txt",getTxt())
			.append("TopLevel",getTopLevel())
			.append("Image",getImage())
			.append("IsRecommend",getIsRecommend())
			.append("Allowcomment",getAllowcomment())
			.append("Status",getStatus())
			.append("Views",getViews())
			.append("Downs",getDowns())
			.append("Ups",getUps())
			.append("Ranking",getRanking())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CmsContent == false) return false;
		if(this == obj) return true;
		CmsContent other = (CmsContent)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

