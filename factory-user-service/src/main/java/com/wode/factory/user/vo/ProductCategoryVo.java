package com.wode.factory.user.vo;

import com.wode.factory.model.ProductCategory;

public class ProductCategoryVo extends ProductCategory{
	private Long parentId;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}