package com.wode.tongji.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.service.impl.FactoryEntityServiceImpl;
import com.wode.tongji.mapper.ManagerBusinessMapper;
import com.wode.tongji.model.ManagerBusiness;
import com.wode.tongji.service.ManagerBusinessService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("managerBusinessService")
public class ManagerBusinessServiceImpl extends FactoryEntityServiceImpl<ManagerBusiness> implements ManagerBusinessService {
	@Autowired
	ManagerBusinessMapper dao;
	
	@Override
	public ManagerBusinessMapper getDao() {
		return dao;
	}

	@Override
	public Long getId(ManagerBusiness entity) {
		return -1L;	// 自增列 无需设置id
	}

	@Override
	public void setId(ManagerBusiness entity, Long id) {
		entity.setId(id);
	}
}
