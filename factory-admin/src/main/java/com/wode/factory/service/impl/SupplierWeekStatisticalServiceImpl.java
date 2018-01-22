package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.SupplierWeekStatisticalDao;
import com.wode.factory.model.SupplierWeekStatistical;
import com.wode.factory.service.SupplierWeekStatisticalService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("SupplierWeekStatisticalService")
public class SupplierWeekStatisticalServiceImpl extends FactoryEntityServiceImpl<SupplierWeekStatistical> implements SupplierWeekStatisticalService {
	@Autowired
	SupplierWeekStatisticalDao SupplierWeekStatisticalMapper;
	
	@Override
	public SupplierWeekStatisticalDao getDao() {
		return SupplierWeekStatisticalMapper;
	}


	@Override
	public Long getId(SupplierWeekStatistical entity) {
		return entity.getId();
	}

	@Override
	public void setId(SupplierWeekStatistical entity, Long id) {
		entity.setId(id);
	}

}
