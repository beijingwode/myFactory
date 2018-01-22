/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.EntParamCodeVo;

@Service("entParamCodeService")
public interface EntParamCodeService{

	public void insert(EntParamCode entity);
	public void delete(Long id);
	public void update(EntParamCode entity);
	public List<EntParamCode> selectByModel(EntParamCode model);
	public List<EntParamCodeVo> selectBanks();
	/**
	 * 取得福利流水代码
	 * @return
	 */
	public Map<String, EntParamCode> getBenefitFlowCode();
}
