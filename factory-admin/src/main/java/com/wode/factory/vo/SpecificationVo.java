/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;

import com.wode.factory.model.Specification;





public class SpecificationVo extends Specification implements java.io.Serializable{
    /**
     * 规格类别
     */
    private String specificationType;

	public String getSpecificationType() {
		return specificationType;
	}

	public void setSpecificationType(String specificationType) {
		this.specificationType = specificationType;
	}
    
}

