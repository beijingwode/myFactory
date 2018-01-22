package com.wode.factory.company.service;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.EntParamCode;


public interface EntParamCodeService extends BasePageService<EntParamCode> {
	
	/**
	 * 取得福利流水代码
	 * @return
	 */
	Map<String, EntParamCode> getBenefitFlowCode();
	/**
	 * 获取企业类型代码
	 * @return
	 */
	Map<String, EntParamCode> getEntTypeCode();
	/**
	 * 获取企业经营行业代码
	 * @return
	 */
	Map<String, EntParamCode> getEntIndustryCode();
	List<EntParamCode> getBanks();
	Map<String, EntParamCode> getAppFirstPrizeCode();
}
