package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.ApprShop;

/**
 *
 */
public interface ApprShopService extends EntityService<ApprShop,Long> {

	/**
	 * @param query 查询条件存储对象, 目前紧支持时间参数, startDate(开始时间) endDate(结束时间)
	 * @return
	 */
	public PageInfo<ApprShop> findApprShop(Map<String, Object> params);
	public List<ApprShop> findApprShopEmpty(Map<String, Object> map);
	
	void insertShop(ApprShop appr);
	void updateShop(ApprShop appr);
}
