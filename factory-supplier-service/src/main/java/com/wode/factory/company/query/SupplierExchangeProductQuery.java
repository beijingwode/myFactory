package com.wode.factory.company.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;

import com.wode.common.frame.base.BaseQuery;
import com.wode.common.stereotype.PrimaryKey;

public class SupplierExchangeProductQuery extends BaseQuery implements Serializable{

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
     * 特卖区分 3：换领       db_column: sale_kbn
     * 
     * 
     * 
     */ 
    @Column("sale_kbn")
    private java.lang.Integer saleKbn;
    /**
     * 换领原因       db_column: sale_note
     * 
     * 
     * 
     */ 
    @Column("sale_note")
    private java.lang.String saleNote;
    /**
     * 商品id       db_column: product_id
     * 
     * 
     * 
     */ 
    @Column("product_id")
    private java.lang.Long productId;
    /**
     * 商品标题       db_column: product_name
     * 
     * 
     * 
     */ 
    @Column("product_name")
    private java.lang.String productName;
    /**
     * 商品图片       db_column: product_img
     * 
     * 
     * 
     */ 
    @Column("product_img")
    private java.lang.String productImg;
    /**
     * 商品价格       db_column: product_price
     * 
     * 
     * 
     */ 
    @Column("product_price")
    private java.math.BigDecimal productPrice;
    /**
     * 总数       db_column: product_cnt
     * 
     * 
     * 
     */ 
    @Column("product_cnt")
    private java.lang.Integer productCnt;
    /**
     * 员工级别       db_column: emp_level
     * 
     * 
     * 
     */ 
    @Column("emp_level")
    private java.lang.Integer empLevel;
    /**
     * 员工数量       db_column: emp_cnt
     * 
     * 
     * 
     */ 
    @Column("emp_cnt")
    private java.lang.Integer empCnt;
    /**
     * 平均每人获得个数       db_column: emp_avg_cnt
     * 
     * 
     * 
     */ 
    @Column("emp_avg_cnt")
    private java.math.BigDecimal empAvgCnt;
    /**
     * 平均获得获得换领币总额       db_column: emp_avg_amount
     * 
     * 
     * 
     */ 
    @Column("emp_avg_amount")
    private java.math.BigDecimal empAvgAmount;
    /**
     * 期限类型 1:1个月 2:2两个 3:3个月 5:半个月 9:售完为止       db_column: limit_type
     * 
     * 
     * 
     */ 
    @Column("limit_type")
    private java.lang.Integer limitType;
    /**
     * 开始日期       db_column: limit_start
     * 
     * 
     * 
     */ 
    @Column("limit_start")
    private java.lang.String limitStart;
    /**
     * 结束日期       db_column: limit_end
     * 
     * 
     * 
     */ 
    @Column("limit_end")
    private java.lang.String limitEnd;
    /**
     * 状态 1;待审核/2:换领中/3:已结束（领完）/4:提前终止/5:已结束（终止）/7:已结束（到期） /9:线下发放       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
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
    /**
     * 更新时间       db_column: update_date
     * 
     * 
     * 
     */ 
    @Column("update_date")
    private java.util.Date updateDate;
    /**
     * 更新者       db_column: update_user
     * 
     * 
     * 
     */ 
    @Column("update_user")
    private java.lang.Long updateUser;
    /**
     * 销售数量       db_column: sell_cnt
     * 
     * 
     * 
     */ 
    @Column("sell_cnt")
    private java.lang.Integer sellCnt;
    /**
     * 领取数量       db_column: distribute_cnt
     * 
     * 
     * 
     */ 
    @Column("distribute_cnt")
    private java.lang.Integer distributeCnt;
    /**
     * 终止时间       db_column: stop_date
     * 
     * 
     * 
     */ 
    @Column("stop_date")
    private java.util.Date stopDate;
    /**
     * 累计销售金额       db_column: all_sale_amount
     * 
     * 
     * 
     */ 
    @Column("all_sale_amount")
    private java.math.BigDecimal allSaleAmount;
    /**
     * 可分配金额       db_column: share_amount
     * 
     * 
     * 
     */ 
    @Column("share_amount")
    private java.math.BigDecimal shareAmount;
    /**
     * 激活通知 1:需要通知 / 0:不需通知       db_column: notify_flg
     * 
     * 
     * 
     */ 
    @Column("notify_flg")
    private java.lang.String notifyFlg;
    /**
     * 清算状态 0:为清算 1:已清算       db_column: clear_status
     * 
     * 
     * 
     */ 
    @Column("clear_status")
    private java.lang.Integer clearStatus;
    /**
     * 清算时间       db_column: clear_date
     * 
     * 
     * 
     */ 
    @Column("clear_date")
    private java.util.Date clearDate;
    /**
     * 强制发放 0:未知 1:发放，有剩余/2:无剩余不需发放       db_column: offline_flg
     * 
     * 
     * 
     */ 
    @Column("offline_flg")
    private java.lang.Integer offlineFlg;
    /**
     * 线下发放时间       db_column: offline_date
     * 
     * 
     * 
     */ 
    @Column("offline_date")
    private java.util.Date offlineDate;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("SupplierId",getSupplierId())
            .append("SaleKbn",getSaleKbn())
            .append("SaleNote",getSaleNote())
            .append("ProductId",getProductId())
            .append("ProductName",getProductName())
            .append("ProductImg",getProductImg())
            .append("ProductPrice",getProductPrice())
            .append("ProductCnt",getProductCnt())
            .append("EmpLevel",getEmpLevel())
            .append("EmpCnt",getEmpCnt())
            .append("EmpAvgCnt",getEmpAvgCnt())
            .append("EmpAvgAmount",getEmpAvgAmount())
            .append("LimitType",getLimitType())
            .append("LimitStart",getLimitStart())
            .append("LimitEnd",getLimitEnd())
            .append("Status",getStatus())
            .append("CreateDate",getCreateDate())
            .append("CreateUser",getCreateUser())
            .append("UpdateDate",getUpdateDate())
            .append("UpdateUser",getUpdateUser())
            .append("SellCnt",getSellCnt())
            .append("DistributeCnt",getDistributeCnt())
            .append("StopDate",getStopDate())
            .append("AllSaleAmount",getAllSaleAmount())
            .append("ShareAmount",getShareAmount())
            .append("NotifyFlg",getNotifyFlg())
            .append("ClearStatus",getClearStatus())
            .append("ClearDate",getClearDate())
            .append("OfflineFlg",getOfflineFlg())
            .append("OfflineDate",getOfflineDate())
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

