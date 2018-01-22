/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CommissionRefund;
import com.wode.factory.supplier.dao.CommissionRefundDao;
import com.wode.factory.supplier.query.CommissionRefundQuery;
import com.wode.factory.supplier.service.CommissionRefundService;

import cn.org.rapid_framework.page.Page;

@Service("commissionRefundService")
public class CommissionRefundServiceImpl extends BaseService<CommissionRefund,java.lang.Long> implements  CommissionRefundService{
	@Autowired
	@Qualifier("commissionRefundDao")
	private CommissionRefundDao commissionRefundDao;
	
	public EntityDao getEntityDao() {
		return this.commissionRefundDao;
	}
	
	public Page findPage(CommissionRefundQuery query) {
		return commissionRefundDao.findPage(query);
	}
	
	/**
	 * 根据查询条件查询对账单详情
	 * @param map
	 * @return
	 */
	public  List<CommissionRefund> findlistPage(Map map){
		return commissionRefundDao.findlistPage(map);
	}
	public  Integer findlistPageCount(Map map){
		return commissionRefundDao.findlistPageCount(map);
	}

	@Override
	public BigDecimal getRecentSum(Long supplierId, Date createTimeBegin, Date createTimeEnd) {
		Map map = new HashMap();
		map.put("supplierId", supplierId);
		map.put("createTimeBegin", createTimeBegin);
		map.put("createTimeEnd", createTimeEnd);
		return commissionRefundDao.getRecentSum(map);
	}
}
