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

@Table("t_specification_value")
public class SpecificationValue extends BaseModel implements java.io.Serializable,Comparable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SpecificationValue";
	
	public static final String ALIAS_ID = "规格值ID";
	
	public static final String ALIAS_SPECIFICATION_ID = "规格ID";
	
	public static final String ALIAS_NAME = "名称";
	
	public static final String ALIAS_IMAGE = "图片路径";
	
	public static final String ALIAS_ORDERS = "排序";
	
	//date formats
	
	//columns START
    /**
     * 规格值ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	private java.lang.Long id;
    /**
     * 规格ID       db_column: specification_id  
     * 
     * 
     * 
     */	
	@Column("specification_id")
	private java.lang.Long specificationId;
    /**
     * 名称       db_column: name  
     * 
     * 
     * @Length(max=50)
     */	
	@Column
	private java.lang.String name;
    /**
     * 图片路径       db_column: image  
     * 
     * 
     * @Length(max=100)
     */	
	@Column
	private java.lang.String image;
    /**
     * 排序       db_column: orders  
     * 
     * 
     * @Max(127)
     */	
	@Column
	private Integer orders;
	//columns END
	
	private Integer usedCont;

	public SpecificationValue(){
	}

	public SpecificationValue(
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
	public void setSpecificationId(java.lang.Long value) {
		this.specificationId = value;
	}
	
	public java.lang.Long getSpecificationId() {
		return this.specificationId;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setImage(java.lang.String value) {
		this.image = value;
	}
	
	public java.lang.String getImage() {
		return this.image;
	}
	public void setOrders(Integer value) {
		this.orders = value;
	}
	
	public Integer getOrders() {
		return this.orders;
	}

	public Integer getUsedCont() {
		return usedCont;
	}

	public void setUsedCont(Integer usedCont) {
		this.usedCont = usedCont;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("SpecificationId",getSpecificationId())
			.append("Name",getName())
			.append("Image",getImage())
			.append("Orders",getOrders())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SpecificationValue == false) return false;
		if(this == obj) return true;
		SpecificationValue other = (SpecificationValue)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof SpecificationValue){  
			SpecificationValue s=(SpecificationValue)o;  
            if(this.orders>s.orders){  
                return 1;  
            }  
        }  
		return -1;  
	}
}

