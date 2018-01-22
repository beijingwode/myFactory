/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */


package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.EnterpriseUser;

public interface EnterpriseUserDao{
	/**
	 * 查询企业人数(有效的人数)
	 * @param enterpriseId
	 * @return
	 */
	public Integer findEnterprisePeopleNumber(Long enterpriseId);
	/**
	 * 查询企业人数(有效的人数)
	 * @param enterpriseId
	 * @return
	 */
	public Integer findEnterpriseActivePeopleCnt(Long enterpriseId);
	
	public Integer findEnterpriseActivePeopleCntDate(Map<String,Object> map);

	public List<EnterpriseUser> selectByModel(EnterpriseUser query);
	public void deleteBySupplierId(Long supplierId);
	public Integer findEnterpriseByDeviceCnt(Map<String, Long> paramPush);

	void delete(Long id);
}
