package com.wode.factory.mapper;

import java.util.List;

import com.wode.tongji.model.CommissionSetting;


public interface CommissionSettingDao {

	/**
	 * 
	 * 功能说明：根据id查询
	 * 日期:	2015年5月15日
	 * 开发者:宋艳垒
	 *
	 * @param id
	 * @return
	 */
	public List<CommissionSetting> selectById(Long id);

}