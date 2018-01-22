
package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.CommissionRefundDao;
import com.wode.factory.model.CommissionRefund;
import com.wode.factory.service.CommissionRefundService;

@Service("CommissionRefundService")
public class CommissionRefundServiceImpl extends EntityServiceImpl<CommissionRefund,Long> implements CommissionRefundService {
	
	@Autowired
	private CommissionRefundDao dao;

	@Override
	public CommissionRefundDao getDao() {
		return dao;
	}

	@Override
	public void insert(CommissionRefund entity) {
		dao.insert(entity);
	}

	@Override
	public CommissionRefund sumBydetail(Long id) {
		return getDao().sumBydetail(id);
	}


}
