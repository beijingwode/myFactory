package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.vo.SupplierExchangeProductVo;

/**
 *
 */
public interface SupplierExchangeProductService extends FactoryEntityService<SupplierExchangeProduct> {

	public List<SupplierExchangeProduct> selectForClear();
	public PageInfo<SupplierExchangeProduct> findPageList(Map<String, Object> map);
	public PageInfo<SupplierExchangeProductVo> findInfoPageList(Map<String, Object> params);
	public List<SupplierExchangeProduct> findListByMap(Map<String, Object> param);
	public List<SupplierExchangeProductVo> findProductBySupplierId(Long id);
	public PageInfo<SupplierExchangeProductVo> findInfoPageListEx(Map<String, Object> params);
}
