package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.SupplierCategory;

/**
 *
 */
public interface SupplierCategoryService {

	/**
	 * 更新佣金比例
	 *
	 */
	void updateCommissionRatio(SupplierCategory supplierCate);
	

	List<SupplierCategory> findByMap(Map<String, Object> map);

	void changShop(Long oldId,Long shopId);
}