	public java.lang.Integer getSaleKbn() {
		return saleKbn;
	}

	public void setSaleKbn(java.lang.Integer saleKbn) {
		this.saleKbn = saleKbn;
	}

	public java.lang.String getSaleNote() {
		return saleNote;
	}

	public void setSaleNote(java.lang.String saleNote) {
		this.saleNote = saleNote;
	}

	public java.lang.Long getProductId() {
		return productId;
	}

	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public java.lang.String getProductImg() {
		return productImg;
	}

	public void setProductImg(java.lang.String productImg) {
		this.productImg = productImg;
	}

	public java.math.BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(java.math.BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public java.lang.Integer getProductCnt() {
		return productCnt;
	}

	public void setProductCnt(java.lang.Integer productCnt) {
		this.productCnt = productCnt;
	}

	public java.lang.Integer getEmpLevel() {
		return empLevel;
	}

	public void setEmpLevel(java.lang.Integer empLevel) {
		this.empLevel = empLevel;
	}

	public java.lang.Integer getEmpCnt() {
		return empCnt;
	}

	public void setEmpCnt(java.lang.Integer empCnt) {
		this.empCnt = empCnt;
	}

	public java.math.BigDecimal getEmpAvgCnt() {
		return empAvgCnt;
	}

	public void setEmpAvgCnt(java.math.BigDecimal empAvgCnt) {
		this.empAvgCnt = empAvgCnt;
	}

	public java.math.BigDecimal getEmpAvgAmount() {
		return empAvgAmount;
	}

	public void setEmpAvgAmount(java.math.BigDecimal empAvgAmount) {
		this.empAvgAmount = empAvgAmount;
	}

	public java.lang.Integer getLimitType() {
		return limitType;
	}

	public void setLimitType(java.lang.Integer limitType) {
		this.limitType = limitType;
	}

	public java.lang.String getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(java.lang.String limitStart) {
		this.limitStart = limitStart;
	}

	public java.lang.String getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(java.lang.String limitEnd) {
		this.limitEnd = limitEnd;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
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

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public java.lang.Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(java.lang.Long updateUser) {
		this.updateUser = updateUser;
	}

	public java.lang.Integer getSellCnt() {
		return sellCnt;
	}

	public void setSellCnt(java.lang.Integer sellCnt) {
		this.sellCnt = sellCnt;
	}

	public java.lang.Integer getDistributeCnt() {
		return distributeCnt;
	}

	public void setDistributeCnt(java.lang.Integer distributeCnt) {
		this.distributeCnt = distributeCnt;
	}

	public java.util.Date getStopDate() {
		return stopDate;
	}

	public void setStopDate(java.util.Date stopDate) {
		this.stopDate = stopDate;
	}

	public java.math.BigDecimal getAllSaleAmount() {
		return allSaleAmount;
	}

	public void setAllSaleAmount(java.math.BigDecimal allSaleAmount) {
		this.allSaleAmount = allSaleAmount;
	}

	public java.math.BigDecimal getShareAmount() {
		return shareAmount;
	}

	public void setShareAmount(java.math.BigDecimal shareAmount) {
		this.shareAmount = shareAmount;
	}

	public java.lang.String getNotifyFlg() {
		return notifyFlg;
	}

	public void setNotifyFlg(java.lang.String notifyFlg) {
		this.notifyFlg = notifyFlg;
	}

	public java.lang.Integer getClearStatus() {
		return clearStatus;
	}

	public void setClearStatus(java.lang.Integer clearStatus) {
		this.clearStatus = clearStatus;
	}

	public java.util.Date getClearDate() {
		return clearDate;
	}

	public void setClearDate(java.util.Date clearDate) {
		this.clearDate = clearDate;
	}

	public java.lang.Integer getOfflineFlg() {
		return offlineFlg;
	}

	public void setOfflineFlg(java.lang.Integer offlineFlg) {
		this.offlineFlg = offlineFlg;
	}

	public java.util.Date getOfflineDate() {
		return offlineDate;
	}

	public void setOfflineDate(java.util.Date offlineDate) {
		this.offlineDate = offlineDate;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }
}
