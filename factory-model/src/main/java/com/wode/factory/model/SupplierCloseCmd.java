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

@Table("t_supplier_close_cmd")
public class SupplierCloseCmd extends BaseModel implements java.io.Serializable{
	//private static final long serialVersionUID = 5454155825314635342L;
	
	
	
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
     * 供应商id       db_column: supplier_id  
     * 
     * 
     * 
     */
	@Column("supplier_id")
	private java.lang.Long supplierId;
    /**
     * 执行日期       db_column: exec_date  
     * 
     *
     */
	@Column("exec_date")
	private java.util.Date execDate;
    /**
     * 账期开始时刻       db_column: close_start  
     * 
     * 
     */
	@Column("close_start")
	private java.util.Date closeStart;
    /**
     * 账期结算时刻       db_column: close_end  
     * 
     * 
     */
	@Column("close_end")
	private java.util.Date closeEnd;
    /**
     * 执行状态       db_column: exec_status  
     * 
     */
	@Column("exec_status")
	private java.lang.String execStatus;
    /**
     * 执行结果       db_column: exec_result  
     * 
     * 
     */
	@Column("exec_result")
	private java.lang.String execResult;
    /**
     * 错误信息      db_column: err_msg  
     * 
     * 
     */
	@Column("err_msg")
	private java.lang.String errMsg;
    /**
     * 结算单ID      db_column: sale_bill_id  
     * 
     * 
     */
	@Column("sale_bill_id")
	private java.lang.Long saleBillId;
    /**
     * 创建时间       db_column: create_time  
     * 
     * 
     * 
     */
	@Column("create_time")
	private java.util.Date createTime;
    /**
     * 创建者       db_column: create_user  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("create_user")
	private java.lang.String createUser;
    /**
     * 更新时间       db_column: update_time  
     * 
     * 
     * 
     */
	@Column("update_time")
	private java.util.Date updateTime;
    /**
     * 修改者       db_column: update_user  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("update_user")
	private java.lang.String updateUser;
	//columns END
	
	/**
	 * 扩展属性 企业id
	 */
	private java.lang.Long enterpriseId;
	
	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.util.Date getExecDate() {
		return execDate;
	}

	public void setExecDate(java.util.Date execDate) {
		this.execDate = execDate;
	}

	public java.util.Date getCloseStart() {
		return closeStart;
	}

	public void setCloseStart(java.util.Date closeStart) {
		this.closeStart = closeStart;
	}

	public java.util.Date getCloseEnd() {
		return closeEnd;
	}

	public void setCloseEnd(java.util.Date closeEnd) {
		this.closeEnd = closeEnd;
	}

	public java.lang.String getExecStatus() {
		return execStatus;
	}

	public void setExecStatus(java.lang.String execStatus) {
		this.execStatus = execStatus;
	}

	public java.lang.String getExecResult() {
		return execResult;
	}

	public void setExecResult(java.lang.String execResult) {
		this.execResult = execResult;
	}

	public java.lang.String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(java.lang.String errMsg) {
		this.errMsg = errMsg;
	}

	public java.lang.Long getSaleBillId() {
		return saleBillId;
	}

	public void setSaleBillId(java.lang.Long saleBillId) {
		this.saleBillId = saleBillId;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(java.lang.String createUser) {
		this.createUser = createUser;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(java.lang.String updateUser) {
		this.updateUser = updateUser;
	}

	public java.lang.Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(java.lang.Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("SupplierId",getSupplierId())
			.append("ExecDate",getExecDate())
			.append("CloseStart",getCloseStart())
			.append("CloseEnd",getCloseEnd())
			.append("ExecStatus",getExecStatus())
			.append("ExecResult",getExecResult())
			.append("ErrMsg",getErrMsg())
			.append("SaleBillId",getSaleBillId())
			.append("CreateTime",getCreateTime())
			.append("CreateUser",getCreateUser())
			.append("UpdateTime",getUpdateTime())
			.append("UpdateUser",getUpdateUser())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SupplierCloseCmd == false) return false;
		if(this == obj) return true;
		SupplierCloseCmd other = (SupplierCloseCmd)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

