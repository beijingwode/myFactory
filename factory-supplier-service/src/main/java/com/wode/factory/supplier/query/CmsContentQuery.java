/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class CmsContentQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** 栏目ID */
	private java.lang.Long channelid;
	/** 用户ID */
	private java.lang.String creatorid;
	/** 属性ID */
	private java.lang.Integer typeid;
	/** 创建日期 */
	private java.util.Date creatdateBegin;
	private java.util.Date creatdateEnd;
	/** 标题 */
	private java.lang.String title;
	/** 来源 */
	private java.lang.String source;
	/** 作者 */
	private java.lang.String author;
	/** 内容 */
	private java.lang.String txt;
	/** 固顶级别 */
	private Integer topLevel;
	/** 是否有标题图 */
	private java.lang.String image;
	/** 是否推荐 */
	private java.lang.Boolean isRecommend;
	/** 0:不允许评论；1：登陆可评论；2：任何用户可评论 */
	private java.lang.Boolean allowcomment;
	/** 状态(0:草稿;1:审核中;2:审核通过;3:审核不通过;4:回收站) */
	private Integer status;
	/** 访问数 */
	private java.lang.Integer views;
	/** 踩数 */
	private java.lang.Integer downs;
	/** 顶数 */
	private java.lang.Integer ups;
	/** ranking */
	private java.lang.Integer ranking;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getChannelid() {
		return this.channelid;
	}
	
	public void setChannelid(java.lang.Long value) {
		this.channelid = value;
	}
	
	public java.lang.String getCreatorid() {
		return this.creatorid;
	}
	
	public void setCreatorid(java.lang.String value) {
		this.creatorid = value;
	}
	
	public java.lang.Integer getTypeid() {
		return this.typeid;
	}
	
	public void setTypeid(java.lang.Integer value) {
		this.typeid = value;
	}
	
	public java.util.Date getCreatdateBegin() {
		return this.creatdateBegin;
	}
	
	public void setCreatdateBegin(java.util.Date value) {
		this.creatdateBegin = value;
	}	
	
	public java.util.Date getCreatdateEnd() {
		return this.creatdateEnd;
	}
	
	public void setCreatdateEnd(java.util.Date value) {
		this.creatdateEnd = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getSource() {
		return this.source;
	}
	
	public void setSource(java.lang.String value) {
		this.source = value;
	}
	
	public java.lang.String getAuthor() {
		return this.author;
	}
	
	public void setAuthor(java.lang.String value) {
		this.author = value;
	}
	
	public java.lang.String getTxt() {
		return this.txt;
	}
	
	public void setTxt(java.lang.String value) {
		this.txt = value;
	}
	
	public Integer getTopLevel() {
		return this.topLevel;
	}
	
	public void setTopLevel(Integer value) {
		this.topLevel = value;
	}
	
	public java.lang.String getImage() {
		return this.image;
	}
	
	public void setImage(java.lang.String value) {
		this.image = value;
	}
	
	public java.lang.Boolean getIsRecommend() {
		return this.isRecommend;
	}
	
	public void setIsRecommend(java.lang.Boolean value) {
		this.isRecommend = value;
	}
	
	public java.lang.Boolean getAllowcomment() {
		return this.allowcomment;
	}
	
	public void setAllowcomment(java.lang.Boolean value) {
		this.allowcomment = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getViews() {
		return this.views;
	}
	
	public void setViews(java.lang.Integer value) {
		this.views = value;
	}
	
	public java.lang.Integer getDowns() {
		return this.downs;
	}
	
	public void setDowns(java.lang.Integer value) {
		this.downs = value;
	}
	
	public java.lang.Integer getUps() {
		return this.ups;
	}
	
	public void setUps(java.lang.Integer value) {
		this.ups = value;
	}
	
	public java.lang.Integer getRanking() {
		return this.ranking;
	}
	
	public void setRanking(java.lang.Integer value) {
		this.ranking = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

