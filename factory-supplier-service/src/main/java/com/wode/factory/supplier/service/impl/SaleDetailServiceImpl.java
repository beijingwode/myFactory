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
import cn.org.rapid_framework.page.Page;

import com.wode.factory.model.SaleBill;
import com.wode.factory.model.SaleDetail;
import com.wode.factory.supplier.dao.SaleDetailDao;
import com.wode.factory.supplier.query.SaleDetailQuery;
import com.wode.factory.supplier.service.SaleDetailService;

@Service("saleDetailService")
public class SaleDetailServiceImpl extends BaseService<SaleDetail,java.lang.Long> implements  SaleDetailService{
	@Autowired
	@Qualifier("saleDetailDao")
	private SaleDetailDao saleDetailDao;
	
	public EntityDao getEntityDao() {
		return this.saleDetailDao;
	}
	
	public Page findPage(SaleDetailQuery query) {
		return saleDetailDao.findPage(query);
	}
	
	/**
	 * 根据查询条件查询对账单详情
	 * @param map
	 * @return
	 */
	public  List<SaleDetail> findlistPage(Map map){
		return saleDetailDao.findlistPage(map);
	}
	public  Integer findlistPageCount(Map map){
		return saleDetailDao.findlistPageCount(map);
	}
}
