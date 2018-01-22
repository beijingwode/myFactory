package com.wode.factory.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
@Table("t_product_shipping")
public class ProductShipping extends BaseModel implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6654653388461460482L;
	@PrimaryKey
	@Column("id")
	private Long id;
	@Column("product_id")
    private Long productId;
	@Column("template_id")
    private Long templateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
}