package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_questionnaire_answer")
public class QuestionnaireAnswer extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 9169235586156996079L;
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
     * 选项id       db_column: option_id
     * 
     * 
     * 
     */ 
    @Column("option_id")
    private java.lang.Long optionId;
    /**
     * 用户id       db_column: user_id
     * 
     * 
     * 
     */ 
    @Column("user_id")
    private java.lang.Long userId;
    /**
     * 创建时间       db_column: create_date
     * 
     * 
     * 
     */ 
    @Column("create_date")
    private java.util.Date createDate;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("QuestionnaireId",getQuestionnaireId())
            .append("QuestionId",getQuestionId())
            .append("OptionId",getOptionId())
            .append("UserId",getUserId())
            .append("CreateDate",getCreateDate())
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

	public java.lang.Long getOptionId() {
		return optionId;
	}

	public void setOptionId(java.lang.Long optionId) {
		this.optionId = optionId;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

}

