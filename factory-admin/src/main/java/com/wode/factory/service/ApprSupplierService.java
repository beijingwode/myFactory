package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.ApprSupplier;

/**
 *
 */
public interface ApprSupplierService extends EntityService<ApprSupplier,Long> {

	/**
	 * @param query 查询条件存储对象, 目前紧支持时间参数, startDate(开始时间) endDate(结束时间)
	 * @return
	 */
	public PageInfo<ApprSupplier> findApprSupplier(Map<String, Object> params);
	public PageInfo<ApprSupplier> findApprSupplierEmpty(Map<String, Object> params);
	
	void insertSupplier(ApprSupplier appr);
	void updateSupplier(ApprSupplier appr);
}
