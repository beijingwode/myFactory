/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.mapper.SaleDurationParamDao;
import com.wode.factory.model.SaleDurationParam;
import com.wode.factory.service.SaleDurationParamService;
import com.wode.common.frame.base.BaseService;
import cn.org.rapid_framework.page.Page;

@Service("saleDurationParamService")
public class SaleDurationParamServiceImpl implements  SaleDurationParamService{
	@Autowired
	@Qualifier("saleDurationParamDao")
	private SaleDurationParamDao saleDurationParamDao;
	
	public EntityDao getEntityDao() {
		return this.saleDurationParamDao;
	}
	
	public Page findPage(SaleDurationParam query) {
		return saleDurationParamDao.findPage(query);
	}

	@Override
	public List<SaleDurationParam> findAll() {
		// TODO Auto-generated method stub
		return this.saleDurationParamDao.findAll();
	}

	@Override
	public SaleDurationParam selectByKey(String key) {
		// TODO Auto-generated method stub
		return this.saleDurationParamDao.selectByKey(key);
	}

}
