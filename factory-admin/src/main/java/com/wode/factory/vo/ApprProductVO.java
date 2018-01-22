/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;

import com.wode.factory.model.ApprProduct;


public class ApprProductVO extends  ApprProduct{
    

    private String supplierName;//供应商名称

    /**
     * ********************以下字段只供显示******************
     */
   

    private String storeCategoryId;  //自定义商品分类ID

    private String opinion;
	public String getOpinion() {
		return opinion;
	}


	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public void setStoreCategoryId(String storeCategoryId) {
		this.storeCategoryId = storeCategoryId;
	}

	@Override
	public String getStoreCategoryId() {
		return storeCategoryId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	

}

