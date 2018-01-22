package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.SupplierTemp;

public interface SupplierTempDao {

	List<SupplierTemp> findAll();

	List<SupplierTemp> pageInfoList(Map<String, Object> params);

	SupplierTemp getById(Long id);

}
