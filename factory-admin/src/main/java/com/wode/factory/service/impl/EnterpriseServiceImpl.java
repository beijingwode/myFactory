/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.EnterpriseDao;
import com.wode.factory.model.Enterprise;
import com.wode.factory.service.EnterpriseService;
import com.wode.factory.vo.EnterpriseVo;

@Service("enterpriseService")
public class EnterpriseServiceImpl implements EnterpriseService{
	@Autowired
	private EnterpriseDao enterpriseDao;

	@Override
	public EnterpriseVo getById(Long id) {
		return enterpriseDao.getById(id);
	}

	@Override
	public List<Enterprise> findeAllEnterprise() {
		// TODO Auto-generated method stub
		return this.enterpriseDao.findeAllEnterprise();
	}
	
	
}
