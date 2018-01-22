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
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_search_rankings")
public class SearchRankings extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SearchRankings";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_KEYWORD = "keyword";
	
	public static final String ALIAS_LINK = "link";
	
	public static final String ALIAS_ORDER_NO = "order_no";
	
	//date formats
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id(auto=false)
	private java.lang.Long id;
    /**
     * keyword       db_column: keyword  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("keyword")
	private java.lang.String keyword;
    /**
     * link       db_column: link  
     * 
     * 
     * @Length(max=100)
     */	
	@Column("link")
	private java.lang.String link;
    /**
     * order_no       db_column: order_no  
     * 
     * 
     * 
     */	
	@Column("order_no")
	private java.lang.Integer orderNo;
	
	
	@Column("css_class")
	private String cssClass;
	//columns END

	public SearchRankings(){
	}

	public SearchRankings(
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
	public void setKeyword(java.lang.String value) {
		this.keyword = value;
	}
	
	public java.lang.String getKeyword() {
		return this.keyword;
	}
	public void setLink(java.lang.String value) {
		this.link = value;
	}
	
	public java.lang.String getLink() {
		return this.link;
	}
	public void setOrderNo(java.lang.Integer value) {
		this.orderNo = value;
	}
	
	public java.lang.Integer getOrderNo() {
		return this.orderNo;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Keyword",getKeyword())
			.append("Link",getLink())
			.append("OrderNo",getOrderNo())
			.append("cssClass",getCssClass())
			.toString();
	}
	
	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SearchRankings == false) return false;
		if(this == obj) return true;
		SearchRankings other = (SearchRankings)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

