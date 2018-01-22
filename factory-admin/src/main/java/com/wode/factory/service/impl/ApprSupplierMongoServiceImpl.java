package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.mongo.MongoBaseDao;
import com.wode.common.mongo.MongoBaseService;

import com.wode.factory.dao.ApprSupplierMongoDao;
import com.wode.factory.model.ApprSupplier;
import com.wode.factory.service.ApprSupplierMongoService;
@Service("apprSupplierMongoService")
public class ApprSupplierMongoServiceImpl extends MongoBaseService<ApprSupplier> implements ApprSupplierMongoService {
	
	@Autowired
	private ApprSupplierMongoDao apprSupplierMongoDao;
	
	@Override
	public MongoBaseDao<ApprSupplier> getMongoDao() {
		return apprSupplierMongoDao;
	}
	
	
	@Override
	public void save(ApprSupplier model) {
		this.getMongoDao().insert(model);
	}

	

}
