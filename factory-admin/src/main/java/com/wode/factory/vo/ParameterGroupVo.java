/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;

import com.wode.factory.model.ParameterGroup;



public class ParameterGroupVo extends ParameterGroup implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7199054552137908054L;
	private String parameterCategory;
	public String getParameterCategory() {
		return parameterCategory;
	}
	public void setParameterCategory(String parameterCategory) {
		this.parameterCategory = parameterCategory;
	}
	 
}

