package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;

@Table("t_user_im_group")
public class UserImGroup extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8943560744291928840L;
	//columns START
    /**
     * id       db_column: id
     * 
     * 
     * 
     */ 
    @Column("id")
    private java.lang.Long id;
    /**
     * im返回群组id       db_column: im_group_id
     * 
     * 
     * 
     */ 
    @Column("im_group_id")
    private java.lang.String imGroupId;
    /**
     * 群组名称       db_column: groupname
     * 
     * 
     * 
     */ 
    @Column("groupname")
    private java.lang.String groupname;
    /**
     * 描述       db_column: desc
     * 
     * 
     * 
     */ 
    @Column("note")
    private java.lang.String note;
    /**
     * 是否公开 1：公开/0：不公开       db_column: public
     * 
     * 
     * 
     */ 
    @Column("publiced")
    private java.lang.String publiced;
    /**
     * 群组最大人数       db_column: maxusers
     * 
     * 
     * 
     */ 
    @Column("maxusers")
    private java.lang.Integer maxusers;
    /**
     * 群组最大人数       db_column: cnt
     * 
     * 
     * 
     */ 
    @Column("cnt")
    private java.lang.Integer cnt;
    /**
     * 是否需要批准加入 1：公开/0：不公开       db_column: approval
     * 
     * 
     * 
     */ 
    @Column("approval")
    private java.lang.String approval;
    /**
     * 群主       db_column: owner
     * 
     * 
     * 
     */ 
    @Column("owner")
    private java.lang.String owner;
    /**
     * 群主用户id       db_column: owner_id
     * 
     * 
     * 
     */ 
    @Column("owner_id")
    private java.lang.Long ownerId;
    /**
     * 创建时间       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private java.util.Date createTime;
    /**
     * 属性1       db_column: op1
     * 
     * 
     * 
     */ 
    @Column("op1")
    private java.lang.String op1;
    /**
     * 属性2       db_column: op2
     * 
     * 
     * 
     */ 
    @Column("op2")
    private java.lang.String op2;
    /**
     * 属性3       db_column: op3
     * 
     * 
     * 
     */ 
    @Column("op3")
    private java.lang.String op3;
    /**
     * 属性4       db_column: op4
     * 
     * 
     * 
     */ 
    @Column("op4")
    private java.lang.String op4;
    /**
     * 群组关系类型       db_column: relationType
     * 
     * 
     * 
     */ 
    @Column("relationType")
    private java.lang.Integer relationType;
    
    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("ImGroupId",getImGroupId())
            .append("Groupname",getGroupname())
            .append("Desc",getNote())
            .append("Public",getPubliced())
            .append("Maxusers",getMaxusers())
            .append("Approval",getApproval())
            .append("Owner",getOwner())
            .append("OwnerId",getOwnerId())
            .append("CreateTime",getCreateTime())
            .append("Op1",getOp1())
            .append("Op2",getOp2())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getImGroupId() {
		return imGroupId;
	}

	public void setImGroupId(java.lang.String imGroupId) {
		this.imGroupId = imGroupId;
	}

	public java.lang.String getGroupname() {
		return groupname;
	}

	public void setGroupname(java.lang.String groupname) {
		this.groupname = groupname;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getPubliced() {
		return publiced;
	}

	public void setPubliced(java.lang.String publiced) {
		this.publiced = publiced;
	}

	public java.lang.Integer getMaxusers() {
		return maxusers;
	}

	public void setMaxusers(java.lang.Integer maxusers) {
		this.maxusers = maxusers;
	}

	public java.lang.String getApproval() {
		return approval;
	}

	public void setApproval(java.lang.String approval) {
		this.approval = approval;
	}

	public java.lang.String getOwner() {
		return owner;
	}

	public void setOwner(java.lang.String owner) {
		this.owner = owner;
	}

	public java.lang.Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(java.lang.Long ownerId) {
		this.ownerId = ownerId;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getOp1() {
		return op1;
	}

	public void setOp1(java.lang.String op1) {
		this.op1 = op1;
	}

	public java.lang.String getOp2() {
		return op2;
	}

	public void setOp2(java.lang.String op2) {
		this.op2 = op2;
	}

	public java.lang.String getOp3() {
		return op3;
	}

	public void setOp3(java.lang.String op3) {
		this.op3 = op3;
	}

	public java.lang.String getOp4() {
		return op4;
	}

	public void setOp4(java.lang.String op4) {
		this.op4 = op4;
	}

	public java.lang.Integer getCnt() {
		return cnt;
	}

	public void setCnt(java.lang.Integer cnt) {
		this.cnt = cnt;
	}

	public java.lang.Integer getRelationType() {
		return relationType;
	}

	public void setRelationType(java.lang.Integer relationType) {
		this.relationType = relationType;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

