package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.stereotype.PrimaryKey;

@Table("m_sale_duration_param")
public class SaleDurationParam  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	
	
	
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
     * 关键字       db_column: key  
     * 
     * 
     * @NotBlank @Length(max=3)
     */	
	@Column("key")
	private java.lang.String key;
    /**
     * 显示内容       db_column: caption  
     * 
     * 
     * @NotBlank @Length(max=20)
     */	
	@Column("caption")
	private java.lang.String caption;
    /**
     * 表示顺序       db_column: disp_order  
     * 
     * 
     * 
     */	
	@Column("disp_order")
	private java.lang.Integer dispOrder;
    /**
     * 值 暂时为空       db_column: value  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("value")
	private java.lang.String value;
    /**
     * 说明       db_column: descr  
     * 
     * 
     * @Length(max=255)
     */	
	@Column("descr")
	private java.lang.String descr;
    /**
     * 停用标识 1:停用/0:正常       db_column: stop_flg  
     * 
     * 
     * @Length(max=1)
     */	
	@Column("stop_flg")
	private java.lang.String stopFlg;
    /**
     * 更新时间       db_column: update_time  
     * 
     * 
     * 
     */	
	@Column("update_time")
	private java.util.Date updateTime;
	//columns END

	public SaleDurationParam(){
	}

	public SaleDurationParam(
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
		
	public void setKey(java.lang.String value) {
		this.key = value;
	}
	
	public java.lang.String getKey() {
		return this.key;
	}
		
	public void setCaption(java.lang.String value) {
		this.caption = value;
	}
	
	public java.lang.String getCaption() {
		return this.caption;
	}
		
	public void setDispOrder(java.lang.Integer value) {
		this.dispOrder = value;
	}
	
	public java.lang.Integer getDispOrder() {
		return this.dispOrder;
	}
		
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	
	public java.lang.String getValue() {
		return this.value;
	}
		
	public void setDescr(java.lang.String value) {
		this.descr = value;
	}
	
	public java.lang.String getDescr() {
		return this.descr;
	}
		
	public void setStopFlg(java.lang.String value) {
		this.stopFlg = value;
	}
	
	public java.lang.String getStopFlg() {
		return this.stopFlg;
	}
		
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Key",getKey())
			.append("Caption",getCaption())
			.append("DispOrder",getDispOrder())
			.append("Value",getValue())
			.append("Descr",getDescr())
			.append("StopFlg",getStopFlg())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SaleDurationParam == false) return false;
		if(this == obj) return true;
		SaleDurationParam other = (SaleDurationParam)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

