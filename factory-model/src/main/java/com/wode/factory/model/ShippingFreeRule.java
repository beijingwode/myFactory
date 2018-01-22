package com.wode.factory.model;

import java.math.BigDecimal;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
@Table("t_shipping_free_rule")
public class ShippingFreeRule extends BaseModel implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4275743224913507252L;
	@PrimaryKey
	@Column("id")
	private Long id;
	@Column("template_id")
    private Long templateId;
	
	@Column("count_type")
    private String countType;
	
	@Column("send_type")
    private String sendType;

	@Column("sort")
    private Integer sort;

	@Column("areas_name")
    private String areasName;

	@Column("areas_code")
    private String areasCode;

	@Column("count_type_des")
    private String countTypeDes;

	@Column("param1")
    private BigDecimal param1;

	@Column("param2")
    private BigDecimal param2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getAreasName() {
		return areasName;
	}

	public void setAreasName(String areasName) {
		this.areasName = areasName;
	}

	public String getAreasCode() {
		return areasCode;
	}

	public void setAreasCode(String areasCode) {
		this.areasCode = areasCode;
	}

	public String getCountTypeDes() {
		return countTypeDes;
	}

	public void setCountTypeDes(String countTypeDes) {
		this.countTypeDes = countTypeDes;
	}

	public BigDecimal getParam1() {
		return param1;
	}

	public void setParam1(BigDecimal param1) {
		this.param1 = param1;
	}

	public BigDecimal getParam2() {
		return param2;
	}

	public void setParam2(BigDecimal param2) {
		this.param2 = param2;
	}

	@Override
	public String toString() {
		return "ShippingFreeRule [id=" + id + ", templateId=" + templateId + ", countType=" + countType + ", sendType="
				+ sendType + ", sort=" + sort + ", areasName=" + areasName + ", areasCode=" + areasCode
				+ ", countTypeDes=" + countTypeDes + ", param1=" + param1 + ", param2=" + param2 + "]";
	}
	
}