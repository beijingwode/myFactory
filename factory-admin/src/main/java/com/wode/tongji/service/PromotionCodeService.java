
package com.wode.tongji.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wode.factory.model.PromotionCode;

@Service("promotionCodeService")
public interface PromotionCodeService{

	/**
	 * 
	 * 功能说明：查询推广码
	 * 日期:	2015年9月9日
	 * 开发者:宋艳垒
	 *
	 * @param i
	 * @return
	 */
	List<PromotionCode> findList(Map<String, Object> parm);

	/**
	 * 
	 * 功能说明：根据状态查询记录条数
	 * 日期:	2015年9月10日
	 * 开发者:宋艳垒
	 *
	 * @param pc
	 * @return
	 */
	int findCount(PromotionCode pc);

	/**
	 * 
	 * 功能说明：提取代码
	 * 日期:	2015年9月11日
	 * 开发者:宋艳垒
	 *
	 * @param count
	 * @return
	 */
	List<PromotionCode> extract(int count,int status);

	int updateSelective(List<PromotionCode> list);

	void batchInsert();
	
	
}
