/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.supplier.dao.SuborderitemDao;
import com.wode.factory.supplier.query.SuborderitemQuery;
import com.wode.factory.supplier.service.SuborderitemService;

@Service("suborderitemService")
public class SuborderitemServiceImpl extends BaseService<Suborderitem,java.lang.Long> implements  SuborderitemService{
	@Autowired
	@Qualifier("suborderitemDao")
	private SuborderitemDao suborderitemDao;
	
	public EntityDao getEntityDao() {
		return this.suborderitemDao;
	}
	
	public Page findPage(SuborderitemQuery query) {
		return suborderitemDao.findPage(query);
	}

	@Override
	public List<Suborderitem> selectByModel(Suborderitem record) {
		return suborderitemDao.selectByModel(record);
	}

	@Override
	public List<Suborderitem> selectSuborderItemByrenturnOrderId(Long returnOrderId) {
		return  suborderitemDao.selectSuborderItemByrenturnOrderId(returnOrderId);
	}
	
}
