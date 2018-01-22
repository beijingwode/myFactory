package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.mongo.MongoBaseDao;
import com.wode.common.mongo.MongoBaseService;
import com.wode.factory.dao.ApprProductMongoDao;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.service.ApprProductMongoService;

@Service("apprProductMongoService")
public class ApprProductMongoServiceImpl extends MongoBaseService<ApprProduct> implements ApprProductMongoService {
	
	@Autowired
	private ApprProductMongoDao apprProductMongoDao;
	
	@Override
	public MongoBaseDao<ApprProduct> getMongoDao() {
		return apprProductMongoDao;
	}
	
	@Override
	public void save(ApprProduct model) {
		this.getMongoDao().insert(model);
	}


}
