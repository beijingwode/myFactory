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

@Table("t_attribute_option")
public class AttributeOption extends BaseModel implements java.io.Serializable, Comparable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "AttributeOption";
	
	public static final String ALIAS_ID = "属性选项ID";
	
	public static final String ALIAS_ATTRIBUTE_ID = "属性ID";
	
	public static final String ALIAS_NAME = "名称";
	
	public static final String ALIAS_ORDERS = "orders";
	
	//date formats
	
	//columns START
    /**
     * 属性选项ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	private java.lang.Long id;
    /**
     * 属性ID       db_column: attributeId  
     * 
     * 
     * 
     */	
	@Column
	private java.lang.Long attributeId;
    /**
     * 名称       db_column: name  
     * 
     * 
     * @Length(max=50)
     */
	@Column
	private java.lang.String name;
    /**
     * orders       db_column: orders  
     * 
     * 
     * 
     */	
	@Column
	private java.lang.Integer orders;
	//columns END

	public AttributeOption(){
	}

	public AttributeOption(
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
	public void setAttributeId(java.lang.Long value) {
		this.attributeId = value;
	}
	
	public java.lang.Long getAttributeId() {
		return this.attributeId;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setOrders(java.lang.Integer value) {
		this.orders = value;
	}
	
	public java.lang.Integer getOrders() {
		return this.orders;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("AttributeId",getAttributeId())
			.append("Name",getName())
			.append("Orders",getOrders())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof AttributeOption == false) return false;
		if(this == obj) return true;
		AttributeOption other = (AttributeOption)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	

	public int compareTo(Object o) {
		if(o instanceof AttributeOption){  
			AttributeOption s=(AttributeOption)o;  
            if(this.orders>s.orders){  
                return 1;  
            }  
        }  
		return -1;  
	}
}

