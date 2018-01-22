package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.CommissionRefundCashDao;
import com.wode.factory.mapper.FactoryBaseDao;
import com.wode.factory.model.CommissionRefundCash;
import com.wode.factory.service.CommissionRefundCashService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("commissionRefundCashService")
public class CommissionRefundCashServiceImpl extends FactoryEntityServiceImpl<CommissionRefundCash> implements CommissionRefundCashService {
	@Autowired
	CommissionRefundCashDao dao;

	@Override
	public List<CommissionRefundCash> selectByModel(CommissionRefundCash query) {
		return getDao().selectByModel(query);
	}

	@Override
	public FactoryBaseDao<CommissionRefundCash> getDao() {
		return dao;
	}

	@Override
	public Long getId(CommissionRefundCash entity) {
		return entity.getId();
	}

	@Override
	public void setId(CommissionRefundCash entity, Long id) {
		entity.setId(id);
	}

}
