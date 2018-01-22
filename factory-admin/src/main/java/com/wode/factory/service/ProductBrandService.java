package com.wode.factory.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.vo.ProductBrandVo;

/**
 *
 */
public interface ProductBrandService {

	List<ProductBrand> findByMap(Map<String, Object> map);

	/**
	 * 
	 * 功能说明：查询商品列表（带分页）
	 * @param params
	 * @return
	 */
	PageInfo<ProductBrandVo> findList(Map<String, Object> params);
	PageInfo<ProductBrandVo> findListLawyer(Map<String, Object> params);
	
	void changShop(Long oldId,Long shopId);
	void setLevel(Long categoryId,Long brandId,String updateBy);
	void setCreateDate(Long shopId,Date createDate);
	public Integer getCountBySupplier(Long supplierId);
	public Integer getCountBySupplierForSale(Long supplierId);
	public String getCategorysByBrand(Long supplierId,Long brandId);
	public Integer getCountBySupplierForSaleDate(Long supplierId,Date startDate,Date endDate);
	
}
