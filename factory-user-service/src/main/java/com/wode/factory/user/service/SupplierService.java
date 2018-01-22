package com.wode.factory.user.service;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.Supplier;

public interface SupplierService {

	List<Supplier> findByManagerId(Map<String, String> queryMap);

}
