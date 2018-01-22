/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class BusinessCircle extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "BusinessCircle";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_AID = "aid";
	
	public static final String ALIAS_NAME = "name";
	
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
	private java.lang.Long id;
    /**
     * aid       db_column: aid  
     * 
     * 
     * 
     */	
	private java.lang.Long aid;
    /**
     * name       db_column: name  
     * 
     * 
     * @Length(max=10)
     */	
	private java.lang.String name;
    /**
     * order_no       db_column: order_no  
     * 
     * 
     * @Length(max=10)
     */	
	private java.lang.String orderNo;
	//columns END

	public BusinessCircle(){
	}

	public BusinessCircle(
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
	public void setAid(java.lang.Long value) {
		this.aid = value;
	}
	
	public java.lang.Long getAid() {
		return this.aid;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}
	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Aid",getAid())
			.append("Name",getName())
			.append("OrderNo",getOrderNo())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BusinessCircle == false) return false;
		if(this == obj) return true;
		BusinessCircle other = (BusinessCircle)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

