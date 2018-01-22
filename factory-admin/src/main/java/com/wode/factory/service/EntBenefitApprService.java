/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.EntBenefitAppr;
import com.wode.factory.model.PageTypeSetting;
import com.wode.factory.model.SaleDetail;
import com.wode.factory.vo.EntBenefitApprVO;

@Service("entBenefitApprService")
public interface EntBenefitApprService{

	/**
	 * 
	 * 功能说明：分页查询
	 * 日期:	2015年9月14日
	 * 开发者:宋艳垒
	 *
	 * @param params
	 * @return
	 */
	PageInfo<EntBenefitApprVO> findPage(Map<String, Object> params);

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
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	EntBenefitAppr getById(Long id);
	

	/**
	 * 功能说明：向数据库插入数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param EntBenefitAppr
	 */
	void insert(EntBenefitAppr pojo);
}
