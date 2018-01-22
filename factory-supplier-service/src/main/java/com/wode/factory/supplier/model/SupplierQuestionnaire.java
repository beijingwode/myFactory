package com.wode.factory.supplier.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
import com.wode.factory.model.ProductQuestionnaire;
import com.wode.factory.model.QuestionnaireQuestion;

@Table("t_supplier_questionnaire")
public class SupplierQuestionnaire extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7259019343223294678L;
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
     * 商家id       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    /**
     * 模板名称       db_column: template_title
     * 
     * 
     * 
     */ 
    @Column("template_title")
    private java.lang.String templateTitle;
    /**
     * 文本输入       db_column: test_flg
     * 
     * 
     * 
     */ 
    @Column("test_flg")
    private java.lang.Integer testFlg;
    /**
     * 创建时间       db_column: create_date
     * 
     * 
     * 
     */ 
    @Column("create_date")
    private java.util.Date createDate;
    /**
     * 创建者       db_column: create_user
     * 
     * 
     * 
     */ 
    @Column("create_user")
    private java.lang.Long createUser;

    //columns END


    private List<QuestionnaireQuestion> listQuestion;
    public SupplierQuestionnaire() {
    	
    }
    
    public SupplierQuestionnaire(ProductQuestionnaire pq) {
    	this.setId(pq.getId());
    	this.setTemplateTitle(pq.getTemplateTitle() + " 正在使用");
    }
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("SupplierId",getSupplierId())
            .append("TemplateTitle",getTemplateTitle())
            .append("TestFlg",getTestFlg())
            .append("CreateDate",getCreateDate())
            .append("CreateUser",getCreateUser())
            .toString();
    }

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

	public java.lang.String getTemplateTitle() {
		return templateTitle;
	}

	public void setTemplateTitle(java.lang.String templateTitle) {
		this.templateTitle = templateTitle;
	}

	public java.lang.Integer getTestFlg() {
		return testFlg;
	}

	public void setTestFlg(java.lang.Integer testFlg) {
		this.testFlg = testFlg;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(java.lang.Long createUser) {
		this.createUser = createUser;
	}

	public List<QuestionnaireQuestion> getListQuestion() {
		if(listQuestion==null) {
			listQuestion = new ArrayList<QuestionnaireQuestion>();
		}
		return listQuestion;
	}

	public void setListQuestion(List<QuestionnaireQuestion> listQuestion) {
		this.listQuestion = listQuestion;
	}
	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
