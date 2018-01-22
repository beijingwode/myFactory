/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CommissionRefund;

public interface CommissionRefundDao extends  EntityDao<CommissionRefund,Long>{
	
	/**
	 * 功能说明：向数据库插入数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SaleDetail
	 */
	public void insert(CommissionRefund entity);
	
	/**
	 * 功能说明：通过明细合计出账单
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param id(明细插入时id)
	 */
	public CommissionRefund sumBydetail(Long id);
}
