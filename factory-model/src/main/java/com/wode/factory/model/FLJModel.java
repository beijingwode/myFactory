package com.wode.factory.model;

import java.util.List;

public class FLJModel extends Shop implements java.io.Serializable {
	
	/**
     * 商品总数
     */
    private int productNum;
    
    /**
     * suk商品
     */
    private List<FLJProductModel> productSpecifications;
    
    
    public List<FLJProductModel> getProductSpecifications() {
		return productSpecifications;
	}

	public void setProductSpecifications(
			List<FLJProductModel> productSpecifications) {
		this.productSpecifications = productSpecifications;
	}
	
	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

}
