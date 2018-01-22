/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.BaseService;
import com.wode.factory.model.SaleBill;
import com.wode.factory.supplier.dao.SaleBillDao;
import com.wode.factory.supplier.query.SaleBillQuery;
import com.wode.factory.supplier.service.SaleBillService;

import cn.org.rapid_framework.page.Page;

@Service("saleBillService")
public class SaleBillServiceImpl extends BaseService<SaleBill,java.lang.Long> implements  SaleBillService{
	@Autowired
	@Qualifier("saleBillDao")
	private SaleBillDao saleBillDao;
	
	public EntityDao getEntityDao() {
		return this.saleBillDao;
	}
	
	public Page findPage(SaleBillQuery query) {
		return saleBillDao.findPage(query);
	}
	
	/**
	 * 根据查询条件查询对账单
	 * @param map
	 * @return
	 */
	public  List<SaleBill> findlistPage(Map map){
		return saleBillDao.findlistPage(map);
	}
	public  Integer findlistPageCount(Map map){
		return saleBillDao.findlistPageCount(map);
	}
	
}
