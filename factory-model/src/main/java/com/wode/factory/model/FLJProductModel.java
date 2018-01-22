package com.wode.factory.model;


public class FLJProductModel extends ProductSpecifications implements java.io.Serializable {
	
	private String source;//参数字段   图片地址
    
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
