/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import cn.org.rapid_framework.page.Page;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CommissionRefund;
import com.wode.factory.model.CommissionRefundDetail;
import com.wode.factory.supplier.query.CommissionRefundDetailQuery;

public interface CommissionRefundDetailDao extends  EntityDao<CommissionRefundDetail,Long>{
	public Page findPage(CommissionRefundDetailQuery query);
	public void saveOrUpdate(CommissionRefundDetail entity);
	/**
	 * 根据查询条件查询对账单详情
	 * @param map
	 * @return
	 */
	public  List<CommissionRefundDetail> findlistPage(Map map);
	public  Integer findlistPageCount(Map map);
}
