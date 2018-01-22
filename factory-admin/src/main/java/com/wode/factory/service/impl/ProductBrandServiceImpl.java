package com.wode.factory.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.ProductBrandMapper;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.service.ProductBrandService;
import com.wode.factory.vo.ProductBrandVo;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("productBrandService")
public class ProductBrandServiceImpl implements ProductBrandService {

	@Autowired
	ProductBrandMapper productBrandMapper;

	@Override
	public List<ProductBrand> findByMap(Map<String, Object> map) {
		return productBrandMapper.findByMap(map);
	}

	@Override
	public void changShop(Long oldId,Long shopId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("oldId", oldId);
		map.put("shopId", shopId);
		
		productBrandMapper.changShop(map);
	}

	@Override
	public String getCategorysByBrand(Long supplierId,Long brandId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("supplierId", supplierId);
		map.put("brandId", brandId);
		
		return productBrandMapper.getCategorysByBrand(map);
	}
	
	@Override
	public Integer getCountBySupplier(Long supplierId) {
		return productBrandMapper.getCountBySupplier(supplierId);
	}

	@Override
	public Integer getCountBySupplierForSale(Long supplierId) {
		return productBrandMapper.getCountBySupplierForSale(supplierId);
	}

	@Override
	public Integer getCountBySupplierForSaleDate(Long supplierId,Date startDate,Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return productBrandMapper.getCountBySupplierForSaleDate(map);
	}

	@Override
	public PageInfo<ProductBrandVo> findList(Map<String, Object> params) {

		PageHelper.startPage(params);
		List<ProductBrandVo> list = productBrandMapper.findList(params);
		return new PageInfo<ProductBrandVo>(list);
	}

	@Override
	public PageInfo<ProductBrandVo> findListLawyer(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ProductBrandVo> list = productBrandMapper.findListLawyer(params);
		return new PageInfo<ProductBrandVo>(list);
	}
	@Override
	public void setLevel(Long categoryId, Long brandId,String updateBy) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("categoryId", categoryId);
		map.put("brandId", brandId);
		map.put("updateBy", updateBy);
		map.put("updateDate", new Date());
		
		productBrandMapper.setLevel(map);
	}

	@Override
	public void setCreateDate(Long shopId, Date createDate) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("shopId", shopId);
		map.put("createDate", createDate);
		
		productBrandMapper.setCreateDate(map);
	}

	
		
}
