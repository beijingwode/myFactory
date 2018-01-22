package com.wode.factory.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.model.GroupBuyProduct;
import com.wode.factory.service.GroupBuyProductService;

@Service("groupBuyProductService")
public class GroupBuyProductServiceImpl implements GroupBuyProductService {
	
	private static Logger logger = LoggerFactory.getLogger(GroupBuyProductServiceImpl.class);
	@Autowired
	private Dao dao;
	
	
	public List<GroupBuyProduct> findByGroupId(Long groupId) {
		return dao.query(GroupBuyProduct.class,Cnd.where("group_id","=",groupId));
	}


	@Override
	public Integer findByCount(Long groupId) {
		return dao.count(GroupBuyProduct.class, Cnd.where("group_id","=",groupId));
	}
	
}
