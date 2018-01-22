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

import com.wode.factory.model.CommissionRefund;
import com.wode.factory.model.CommissionRefundDetail;
import com.wode.factory.supplier.dao.CommissionRefundDetailDao;
import com.wode.factory.supplier.query.CommissionRefundDetailQuery;
import com.wode.factory.supplier.service.CommissionRefundDetailService;

@Service("commissionRefundDetailService")
public class CommissionRefundDetailServiceImpl extends BaseService<CommissionRefundDetail,java.lang.Long> implements  CommissionRefundDetailService{
	@Autowired
	@Qualifier("commissionRefundDetailDao")
	private CommissionRefundDetailDao commissionRefundDetailDao;
	
	public EntityDao getEntityDao() {
		return this.commissionRefundDetailDao;
	}
	
	public Page findPage(CommissionRefundDetailQuery query) {
		return commissionRefundDetailDao.findPage(query);
	}
	/**
	 * 根据查询条件查询对账单详情
	 * @param map
	 * @return
	 */
	public  List<CommissionRefundDetail> findlistPage(Map map){
		return commissionRefundDetailDao.findlistPage(map);
	}
	public  Integer findlistPageCount(Map map){
		return commissionRefundDetailDao.findlistPageCount(map);
	}
}
