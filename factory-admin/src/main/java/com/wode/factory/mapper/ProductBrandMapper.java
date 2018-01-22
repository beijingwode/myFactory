package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.ProductBrand;
import com.wode.factory.vo.ProductBrandVo;

/**
 * Created by zoln on 2015/7/24.
 */
public interface ProductBrandMapper {
	List<ProductBrand> findByMap(Map<String, Object> map);

	/**
	 * 
	 * 功能说明：查询属性列表
	 *
	 * @param params
	 * @return
	 */
	public List<ProductBrandVo> findList(Map<String, Object> params);
	public List<ProductBrandVo> findListLawyer(Map<String, Object> params);
	
	public String getCategorysByBrand(Map<String, Object> params);
	public Integer getCountBySupplier(Long supplierId);
	public Integer getCountBySupplierForSale(Long supplierId);
	public Integer getCountBySupplierForSaleDate(Map<String,Object> map);
	
	void changShop(Map<String, Object> map);
	void setLevel(Map<String, Object> map);
	void setCreateDate(Map<String, Object> map);

	List<ProductBrand> findByShopId(Long shopId);

	void deleteById(Long id);

	void deleteBySupplierId(Long supplierId);
	
}
