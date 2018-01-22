package com.wode.factory.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
@Table("t_shipping_template")
public class ShippingTemplate extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3167432220092460234L;
	@PrimaryKey
	@Column("id")
    private Long id;
	@Column("supplier_id")
    private Long supplierId;

	@Column("name")
    private String name;

	@Column("send_time_des")
    private String sendTimeDes;

	@Column("count_type")
    private String countType;

	@Column("create_user")
    private String createUser;

	@Column("create_time")
    private Date createTime;

	@Column("update_user")
    private String updateUser;

	@Column("update_time")
    private Date updateTime;
	
	@Column("version")
	private Integer version;
	
	@Column("is_audit")
	private Integer isAudit;//是否需要审核
	
	//属性值列表
	@Many(target = ShippingTemplateRule.class, field = "templateId")
	private List<ShippingTemplateRule> rulelist = new ArrayList<ShippingTemplateRule>();

	//属性值列表
	@Many(target = ShippingFreeRule.class, field = "templateId")
	private List<ShippingFreeRule> freelist = new ArrayList<ShippingFreeRule>();
	
	private String supplierName;//商家名称
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSendTimeDes() {
        return sendTimeDes;
    }

    public void setSendTimeDes(String sendTimeDes) {
        this.sendTimeDes = sendTimeDes == null ? null : sendTimeDes.trim();
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType == null ? null : countType.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public List<ShippingTemplateRule> getRulelist() {
		return rulelist;
	}

	public void setRulelist(List<ShippingTemplateRule> rulelist) {
		this.rulelist = rulelist;
	}

	public List<ShippingFreeRule> getFreelist() {
		return freelist;
	}

	public void setFreelist(List<ShippingFreeRule> freelist) {
		this.freelist = freelist;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}

	@Override
	public String toString() {
		return "ShippingTemplate [id=" + id + ", supplierId=" + supplierId + ", name=" + name + ", sendTimeDes="
				+ sendTimeDes + ", countType=" + countType + ", createUser=" + createUser + ", createTime=" + createTime
				+ ", updateUser=" + updateUser + ", updateTime=" + updateTime + ", version=" + version + ", isAudit="
				+ isAudit + ", rulelist=" + rulelist + ", freelist=" + freelist + "]";
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
    
	
    
}