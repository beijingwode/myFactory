package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

/**
 * @author Administrator
 *
 */
@Table("t_supplier_prize")
public class SupplierPrize extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6274349987455213587L;
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
     * 所属企业       db_column: enterprise_id
     * 
     * 
     * 
     */ 
    @Column("enterprise_id")
    private java.lang.Long enterpriseId;
    /**
     * 企业名称       db_column: enterprise_name
     * 
     * 
     * 
     */ 
    @Column("enterprise_name")
    private java.lang.String enterpriseName;
    /**
     * 活动id       db_column: acticity_id
     * 
     * 
     * 
     */ 
    @Column("acticity_id")
    private java.lang.Long acticityId;
    /**
     * 活动主题       db_column: acticity_theme
     * 
     * 
     * 
     */ 
    @Column("acticity_theme")
    private java.lang.String acticityTheme;
    /**
     * 奖品等级       db_column: prize_grade
     * 
     * 
     * 
     */ 
    @Column("prize_grade")
    private java.lang.Integer prizeGrade;
    
    /**
     * 等级名称
     */
    @Column("grade_name")
    private String gradeName;
    /**
     * 奖品名称       db_column: prize_name
     * 
     * 
     * 
     */ 
    @Column("prize_name")
    private java.lang.String prizeName;
    /**
     * 奖品图片       db_column: image
     * 
     * 
     * 
     */ 
    @Column("image")
    private java.lang.String image;
    /**
     * 奖品类型       db_column: type
     * 
     * 
     * 
     */ 
    @Column("type")
    private java.lang.String type;
    /**
     * 链接 或商品idskuId       db_column: link
     * 
     * 
     * 
     */ 
    @Column("link")
    private java.lang.String link;
    /**
     * 数量       db_column: number
     * 
     * 
     * 
     */ 
    @Column("number")
    private java.lang.Integer number;
    /**
     * 现金       db_column: cash
     * 
     * 
     * 
     */ 
    @Column("cash")
    private java.math.BigDecimal cash;
    /**
     * 内购券       db_column: ticket
     * 
     * 
     * 
     */ 
    @Column("ticket")
    private java.math.BigDecimal ticket;
    /**
     * 备注       db_column: note
     * 
     * 
     * 
     */ 
    @Column("note")
    private java.lang.String note;
    /**
     * 开始日期       db_column: limit_start
     * 
     * 
     * 
     */ 
    @Column("limit_start")
    private java.util.Date limitStart;
    /**
     * 结束日期       db_column: limit_end
     * 
     * 
     * 
     */ 
    @Column("limit_end")
    private java.util.Date limitEnd;
    

    //columns END

    public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("EnterpriseId",getEnterpriseId())
            .append("EnterpriseName",getEnterpriseName())
            .append("ActicityId",getActicityId())
            .append("ActicityTheme",getActicityTheme())
            .append("PrizeGrade",getPrizeGrade())
            .append("PrizeName",getPrizeName())
            .append("Image",getImage())
            .append("Type",getType())
            .append("Link",getLink())
            .append("Number",getNumber())
            .append("Cash",getCash())
            .append("Ticket",getTicket())
            .append("Note",getNote())
            .append("LimitStart",getLimitStart())
            .append("LimitEnd",getLimitEnd())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(java.lang.Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public java.lang.String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(java.lang.String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public java.lang.Long getActicityId() {
		return acticityId;
	}

	public void setActicityId(java.lang.Long acticityId) {
		this.acticityId = acticityId;
	}

	public java.lang.String getActicityTheme() {
		return acticityTheme;
	}

	public void setActicityTheme(java.lang.String acticityTheme) {
		this.acticityTheme = acticityTheme;
	}

	public java.lang.Integer getPrizeGrade() {
		return prizeGrade;
	}

	public void setPrizeGrade(java.lang.Integer prizeGrade) {
		this.prizeGrade = prizeGrade;
	}

	public java.lang.String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(java.lang.String prizeName) {
		this.prizeName = prizeName;
	}

	public java.lang.String getImage() {
		return image;
	}

	public void setImage(java.lang.String image) {
		this.image = image;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.String getLink() {
		return link;
	}

	public void setLink(java.lang.String link) {
		this.link = link;
	}

	public java.lang.Integer getNumber() {
		return number;
	}

	public void setNumber(java.lang.Integer number) {
		this.number = number;
	}

	public java.math.BigDecimal getCash() {
		return cash;
	}

	public void setCash(java.math.BigDecimal cash) {
		this.cash = cash;
	}

	public java.math.BigDecimal getTicket() {
		return ticket;
	}

	public void setTicket(java.math.BigDecimal ticket) {
		this.ticket = ticket;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.util.Date getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(java.util.Date limitStart) {
		this.limitStart = limitStart;
	}

	public java.util.Date getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(java.util.Date limitEnd) {
		this.limitEnd = limitEnd;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

