

package com.wode.tongji.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.PromotionCode;


public interface PromotionCodeMapper {

	/**
	 * 
	 * 功能说明：查询指定个数的推广码
	 * 日期:	2015年9月9日
	 * 开发者:宋艳垒
	 *
	 * @param i 查询数量
	 * @param status 
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
	 * 功能说明：更新操作
	 * 日期:	2015年9月11日
	 * 开发者:宋艳垒
	 *
	 * @param pc
	 * @return
	 */
	int update(PromotionCode pc);

	
	int updateSelective(List<PromotionCode> list);

	void insert(PromotionCode pc);

}
