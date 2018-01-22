package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.CategoryWeekStatisticalDao;
import com.wode.factory.mapper.FactoryBaseDao;
import com.wode.factory.mapper.SupplierWeekStatisticalDao;
import com.wode.factory.model.CategoryWeekStatistical;
import com.wode.factory.model.SupplierWeekStatistical;
import com.wode.factory.service.CategoryWeekStatisticalService;
import com.wode.factory.service.SupplierWeekStatisticalService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("CategoryWeekStatisticalService")
public class CategoryWeekStatisticalServiceImpl extends FactoryEntityServiceImpl<CategoryWeekStatistical> implements CategoryWeekStatisticalService {
	@Autowired
	CategoryWeekStatisticalDao categoryWeekStatisticalMapper;

	@Override
	public FactoryBaseDao<CategoryWeekStatistical> getDao() {
		// TODO Auto-generated method stub
		return categoryWeekStatisticalMapper;
	}

	@Override
	public Long getId(CategoryWeekStatistical entity) {
		return entity.getId();
	}

	@Override
	public void setId(CategoryWeekStatistical entity, Long id) {
		entity.setId(id);
	}
	

}
