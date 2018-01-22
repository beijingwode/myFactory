
package com.wode.factory.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.CommissionRefundDetailDao;
import com.wode.factory.model.CommissionRefundDetail;
import com.wode.factory.model.SupplierCloseCmd;
import com.wode.factory.service.CommissionRefundDetailService;
import com.wode.factory.vo.SupplierSaleFuliVo;

@Service("CommissionRefundDetailService")
public class CommissionRefundDetailServiceImpl extends EntityServiceImpl<CommissionRefundDetail,Long> implements CommissionRefundDetailService {
	
	@Autowired
	private CommissionRefundDetailDao dao;

	@Override
	public CommissionRefundDetailDao getDao() {
		return dao;
	}

	@Override
	public void insert(CommissionRefundDetail entity) {
		dao.insert(entity);
	}

	@Override
	public List<CommissionRefundDetail> countByOrder(SupplierCloseCmd entity) {
		return dao.countByOrder(entity);
	}

	@Override
	public List<SupplierSaleFuliVo> countSupplierDaySaleFuli(String startTime, String endTime) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return dao.countSupplierDaySaleFuli(map);
	}
	
	@Override
	public List<CommissionRefundDetail> getByRefundId(Long commissionRefundId) {
		return dao.getByRefundId(commissionRefundId);
	}	
}
