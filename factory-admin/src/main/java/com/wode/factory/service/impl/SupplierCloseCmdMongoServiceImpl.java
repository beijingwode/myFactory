package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.mongo.MongoBaseDao;
import com.wode.common.mongo.MongoBaseService;
import com.wode.factory.dao.SupplierCloseCmdMongoDao;
import com.wode.factory.model.SupplierCloseCmd;
import com.wode.factory.service.SupplierCloseCmdMongoService;

@Service("supplierCloseCmdMongoService")
public class SupplierCloseCmdMongoServiceImpl extends MongoBaseService<SupplierCloseCmd> implements SupplierCloseCmdMongoService {

	@Autowired
	private SupplierCloseCmdMongoDao supplierCloseCmdMongoDao;

	@Override
	public MongoBaseDao<SupplierCloseCmd> getMongoDao() {
		return supplierCloseCmdMongoDao;
	}
	
	@Override
	public void save(SupplierCloseCmd model) {
		this.getMongoDao().insert(model);
	}

}
