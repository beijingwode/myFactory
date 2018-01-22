package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.BalanceMonthStatistical;

public interface BalanceMonthStatisticalService extends EntityService<BalanceMonthStatistical,Long>  {
	public PageInfo findList(Map<String, Object> params);

}
