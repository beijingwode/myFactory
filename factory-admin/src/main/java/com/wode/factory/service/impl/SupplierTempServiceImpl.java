package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.SupplierTempDao;
import com.wode.factory.model.SupplierTemp;
import com.wode.factory.service.SupplierTempService;
import com.wode.factory.vo.SupplierVo;
@Service("supplierTempService")
public class SupplierTempServiceImpl implements SupplierTempService {
	@Autowired
	private SupplierTempDao supplierTempDao;

	@Override
	public List<SupplierTemp> findAll() {
		
		return supplierTempDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public PageInfo<SupplierTemp> pageInfoList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SupplierTemp> list = supplierTempDao.pageInfoList(params);
		//List<SupplierVo> ordersList = supplierMapper.findSupplierVo(params);
		return new PageInfo(list);
	}
	@Override
	public SupplierTemp getById(Long id) {
		return supplierTempDao.getById(id);
	}
}
