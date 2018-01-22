package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ApprSupplier;

/**
 * Created by zoln on 2015/7/24.
 */
public interface ApprSupplierDao extends  EntityDao<ApprSupplier,Long> {

	List<ApprSupplier> findApprSupplier(Map<String, Object> map);
	List<ApprSupplier> findApprSupplierEmpty(Map<String, Object> map);	
	void insertSupplier(ApprSupplier appr);
	void updateSupplier(ApprSupplier appr);
	List<ApprSupplier> findByCreatTime(Map<String, Object> map);
	void deleteBySupplierId(Long supplierId);
}
