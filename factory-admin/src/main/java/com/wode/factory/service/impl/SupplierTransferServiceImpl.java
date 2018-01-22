package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.SupplierTransferDao;
import com.wode.factory.model.SupplierTransfer;
import com.wode.factory.service.SupplierTransferService;
import com.wode.factory.vo.SupplierTransferVo;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("supplierTransferService")
public class SupplierTransferServiceImpl extends FactoryEntityServiceImpl<SupplierTransfer> implements SupplierTransferService {

	@Autowired
	SupplierTransferDao dao;
//
//	@Override
//	@Transactional(readOnly = true)
//	public PageInfo<SupplierTransfer> findSupplierTransfer(Map<String, Object> params) {
//		PageHelper.startPage(params);
//		List<SupplierTransfer> ordersList = SupplierTransferMapper.findSupplierTransfer(params);
//		return new PageInfo(ordersList);
//	}

	@Override
	public SupplierTransferDao getDao() {
		return dao;
	}

	@Override
	public PageInfo<SupplierTransferVo> getPage(Map<String, Object> params) {

		PageHelper.startPage(params);
		List<SupplierTransferVo> listSaleBill = this.getDao().findPageList(params);
		return new PageInfo(listSaleBill);
	}

	@Override
	public Long getId(SupplierTransfer entity) {
		return entity.getId();
	}

	@Override
	public void setId(SupplierTransfer entity, Long id) {
		entity.setId(id);
	}
}
