package com.wode.factory.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.vo.SupplierExchangeProductVo;

/**
 * Created by zoln on 2015/7/24.
 */
public interface SupplierExchangeProductDao extends  FactoryBaseDao<SupplierExchangeProduct> {

	public List<SupplierExchangeProduct> selectForClear();
	
	/**
	 * 分页条件查询对账单信息
	 * @param map
	 * @return
	 */
	public List<SupplierExchangeProduct> findPageList(Map<String, Object> map);

	public void deleteBySupplierId(Long supplierId);

	public List<SupplierExchangeProductVo> findInfoPageList(Map<String, Object> params);

	public List<SupplierExchangeProduct> findListByMap(Map<String, Object> param);

	public List<SupplierExchangeProductVo> findProductBySupplierId(Map map);

	public List<SupplierExchangeProductVo> findInfoPageListEx(Map<String, Object> params);
}
