package com.wode.factory.model;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;

@Table("t_questionnaire_question")
public class QuestionnaireQuestion extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8253275976009025484L;
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
     * 问题标题       db_column: question_title
     * 
     * 
     * 
     */ 
    @Column("question_title")
    private java.lang.String questionTitle;
    /**
     * 选择方式 1:单选/2:多选       db_column: select_type
     * 
     * 
     * 
     */ 
    @Column("select_type")
    private java.lang.Integer selectType;
    /**
     * 排序       db_column: orders
     * 
     * 
     * 
     */ 
    @Column("orders")
    private java.lang.Integer orders;
    /**
     * 原问题id以便处理选项       db_column: old_id
     * 
     * 
     * 
     */ 
    @Column("old_id")
    private java.lang.Long oldId;

    //columns END

    private List<QuestionnaireOption> listOption;
    
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("QuestionnaireId",getQuestionnaireId())
            .append("QuestionTitle",getQuestionTitle())
            .append("SelectType",getSelectType())
            .append("Orders",getOrders())
            .append("OldId",getOldId())
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

	public java.lang.String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(java.lang.String questionTitle) {
		this.questionTitle = questionTitle;
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

	public java.lang.Long getOldId() {
		return oldId;
	}

	public void setOldId(java.lang.Long oldId) {
		this.oldId = oldId;
	}

	public List<QuestionnaireOption> getListOption() {
		if(listOption==null) {
			listOption = new ArrayList<QuestionnaireOption>();
		}
		return listOption;
	}

	public void setListOption(List<QuestionnaireOption> listOption) {
		this.listOption = listOption;
	}

}

