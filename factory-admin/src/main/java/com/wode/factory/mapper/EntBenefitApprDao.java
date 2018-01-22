/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.EntBenefitAppr;
import com.wode.factory.vo.EntBenefitApprVO;


public interface EntBenefitApprDao{

	/**
	 * 
	 * 功能说明：分页查询
	 * 日期:	2015年9月14日
	 * 开发者:宋艳垒
	 *
	 * @param params
	 * @return
	 */
	List<EntBenefitApprVO> findPage(Map<String, Object> params);

	/**
	 * 
	 * 功能说明：更新
	 * 日期:	2015年9月15日
	 * 开发者:宋艳垒
	 *
	 * @param pojo
	 * @return
	 */
	int updateSelect(EntBenefitAppr pojo);
	
	EntBenefitAppr getById(Long id);
	

	void insert(EntBenefitAppr entity);
}
