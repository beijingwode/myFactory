package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.BrandProducttype;

public interface BrandProducttypeDao {

	void deleteById(Long id);

	List<BrandProducttype> findByBrandIdAndSupplierId(Map map);

	void deleteBySupplierId(Long supplierId);

}
