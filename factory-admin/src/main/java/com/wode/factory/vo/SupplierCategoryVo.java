package com.wode.factory.vo;

import com.wode.factory.model.SupplierCategory;


public class SupplierCategoryVo extends SupplierCategory {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5277591599565114031L;
	/**
	 * 分类名称
	 */
	private String categoryName;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
    
}
