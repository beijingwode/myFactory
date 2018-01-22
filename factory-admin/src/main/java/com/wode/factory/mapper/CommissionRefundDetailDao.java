/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CommissionRefundDetail;
import com.wode.factory.model.SupplierCloseCmd;
import com.wode.factory.vo.SupplierSaleFuliVo;

public interface CommissionRefundDetailDao extends  EntityDao<CommissionRefundDetail,Long>{
	
	/**
	 * 功能说明：向数据库插入数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SaleDetail
	 */
	public void insert(CommissionRefundDetail entity);

	/**
	 * 功能说明：通过账期计算账期内订单，并形成账单明细
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
	 */
	public List<CommissionRefundDetail> countByOrder(SupplierCloseCmd entity);
	
	/**
	 * 功能说明：统计一段时间内商家妥投订单中内购券的数量
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<SupplierSaleFuliVo> countSupplierDaySaleFuli(Map<String,String> map);
	
	public List<CommissionRefundDetail> getByRefundId(Long commissionRefundId);
	
}
