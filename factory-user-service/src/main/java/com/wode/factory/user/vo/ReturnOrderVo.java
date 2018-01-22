package com.wode.factory.user.vo;

import com.wode.factory.model.Returnorder;

import java.math.BigDecimal;

/**
 * Created by Bing King on 2015/4/1.
 */
public class ReturnOrderVo extends Returnorder {
    private String productName;

    private BigDecimal realPrice;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }
}
