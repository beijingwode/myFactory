/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */


package com.wode.factory.mapper;

import java.util.List;

import com.wode.factory.model.Enterprise;
import com.wode.factory.vo.EnterpriseVo;


public interface EnterpriseDao{

	/**
	 * 
	 * 功能说明：根据id查询
	 * 日期:	2015年9月15日
	 * 开发者:宋艳垒
	 *
	 * @param id
	 * @return
	 */
	EnterpriseVo getById(Long id);
	
	/**
	 * 查询全部的企业信息
	 * @return
	 */
	List<Enterprise> findeAllEnterprise();
}
