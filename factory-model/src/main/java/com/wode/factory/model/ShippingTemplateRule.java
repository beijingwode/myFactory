package com.wode.factory.model;

import java.math.BigDecimal;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
@Table("t_shipping_template_rule")
public class ShippingTemplateRule extends BaseModel implements java.io.Serializable{
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

	@Column("first_cnt")
    private BigDecimal firstCnt;

	@Column("first_price")
    private BigDecimal firstPrice;

	@Column("plus_cnt")
    private BigDecimal plusCnt;

	@Column("plus_price")
    private BigDecimal plusPrice;

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
        this.sendType = sendType == null ? null : sendType.trim();
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
        this.areasName = areasName == null ? null : areasName.trim();
    }

    public String getAreasCode() {
        return areasCode;
    }

    public void setAreasCode(String areasCode) {
        this.areasCode = areasCode == null ? null : areasCode.trim();
    }

    public BigDecimal getFirstCnt() {
        return firstCnt;
    }

    public void setFirstCnt(BigDecimal firstCnt) {
        this.firstCnt = firstCnt;
    }

    public BigDecimal getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(BigDecimal firstPrice) {
        this.firstPrice = firstPrice;
    }

    public BigDecimal getPlusCnt() {
        return plusCnt;
    }

    public void setPlusCnt(BigDecimal plusCnt) {
        this.plusCnt = plusCnt;
    }

    public BigDecimal getPlusPrice() {
        return plusPrice;
    }

    public void setPlusPrice(BigDecimal plusPrice) {
        this.plusPrice = plusPrice;
    }

	@Override
	public String toString() {
		return "ShippingTemplateRule [id=" + id + ", templateId=" + templateId + ", countType=" + countType
				+ ", sendType=" + sendType + ", sort=" + sort + ", areasName=" + areasName + ", areasCode=" + areasCode
				+ ", firstCnt=" + firstCnt + ", firstPrice=" + firstPrice + ", plusCnt=" + plusCnt + ", plusPrice="
				+ plusPrice + "]";
	}
    
}