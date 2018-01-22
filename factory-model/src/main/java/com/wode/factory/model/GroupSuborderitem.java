/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import com.alibaba.fastjson.JSON;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

@Table("t_group_suborderitem")
public class GroupSuborderitem extends BaseModel implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    //date formats
    public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
    public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;

    //columns START
    /**
     * 子单项ID       db_column: subOrderItemId
     */
    @PrimaryKey
    @Id(auto=false)
    private java.lang.Long subOrderItemId;
    /**
     * 子单号ID       db_column: subOrderId
     */
    @Column("subOrderId")
    private String subOrderId;
    /**
     * 商品编码       db_column: partNumber
     *
     * @Length(max=100)
     */
    @Column("partNumber")
    private String partNumber;
    /**
     * 单价       db_column: price
     */
    @Column("price")
    private BigDecimal price;
    /**
     * 数量       db_column: number
     *
     * @Max(127)
     */
    @Column("number")
    private Integer number;
    /**
     * 创建时间       db_column: createTime
     */
    @Column("createTime")
    private Date createTime;
    /**
     * 更新时间       db_column: updateTime
     */
    @Column("updateTime")
    private Date updateTime;
    /**
     * 修改者       db_column: updateBy
     *
     * @Length(max=50)
     */
    @Column("updateBy")
    private String updateBy;
    /**
     * 商品ID       db_column: productId
     */
    @Column("productId")
    private Long productId;
    /**
     * SKU主键       db_column: skuId
     */
    @Column("skuId")
    private Long skuId;
    /**
     * 评论标识
     */
    @Column("commentFlag")
    private Integer commentFlag;
    
    /**
     * 活动主键       db_column: promotion_id
     */
    @Column("promotion_id")
    private Long promotionId;
    
    /**
     * 活动商品主键       db_column: promotion_product_id
     */
    @Column("promotion_product_id")
    private Long promotionProductId;
    /**
     * 单项实付总计       db_column: real_pay
     */
    @Column("real_pay")
    private BigDecimal realPay;
    /**
     * 单项运费      db_column: shipping
     */
    @Column("shipping")
    private BigDecimal shipping;
    /**
     * 关联的订单ID      db_column: order_id
     */
    @Column("order_id")
    private Long orderId;
    /**
     * 结算ID      db_column: sale_bill_id
     */
    @Column("sale_bill_id")
    private Long saleBillId;
    
    @Column("commissionRatio")
    private Float commissionRatio;
	@Column("companyTicket")
	private BigDecimal companyTicket;  // 内购券使用量
	@Column("benefit_type")
	private java.lang.Integer benefitType;	//优惠类型 3:换领币
	@Column("benefit_amount")
	private BigDecimal benefitAmount;  // 优惠金额
	@Column("sale_kbn")
	private java.lang.Integer saleKbn;  // 优惠金额
	@Column("productName")
	private java.lang.String productName;  	// 商品名称
	@Column("emp_price")
	private BigDecimal empPrice; 			// 员工特价
	@Column("categoryId")
	private java.lang.Long categoryId; 		// 品类id金额
	@Column("categoryName")
	private java.lang.String categoryName;  //品类名称
	@Column("product_code")
	private java.lang.String productCode;  	//商品条形码
	@Column("itemValues")
	private java.lang.String itemValues;  	//商品规格
	@Column("image")
	private java.lang.String image;  		//图片路径
	@Column("benefit_ticket")
	private BigDecimal benefitTicket;  		// 优惠券
	@Column("benefit_self")
	private BigDecimal benefitSelf;  		// benefit_self
	@Column("internal_purchase_price")		//内购价
	private BigDecimal internalPurchasePrice;
	@Column("group_id")		//团id
	private Long groupId;
	@Column("now_real_pay")		//实付单项金额（排除运费）
	private BigDecimal nowRealPay;
	@Column("now_price")		//实际商品单价
	private BigDecimal nowPrice;
	@Column("from_way")		//商品来自来自页面
	private String fromWay;
	@Column("from_type")		//商品来自客户端
	private String fromType;
    private Integer useFuliCoin;//内购券使用个数
    
	public String getFromWay() {
		return fromWay;
	}

	public void setFromWay(String fromWay) {
		this.fromWay = fromWay;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public BigDecimal getNowRealPay() {
		return nowRealPay;
	}

	public void setNowRealPay(BigDecimal nowRealPay) {
		this.nowRealPay = nowRealPay;
	}

	public BigDecimal getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(BigDecimal nowPrice) {
		this.nowPrice = nowPrice;
	}

	public Float getCommissionRatio() {
		return commissionRatio;
	}

	public void setCommissionRatio(Float commissionRatio) {
		this.commissionRatio = commissionRatio;
	}

	public Long getPromotionProductId() {
		return promotionProductId;
	}

	public void setPromotionProductId(Long promotionProductId) {
		this.promotionProductId = promotionProductId;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	private HashMap<String, String> proValues;
    //columns END

    public GroupSuborderitem() {
    }

    public GroupSuborderitem(
            java.lang.Long subOrderItemId
    ) {
        this.subOrderItemId = subOrderItemId;
    }

    public void setSubOrderItemId(java.lang.Long value) {
        this.subOrderItemId = value;
    }

    public java.lang.Long getSubOrderItemId() {
        return this.subOrderItemId;
    }

    public void setSubOrderId(java.lang.String value) {
        this.subOrderId = value;
    }

    public java.lang.String getSubOrderId() {
        return this.subOrderId;
    }

    public void setPartNumber(java.lang.String value) {
        this.partNumber = value;
    }

    public java.lang.String getPartNumber() {
        return this.partNumber;
    }

    public void setPrice(BigDecimal value) {
        this.price = value;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setNumber(Integer value) {
        this.number = value;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setCreateTime(java.util.Date value) {
        this.createTime = value;
    }

    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    public void setUpdateTime(java.util.Date value) {
        this.updateTime = value;
    }

    public java.util.Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateBy(java.lang.String value) {
        this.updateBy = value;
    }

    public java.lang.String getUpdateBy() {
        return this.updateBy;
    }

    public java.lang.Long getProductId() {
        return productId;
    }

    public void setProductId(java.lang.Long productId) {
        this.productId = productId;
    }

    public java.lang.Long getSkuId() {
        return skuId;
    }

    public void setSkuId(java.lang.Long skuId) {
        this.skuId = skuId;
    }
    
    public Integer getCommentFlag() {
		return commentFlag;
	}
	public void setCommentFlag(Integer commentFlag) {
		this.commentFlag = commentFlag;
	}

    public HashMap<String, String> getProValues() {
        if (StringUtils.isNotBlank(itemValues)) {
        	if (!itemValues.contains("<div")) {
                proValues = JSON.parseObject(this.itemValues, HashMap.class);			
			}
        }
        return proValues;
    }

    public void setProValues(HashMap<String, String> proValues) {
        this.proValues = proValues;
    }
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("SubOrderItemId", getSubOrderItemId())
                .append("SubOrderId", getSubOrderId())
                .append("PartNumber", getPartNumber())
                .append("Price", getPrice())
                .append("Number", getNumber())
                .append("CreateTime", getCreateTime())
                .append("UpdateTime", getUpdateTime())
                .append("UpdateBy", getUpdateBy())
                .append("ProductId", getProductId())
                .append("SkuId", getSkuId())
                .toString();
    }

    public BigDecimal getRealPay() {
		return realPay;
	}

	public void setRealPay(BigDecimal realPay) {
		this.realPay = realPay;
	}

	public BigDecimal getShipping() {
		return shipping;
	}

	public void setShipping(BigDecimal shipping) {
		this.shipping = shipping;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getSaleBillId() {
		return saleBillId;
	}

	public void setSaleBillId(Long saleBillId) {
		this.saleBillId = saleBillId;
	}

	public Integer getUseFuliCoin() {
		return useFuliCoin;
	}

	public void setUseFuliCoin(Integer useFuliCoin) {
		this.useFuliCoin = useFuliCoin;
	}

	public BigDecimal getCompanyTicket() {
		return companyTicket;
	}

	public void setCompanyTicket(BigDecimal companyTicket) {
		this.companyTicket = companyTicket;
	}

	public java.lang.Integer getBenefitType() {
		return benefitType;
	}

	public void setBenefitType(java.lang.Integer benefitType) {
		this.benefitType = benefitType;
	}

	public BigDecimal getBenefitAmount() {
		return benefitAmount;
	}

	public void setBenefitAmount(BigDecimal benefitAmount) {
		this.benefitAmount = benefitAmount;
	}

	public java.lang.Integer getSaleKbn() {
		return saleKbn;
	}

	public void setSaleKbn(java.lang.Integer saleKbn) {
		this.saleKbn = saleKbn;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public BigDecimal getEmpPrice() {
		return empPrice;
	}

	public void setEmpPrice(BigDecimal empPrice) {
		this.empPrice = empPrice;
	}

	public java.lang.Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(java.lang.Long categoryId) {
		this.categoryId = categoryId;
	}

	public java.lang.String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(java.lang.String categoryName) {
		this.categoryName = categoryName;
	}

	public java.lang.String getProductCode() {
		return productCode;
	}

	public void setProductCode(java.lang.String productCode) {
		this.productCode = productCode;
	}

	public java.lang.String getItemValues() {
		return itemValues;
	}

	public void setItemValues(java.lang.String itemValues) {
		this.itemValues = itemValues;
	}

	public java.lang.String getImage() {
		return image;
	}

	public void setImage(java.lang.String image) {
		this.image = image;
	}

	public BigDecimal getBenefitTicket() {
		return benefitTicket;
	}

	public void setBenefitTicket(BigDecimal benefitTicket) {
		this.benefitTicket = benefitTicket;
	}

	public BigDecimal getBenefitSelf() {
		return benefitSelf;
	}

	public void setBenefitSelf(BigDecimal benefitSelf) {
		this.benefitSelf = benefitSelf;
	}

	public int hashCode() {
        return new HashCodeBuilder()
                .append(getSubOrderItemId())
                .toHashCode();
    }

	public boolean equals(Object obj) {
        if (obj instanceof GroupSuborderitem == false) return false;
        if (this == obj) return true;
        GroupSuborderitem other = (GroupSuborderitem) obj;
        return new EqualsBuilder()
                .append(getSubOrderItemId(), other.getSubOrderItemId())
                .isEquals();
    }

	public BigDecimal getInternalPurchasePrice() {
		return internalPurchasePrice;
	}

	public void setInternalPurchasePrice(BigDecimal internalPurchasePrice) {
		this.internalPurchasePrice = internalPurchasePrice;
	}
}

