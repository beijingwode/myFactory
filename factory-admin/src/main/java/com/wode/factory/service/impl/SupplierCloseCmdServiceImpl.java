
package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.mapper.SupplierCloseCmdDao;
import com.wode.factory.model.SupplierCloseCmd;
import com.wode.factory.service.SupplierCloseCmdService;

@Service("SupplierCloseCmdService")
public class SupplierCloseCmdServiceImpl extends EntityServiceImpl<SupplierCloseCmd,Long> implements SupplierCloseCmdService {
	
	@Autowired
	private SupplierCloseCmdDao dao;
	
	@Override
	public void insert(SupplierCloseCmd entity) {
		dao.insert(entity);
	}

	@Override
	public List<SupplierCloseCmd> find(SupplierCloseCmd entity) {
		return dao.find(entity);
	}

	@Override
	public EntityDao<SupplierCloseCmd, Long> getDao() {
		return dao;
	}	
}
