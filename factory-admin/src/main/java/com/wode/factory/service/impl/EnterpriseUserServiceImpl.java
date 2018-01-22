/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.EnterpriseUserDao;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.service.EnterpriseUserService;

@Service("enterpriseUserService")
public class EnterpriseUserServiceImpl implements EnterpriseUserService{
	@Autowired
	private EnterpriseUserDao enterpriseUserDao;

	@Override
	public Integer findEnterprisePeopleNumber(Long enterpriseId) {
		return this.enterpriseUserDao.findEnterprisePeopleNumber(enterpriseId);
	}

	@Override
	public Integer findEnterpriseActivePeopleCnt(Long enterpriseId) {
		return this.enterpriseUserDao.findEnterpriseActivePeopleCnt(enterpriseId);
	}

	@Override
	public Integer findEnterpriseActivePeopleCntDate(Long enterpriseId,Date startDate,Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enterpriseId", enterpriseId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return this.enterpriseUserDao.findEnterpriseActivePeopleCntDate(map);
	}

	@Override
	public List<EnterpriseUser> selectByModel(EnterpriseUser query) {
		return enterpriseUserDao.selectByModel(query);
	}

	@Override
	public Integer findEnterpriseByDeviceCnt(Map<String, Long> paramPush) {
		return enterpriseUserDao.findEnterpriseByDeviceCnt(paramPush);
	}

	@Override
	public void delete(Long id) {
		enterpriseUserDao.delete(id);
	}

}
