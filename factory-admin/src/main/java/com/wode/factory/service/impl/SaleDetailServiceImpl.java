
package com.wode.factory.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.SaleDetailDao;
import com.wode.factory.model.SaleDetail;
import com.wode.factory.model.SupplierCloseCmd;
import com.wode.factory.service.SaleDetailService;

@Service("SaleDetailService")
public class SaleDetailServiceImpl extends EntityServiceImpl<SaleDetail,Long> implements SaleDetailService {
	
	@Autowired
	private SaleDetailDao dao;

	@Override
	public SaleDetailDao getDao() {
		return dao;
	}

	@Override
	public void insert(SaleDetail entity) {
		dao.insert(entity);
	}

	@Override
	public List<SaleDetail> countByOrder(SupplierCloseCmd entity) {
		return dao.countByOrder(entity);
	}	

	@Override
	public List<SaleDetail> countByOrderEx(SupplierCloseCmd entity) {
		return dao.countByOrderEx(entity);
	}

	@Override
	public List<SaleDetail> countExchangeOrder(Date closeStart, Date closeEnd) {
		Map<String,Object> param= new HashMap<String, Object>();
		param.put("closeStart", closeStart);
		param.put("closeEnd", closeEnd);
		return dao.countExchangeOrder(param);
	}
}
