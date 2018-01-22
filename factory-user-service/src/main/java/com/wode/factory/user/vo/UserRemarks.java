package com.wode.factory.user.vo;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.factory.user.model.UserContacts;

public class UserRemarks extends UserContacts implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2755076817729117176L;

	public java.lang.Long getRemarkUserId() {
		return this.getContactsId();
	}
	public void setRemarkUserId(java.lang.Long remarkUserId) {
		this.setContactsId(remarkUserId);
	}
	public java.lang.String getUserNote() {
		return getContactsMemo();
	}
	public void setUserNote(java.lang.String userNote) {
		this.setContactsMemo(userNote);
	}
	public java.lang.String getExp1() {
		return this.getOptEx1();
	}
	public void setExp1(java.lang.String exp1) {
		this.setOptEx1(exp1);
	}
	public java.lang.String getExp2() {
		return this.getOptEx2();
	}
	public void setExp2(java.lang.String exp2) {
		this.setOptEx2(exp2);
	}
	public java.lang.String getExp3() {
		return this.getOptEx3();
	}
	public void setExp3(java.lang.String exp3) {
		this.setOptEx3(exp3);
	}
	public java.lang.String getRemarkOpenId() {
		return this.getContactsImId();
	}
	public void setRemarkOpenId(java.lang.String remarkOpenId) {
		this.setContactsImId(remarkOpenId);
	}
	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("UserId",getUserId())
            .append("UserNote",getUserNote())
            .append("remarkOpenId",getRemarkOpenId())
            .append("Exp1",getExp1())
            .append("Exp2",getExp2())
            .append("Exp3",getExp3())
            .toString();
    }
    
	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }
    
    
}
