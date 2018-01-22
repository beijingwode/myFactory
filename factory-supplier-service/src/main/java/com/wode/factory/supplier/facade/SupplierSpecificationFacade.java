package com.wode.factory.supplier.facade;

import java.util.List;

import com.wode.factory.model.SpecificationValue;
import com.wode.factory.model.SupplierSpecification;

public interface SupplierSpecificationFacade {

	/**
	 * 保存商家自定义规格
	 * 
	 * @param supplierId
	 * @param categoryId
	 * @param specification1
	 * @param values1
	 * @param specification2
	 * @param values2
	 * @return
	 */
	void SaveSupplierSpecification(Long supplierId, Long categoryId, SupplierSpecification specification1,
			List<SpecificationValue> values1, SupplierSpecification specification2, List<SpecificationValue> values2);
	
	/**
	 * 规格数据数据copy
	 * @param supplierId
	 * @param oldId
	 * @param newId
	 * @param productId
	 */
	void copySupplierSpecification(Long supplierId, Long oldId,Long newId,Long productId,Integer type);

}
