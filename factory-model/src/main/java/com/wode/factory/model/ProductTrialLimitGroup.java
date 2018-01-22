package com.wode.factory.model;


import java.util.Date;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_product_trial_limit_group")
public class ProductTrialLimitGroup extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6824021652652530413L;
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
     * 限购组名称       db_column: name
     * 
     * 
     * 
     */ 
    @Column("name")
    private java.lang.String name;
    /**
     * 备注       db_column: remark
     * 
     * 
     * 
     */ 
    @Column("remark")
    private java.lang.String remark;
    /**
     * 操作人       db_column: operator
     * 
     * 
     * 
     */ 
    @Column("operator")
    private java.lang.String operator;
    /**
     * 更新时间       db_column: update_time
     * 
     * 
     * 
     */ 
    @Column("update_time")
    private java.util.Date updateTime;
    
    private Date limitStart;//开始时间
    
    private Date limitEnd;//结束时间
    
    private Integer status;//状态 -1 停用，-2 已过期 0正常
    
    private Integer productCount;//商品数
    
    private Long operatorId;
    
    
    

    //columns END


	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Integer getStatus() {
		return status;
	}

	public Date getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(Date limitStart) {
		this.limitStart = limitStart;
	}

	public Date getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(Date limitEnd) {
		this.limitEnd = limitEnd;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Name",getName())
            .append("Remark",getRemark())
            .append("Operator",getOperator())
            .append("UpdateTime",getUpdateTime())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.String getOperator() {
		return operator;
	}

	public void setOperator(java.lang.String operator) {
		this.operator = operator;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
