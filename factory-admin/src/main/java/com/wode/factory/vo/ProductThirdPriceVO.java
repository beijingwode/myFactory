/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;

import com.wode.factory.model.ProductThirdPrice;


public class ProductThirdPriceVO extends  ProductThirdPrice{
    private static final long serialVersionUID = 5454155825314635342L;
    
    private String supplierName;//供应商名称
    private String proName;  //自定义商品分类ID
    
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}

}

