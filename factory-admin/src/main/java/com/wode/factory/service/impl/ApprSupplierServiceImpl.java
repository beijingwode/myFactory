package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.mapper.ApprSupplierDao;
import com.wode.factory.model.ApprSupplier;
import com.wode.factory.service.ApprSupplierService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("apprSupplierServiceImpl")
public class ApprSupplierServiceImpl extends EntityServiceImpl<ApprSupplier,Long> implements ApprSupplierService {

	@Autowired
	ApprSupplierDao apprSupplierMapper;
	
	@Override
	@Transactional(readOnly = true)
	public PageInfo<ApprSupplier> findApprSupplier(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ApprSupplier> ordersList = apprSupplierMapper.findApprSupplier(params);
		return new PageInfo(ordersList);
	}

	@Override
	@Transactional(readOnly = true)
	public PageInfo<ApprSupplier> findApprSupplierEmpty(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ApprSupplier> ordersList = apprSupplierMapper.findApprSupplierEmpty(params);
		return new PageInfo(ordersList);
	}

	@Override
	public EntityDao<ApprSupplier, Long> getDao() {
		return apprSupplierMapper;
	}

	@Override
	public void insertSupplier(ApprSupplier appr) {
		apprSupplierMapper.insertSupplier(appr);
	}
	@Override
	public void updateSupplier(ApprSupplier appr) {
		apprSupplierMapper.updateSupplier(appr);
	}
}
