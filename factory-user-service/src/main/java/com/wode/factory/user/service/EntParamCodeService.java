package com.wode.factory.user.service;

import java.math.BigDecimal;
import java.util.Map;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.EntParamCode;



public interface EntParamCodeService extends EntityService<EntParamCode,Long>{
	
	/**
	 * 取得福利流水代码
	 * @return
	 */
	public Map<String, EntParamCode> getBenefitFlowCode();
	/**
	 * 获取企业类型代码
	 * @return
	 */
	public Map<String, EntParamCode> getEntTypeCode();
	/**
	 * 获取企业经营行业代码
	 * @return
	 */
	public Map<String, EntParamCode> getEntIndustryCode();
	
	public Map<String, EntParamCode> getAppFirstPrizeCode();
	
	/**
	 * 获取换领账户流水代码
	 */
	public Map<String,EntParamCode> getPurchasedFlowCode();
	
	/**
	 * 内购券补贴标准
	 */
	public BigDecimal getBenefitSubsidy();
	
	/**
	 * 团长 确认收货补贴
	 */
	public BigDecimal getGroupMasterPrize();
}
