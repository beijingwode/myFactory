package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.SuborderitemDao;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.service.SuborderItemService;

@Service("subOrderItemService")
public class SuborderItemServiceImpl implements SuborderItemService{
	@Autowired
	private SuborderitemDao suborderitemDao;
	
	@Override
	public List<Suborderitem> selectByModel(Suborderitem query) {
		return suborderitemDao.selectByModel(query);
	}

	@Override
	public void update(Suborderitem soi) {
		suborderitemDao.update(soi);
	}

}
