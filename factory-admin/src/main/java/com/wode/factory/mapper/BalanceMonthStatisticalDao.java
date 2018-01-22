package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.BalanceMonthStatistical;

/**
 * Created by zoln on 2015/7/24.
 */
public interface BalanceMonthStatisticalDao extends  FactoryBaseDao<BalanceMonthStatistical> {

	List<BalanceMonthStatistical> findList(Map<String, Object> params);
	/**
	 * 保存上个月的余额统计
	 * @param balanceMonth
	 */
	void insertSelective(BalanceMonthStatistical balanceMonth);
}
