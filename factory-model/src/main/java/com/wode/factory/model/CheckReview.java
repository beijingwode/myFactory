package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_check_review")
public class CheckReview extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5382519174027332301L;
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
     * 
     */ 
    @Column("obj_key")
    private java.lang.Long objKey;
    /**
     * 备注       db_column: memo
     * 
     * 
     * 
     */ 
    @Column("memo")
    private java.lang.String memo;
    /**
     * 下次提醒日期       db_column: alarm_date
     * 
     * 
     * 
     */ 
    @Column("alarm_date")
    private java.util.Date alarmDate;
    /**
     * 备选1       db_column: option1
     * 
     * 
     * 
     */ 
    @Column("option1")
    private java.lang.String option1;
    /**
     * 备选2       db_column: option2
     * 
     * 
     * 
     */ 
    @Column("option2")
    private java.lang.String option2;
    /**
     * 备选3       db_column: option3
     * 
     * 
     * 
     */ 
    @Column("option3")
    private java.lang.String option3;
    /**
     * 删除标志 1：删除       db_column: is_delete
     * 
     * 
     * 
     */ 
    @Column("is_delete")
    private java.lang.Integer isDelete;
    /**
     * 创建日期       db_column: create_time
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
     * 
     */ 
    @Column("create_user")
    private java.lang.String createUser;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Key",getObjKey())
            .append("Memo",getMemo())
            .append("AlarmDate",getAlarmDate())
            .append("Option1",getOption1())
            .append("Option2",getOption2())
            .append("Option3",getOption3())
            .append("IsDelete",getIsDelete())
            .append("CreateTime",getCreateTime())
            .append("CreateUser",getCreateUser())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getObjKey() {
		return objKey;
	}

	public void setObjKey(java.lang.Long objKey) {
		this.objKey = objKey;
	}

	public java.lang.String getMemo() {
		return memo;
	}

	public void setMemo(java.lang.String memo) {
		this.memo = memo;
	}

	public java.util.Date getAlarmDate() {
		return alarmDate;
	}

	public void setAlarmDate(java.util.Date alarmDate) {
		this.alarmDate = alarmDate;
	}

	public java.lang.String getOption1() {
		return option1;
	}

	public void setOption1(java.lang.String option1) {
		this.option1 = option1;
	}

	public java.lang.String getOption2() {
		return option2;
	}

	public void setOption2(java.lang.String option2) {
		this.option2 = option2;
	}

	public java.lang.String getOption3() {
		return option3;
	}

	public void setOption3(java.lang.String option3) {
		this.option3 = option3;
	}

	public java.lang.Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
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

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
