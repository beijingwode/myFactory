package com.wode.factory.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.SupplierExchangeProductDao;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.service.SupplierExchangeProductService;
import com.wode.factory.vo.SupplierExchangeProductVo;
import com.wode.factory.vo.SupplierTransferVo;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("supplierExchangeProductService")
public class SupplierExchangeProductServiceImpl extends FactoryEntityServiceImpl<SupplierExchangeProduct> implements SupplierExchangeProductService {
	@Autowired
	SupplierExchangeProductDao dao;
	
	@Override
	public SupplierExchangeProductDao getDao() {
		return dao;
	}


	@Override
	public Long getId(SupplierExchangeProduct entity) {
		return entity.getId();
	}

	@Override
	public void setId(SupplierExchangeProduct entity, Long id) {
		entity.setId(id);
	}


	@Override
	public List<SupplierExchangeProduct> selectForClear() {
		return dao.selectForClear();
	}


	@Override
	public PageInfo<SupplierExchangeProduct> findPageList(Map<String, Object> map) {

		PageHelper.startPage(map);
		List<SupplierExchangeProduct> listSaleBill = this.getDao().findPageList(map);
		return new PageInfo(listSaleBill);
	}


	@Override
	public PageInfo<SupplierExchangeProductVo> findInfoPageList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SupplierExchangeProductVo> listSaleBill = dao.findInfoPageList(params);
		return new PageInfo(listSaleBill);
	}


	@Override
	public List<SupplierExchangeProduct> findListByMap(Map<String, Object> param) {
		return dao.findListByMap(param);
	}


	@Override
	public List<SupplierExchangeProductVo> findProductBySupplierId(Long id) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("date",new Date());
		return dao.findProductBySupplierId(map);
	}


	@Override
	public PageInfo<SupplierExchangeProductVo> findInfoPageListEx(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SupplierExchangeProductVo> listSaleBill = dao.findInfoPageListEx(params);
		return new PageInfo(listSaleBill);
	}
}
