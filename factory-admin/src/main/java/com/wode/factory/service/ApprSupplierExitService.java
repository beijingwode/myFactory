package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.ApprSupplierExit;

/**
 *
 */
public interface ApprSupplierExitService extends FactoryEntityService<ApprSupplierExit> {

	List<ApprSupplierExit> findCountsByMap(Map<String, Object> map);

	PageInfo selectByMap(Map<String, Object> paramMap, String userName);

	void deleteSupplierCorrelationMsg(Long supplierId);

	PageInfo getApprSupplierList(Map<String, Object> params,String userName);

	ApprSupplierExit getApprSupplierExitBySupplierId(Long supplierId);

}
