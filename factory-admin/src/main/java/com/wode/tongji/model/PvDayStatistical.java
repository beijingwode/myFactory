package com.wode.tongji.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

public class PvDayStatistical extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1629947249622184377L;
	//columns START
    /**
     * id       db_column: id
     * 
     * 
     * 
     */ 
    @PrimaryKey
    private java.lang.Integer id;
    /**
     * 日期       db_column: day
     * 
     * 
     * 
     */ 
    private java.util.Date day;
    /**
     * 统计日期       db_column: create_time
     * 
     * 
     * 
     */ 
    private java.util.Date createTime;
    /**
     * 商品浏览量       db_column: product_cnt
     * 
     * 
     * 
     */ 
    private java.lang.Long productCnt;
    /**
     * 首页浏览量       db_column: index_cnt
     * 
     * 
     * 
     */ 
    private java.lang.Long indexCnt;
    /**
     * 检索浏览量       db_column: search_cnt
     * 
     * 
     * 
     */ 
    private java.lang.Long searchCnt;
    /**
     * 店铺浏览量       db_column: shop_cnt
     * 
     * 
     * 
     */ 
    private java.lang.Long shopCnt;
    /**
     * 加入购物车次数       db_column: cart_cnt
     * 
     * 
     * 
     */ 
    private java.lang.Long cartCnt;
    /**
     * 直接下单量       db_column: direct_cnt
     * 
     * 
     * 
     */ 
    private java.lang.Long directCnt;
    /**
     * 订单量       db_column: order_cnt
     * 
     * 
     * 
     */ 
    private java.lang.Long orderCnt;
    /**
     * 支付量       db_column: pay_cnt
     * 
     * 
     * 
     */ 
    private java.lang.Long payCnt;
    /**
     * 其他1       db_column: else1_cnt
     * 
     * 
     * 
     */ 
    private java.lang.Long else1Cnt;
    /**
     * 其他2       db_column: else2_cnt
     * 
     * 
     * 
     */ 
    private java.lang.Long else2Cnt;
    /**
     * 其他3       db_column: else3_cnt
     * 
     * 
     * 
     */ 
    private java.lang.Long else3Cnt;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Day",getDay())
            .append("CreateTime",getCreateTime())
            .append("ProductCnt",getProductCnt())
            .append("IndexCnt",getIndexCnt())
            .append("SearchCnt",getSearchCnt())
            .append("ShopCnt",getShopCnt())
            .append("CartCnt",getCartCnt())
            .append("DirectCnt",getDirectCnt())
            .append("OrderCnt",getOrderCnt())
            .append("PayCnt",getPayCnt())
            .append("Else1Cnt",getElse1Cnt())
            .append("Else2Cnt",getElse2Cnt())
            .append("Else3Cnt",getElse3Cnt())
            .toString();
    }

    public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.util.Date getDay() {
		return day;
	}

	public void setDay(java.util.Date day) {
		this.day = day;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.Long getProductCnt() {
		return productCnt;
	}

	public void setProductCnt(java.lang.Long productCnt) {
		this.productCnt = productCnt;
	}

	public java.lang.Long getIndexCnt() {
		return indexCnt;
	}

	public void setIndexCnt(java.lang.Long indexCnt) {
		this.indexCnt = indexCnt;
	}

	public java.lang.Long getSearchCnt() {
		return searchCnt;
	}

	public void setSearchCnt(java.lang.Long searchCnt) {
		this.searchCnt = searchCnt;
	}

	public java.lang.Long getShopCnt() {
		return shopCnt;
	}

	public void setShopCnt(java.lang.Long shopCnt) {
		this.shopCnt = shopCnt;
	}

	public java.lang.Long getCartCnt() {
		return cartCnt;
	}

	public void setCartCnt(java.lang.Long cartCnt) {
		this.cartCnt = cartCnt;
	}

	public java.lang.Long getDirectCnt() {
		return directCnt;
	}

	public void setDirectCnt(java.lang.Long directCnt) {
		this.directCnt = directCnt;
	}

	public java.lang.Long getOrderCnt() {
		return orderCnt;
	}

	public void setOrderCnt(java.lang.Long orderCnt) {
		this.orderCnt = orderCnt;
	}

	public java.lang.Long getPayCnt() {
		return payCnt;
	}

	public void setPayCnt(java.lang.Long payCnt) {
		this.payCnt = payCnt;
	}

	public java.lang.Long getElse1Cnt() {
		return else1Cnt;
	}

	public void setElse1Cnt(java.lang.Long else1Cnt) {
		this.else1Cnt = else1Cnt;
	}

	public java.lang.Long getElse2Cnt() {
		return else2Cnt;
	}

	public void setElse2Cnt(java.lang.Long else2Cnt) {
		this.else2Cnt = else2Cnt;
	}

	public java.lang.Long getElse3Cnt() {
		return else3Cnt;
	}

	public void setElse3Cnt(java.lang.Long else3Cnt) {
		this.else3Cnt = else3Cnt;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
