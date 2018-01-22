package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.SupplierCategory;

/**
 * Created by zoln on 2015/7/24.
 */
public interface SupplierCategoryMapper {

	/**根据id修改佣金比例
	 * @param supplierCate
	 * @return
	 */
	int updateCommissionRatio(SupplierCategory supplierCate);
	

	List<SupplierCategory> findByMap(Map<String, Object> map);
	
	void changShop(Map<String, Object> map);

	List<SupplierCategory> findByShopId(Long shopId);

	void deleteById(Long id);


	void deleteBySupplierId(Long supplierId);
}
