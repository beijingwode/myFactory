package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.SupplierTemp;

public interface SupplierTempService {

	List<SupplierTemp> findAll();

	PageInfo<SupplierTemp> pageInfoList(Map<String, Object> params);

	SupplierTemp getById(Long id);

}
