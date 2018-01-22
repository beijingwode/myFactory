/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;


public class StoreCategory extends BaseModel implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    //date formats
    public static final String FORMAT_CREATE_DATE = DATE_TIME_FORMAT;
    public static final String FORMAT_UPDATE_DATE = DATE_TIME_FORMAT;

    //columns START
    /**
     * ID       db_column: id
     */
    @PrimaryKey
    private java.lang.Long id;
    /**
     * 排序       db_column: orders
     *
     * @Max(127)
     */
    private Integer orders;
    /**
     * 级别       db_column: grade
     *
     * @Max(127)
     */
    private Integer grade;
    /**
     * 名称       db_column: name
     *
     * @Length(max=100)
     */
    private java.lang.String name;
    /**
     * 父分类ID       db_column: parent
     */
    private java.lang.Long parent;
    /**
     * 供应商ID       db_column: supplierId
     */
    private java.lang.Long supplierId;
    /**
     * 创建时间       db_column: createDate
     */
    private java.util.Date createDate;
    /**
     * 更新时间       db_column: updateDate
     */
    private java.util.Date updateDate;
    //columns END

    /**
     * 是否默认展开
     */
    private Boolean isExpanding;

    private List<StoreCategory> children=new ArrayList();

    public List<StoreCategory> getChildren() {
        return children;
    }

    public void setChildren(List<StoreCategory> children) {
        this.children = children;
    }

    public StoreCategory() {
    }

    public StoreCategory(
            java.lang.Long id
    ) {
        this.id = id;
    }

    public void setId(java.lang.Long value) {
        this.id = value;
    }

    public java.lang.Long getId() {
        return this.id;
    }

    public void setOrders(Integer value) {
        this.orders = value;
    }

    public Integer getOrders() {
        return this.orders;
    }

    public void setGrade(Integer value) {
        this.grade = value;
    }

    public Integer getGrade() {
        return this.grade;
    }

    public void setName(java.lang.String value) {
        this.name = value;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setParent(java.lang.Long value) {
        this.parent = value;
    }

    public java.lang.Long getParent() {
        return this.parent;
    }

    public void setSupplierId(java.lang.Long value) {
        this.supplierId = value;
    }

    public java.lang.Long getSupplierId() {
        return this.supplierId;
    }

    public String getCreateDateString() {
        return DateConvertUtils.format(getCreateDate(), FORMAT_CREATE_DATE);
    }

    public void setCreateDateString(String value) {
        setCreateDate(DateConvertUtils.parse(value, FORMAT_CREATE_DATE, java.util.Date.class));
    }

    public void setCreateDate(java.util.Date value) {
        this.createDate = value;
    }

    public java.util.Date getCreateDate() {
        return this.createDate;
    }

    public String getUpdateDateString() {
        return DateConvertUtils.format(getUpdateDate(), FORMAT_UPDATE_DATE);
    }

    public void setUpdateDateString(String value) {
        setUpdateDate(DateConvertUtils.parse(value, FORMAT_UPDATE_DATE, java.util.Date.class));
    }

    public void setUpdateDate(java.util.Date value) {
        this.updateDate = value;
    }

    public java.util.Date getUpdateDate() {
        return this.updateDate;
    }

    public Boolean getIsExpanding() {
        return isExpanding;
    }

    public void setIsExpanding(Boolean isExpanding) {
        this.isExpanding = isExpanding;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Id", getId())

                .append("Orders", getOrders())
                .append("Grade", getGrade())
                .append("Name", getName())
                .append("Parent", getParent())
                .append("SupplierId", getSupplierId())
                .append("CreateDate", getCreateDate())
                .append("UpdateDate", getUpdateDate())
                .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof StoreCategory == false) return false;
        if (this == obj) return true;
        StoreCategory other = (StoreCategory) obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

