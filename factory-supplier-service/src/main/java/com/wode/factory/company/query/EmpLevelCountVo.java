package com.wode.factory.company.query;

import java.io.Serializable;

public class EmpLevelCountVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1655301811860946399L;
    /**
     * 福利级别
     */
    private Integer welfareLevel;
    /**
     * 数量
     */
    private Integer levelCount;
    /**
     * 企业id
     */
    private Long enterpriseId;
    
	public Integer getWelfareLevel() {
		return welfareLevel;
	}
	public void setWelfareLevel(Integer welfareLevel) {
		this.welfareLevel = welfareLevel;
	}
	public Integer getLevelCount() {
		return levelCount;
	}
	public void setLevelCount(Integer levelCount) {
		this.levelCount = levelCount;
	}
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
    
}
