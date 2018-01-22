package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.ApprSupplierExit;

/**
 * Created by zoln on 2015/7/24.
 */
public interface ApprSupplierExitDao extends  FactoryBaseDao<ApprSupplierExit> {

	List<ApprSupplierExit> findCountsByMap(Map<String, Object> map);

	List<ApprSupplierExit> selectByMap(Map<String, Object> paramMap);

	List<ApprSupplierExit> getApprSupplierList(Map<String, Object> params);

	ApprSupplierExit getApprSupplierExitBySupplierId(Long supplierId);
}
