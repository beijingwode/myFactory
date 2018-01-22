package com.wode.factory.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.stereotype.PrimaryKey;

@Table("t_refundorder_attachment")
public class RefundorderAttachment  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	
	
	
	//columns START
    /**
     * 仅退款凭证附件ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Column("id")
	private java.lang.Long id;
    /**
     * 退款单ID       db_column: refundOrderId  
     * 
     * 
     * @NotNull 
     */	
	@Column("refundOrderId")
	private java.lang.Long refundOrderId;
    /**
     * 仅退款凭证附件图片路径       db_column: image  
     * 
     * 
     * @NotBlank @Length(max=300)
     */	
	@Column("image")
	private java.lang.String image;
	//columns END

	public RefundorderAttachment(){
	}

	public RefundorderAttachment(
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
		
	public void setRefundOrderId(java.lang.Long value) {
		this.refundOrderId = value;
	}
	
	public java.lang.Long getRefundOrderId() {
		return this.refundOrderId;
	}
		
	public void setImage(java.lang.String value) {
		this.image = value;
	}
	
	public java.lang.String getImage() {
		return this.image;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("RefundOrderId",getRefundOrderId())
			.append("Image",getImage())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RefundorderAttachment == false) return false;
		if(this == obj) return true;
		RefundorderAttachment other = (RefundorderAttachment)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

