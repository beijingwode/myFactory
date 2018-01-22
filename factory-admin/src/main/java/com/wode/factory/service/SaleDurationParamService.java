/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.SaleDurationParam;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

@Service("saleDurationParamService")
public interface SaleDurationParamService{
	
	public EntityDao getEntityDao() ;
	
	public Page findPage(SaleDurationParam query);
	
	public List<SaleDurationParam> findAll();
	
	public SaleDurationParam selectByKey(String key);
}
