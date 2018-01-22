package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.mongo.MongoBaseDao;
import com.wode.common.mongo.MongoBaseService;
import com.wode.factory.dao.ApprShopMongoDao;
import com.wode.factory.model.ApprShop;
import com.wode.factory.service.ApprShopMongoService;
@Service("apprShopMongoService")
public class ApprShopMongoServiceImpl extends MongoBaseService<ApprShop> implements ApprShopMongoService{
	
	@Autowired
	private ApprShopMongoDao apprShopMongoDao;
	
	@Override
	public MongoBaseDao<ApprShop> getMongoDao() {
		return apprShopMongoDao;
	}
	
	@Override
	public void save(ApprShop model) {
		this.getMongoDao().insert(model);
	}


}
