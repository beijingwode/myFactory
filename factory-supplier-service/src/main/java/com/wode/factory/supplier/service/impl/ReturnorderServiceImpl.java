/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Returnorder;
import com.wode.factory.supplier.dao.ReturnorderDao;
import com.wode.factory.supplier.query.ReturnorderQuery;
import com.wode.factory.supplier.service.ReturnorderService;

@Service("returnorderService")
public class ReturnorderServiceImpl extends BaseService<Returnorder,java.lang.Long> implements  ReturnorderService{
	@Autowired
	@Qualifier("returnorderDao")
	private ReturnorderDao returnorderDao;
	
	public EntityDao getEntityDao() {
		return this.returnorderDao;
	}
	
	public Page findPage(ReturnorderQuery query) {
		return returnorderDao.findPage(query);
	}
//
//	@Override
//	public Returnorder returnOrderByMap(Map<String, Object> map) {
//		return returnorderDao.returnOrderByMap(map);
//	}
	@Override
	public Returnorder returnOrderById(Long id) {
		return returnorderDao.returnOrderById(id);
	}

	@Override
	public void updatebymap(Map<String, Object> map) {
		returnorderDao.updatebymap(map);
	}
	
}
