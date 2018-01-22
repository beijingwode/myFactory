package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_questionnaire_option")
public class QuestionnaireOption extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7843758764073622434L;
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
     * 问卷id       db_column: questionnaire_id
     * 
     * 
     * 
     */ 
    @Column("questionnaire_id")
    private java.lang.Long questionnaireId;
    /**
     * 问题Id       db_column: question_id
     * 
     * 
     * 
     */ 
    @Column("question_id")
    private java.lang.Long questionId;
    /**
     * 选项标题       db_column: option_title
     * 
     * 
     * 
     */ 
    @Column("option_title")
    private java.lang.String optionTitle;
    /**
     * 选择方式 1:单选/2:多选       db_column: select_type
     * 
     * 
     * 
     */ 
    @Column("select_type")
    private java.lang.Integer selectType;
    /**
     * 选项标题       db_column: option_title
     * 
     * 
     * 
     */ 
    @Column("image")
    private java.lang.String image;
    /**
     * 排序       db_column: orders
     * 
     * 
     * 
     */ 
    @Column("orders")
    private java.lang.Integer orders;

    //columns END

    private Long cnt;
    
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("QuestionnaireId",getQuestionnaireId())
            .append("QuestionId",getQuestionId())
            .append("OptionTitle",getOptionTitle())
            .append("SelectType",getSelectType())
            .append("Orders",getOrders())
            .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(java.lang.Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public java.lang.Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(java.lang.Long questionId) {
		this.questionId = questionId;
	}

	public java.lang.String getOptionTitle() {
		return optionTitle;
	}

	public void setOptionTitle(java.lang.String optionTitle) {
		this.optionTitle = optionTitle;
	}

	public java.lang.Integer getSelectType() {
		return selectType;
	}

	public void setSelectType(java.lang.Integer selectType) {
		this.selectType = selectType;
	}

	public java.lang.Integer getOrders() {
		return orders;
	}

	public void setOrders(java.lang.Integer orders) {
		this.orders = orders;
	}

	public java.lang.String getImage() {
		return image;
	}

	public void setImage(java.lang.String image) {
		this.image = image;
	}

	public Long getCnt() {
		return cnt;
	}

	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}

}

