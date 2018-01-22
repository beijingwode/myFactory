/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wode.factory.model.EnterpriseUser;


@Service("enterpriseUserService")
public interface EnterpriseUserService{
	public Integer findEnterprisePeopleNumber(Long enterpriseId);
	public Integer findEnterpriseActivePeopleCnt(Long enterpriseId);

	public Integer findEnterpriseActivePeopleCntDate(Long enterpriseId,Date startDate,Date endDate);
	public List<EnterpriseUser> selectByModel(EnterpriseUser query);
	public Integer findEnterpriseByDeviceCnt(Map<String, Long> paramPush);

	void delete(Long id);
}
