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
import com.wode.factory.model.SaleDetail;
import com.wode.factory.model.SupplierCloseCmd;

public interface SaleDetailDao extends  EntityDao<SaleDetail,Long>{
	
	/**
	 * 功能说明：向数据库插入数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SaleDetail
	 */
	public void insert(SaleDetail entity);

	/**
	 * 功能说明：通过账期计算账期内订单，并形成账单明细
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
	 */
	public List<SaleDetail> countByOrder(SupplierCloseCmd entity);

	/**
	 * 功能说明：通过账期计算账期内订单，并形成账单明细（已支付订单结算）
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
	 */
	public List<SaleDetail> countByOrderEx(SupplierCloseCmd entity);

	/**
	 * 功能说明：通过账期计算账期内订单，并形成账单明细（已支付订单结算）
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
	 */
	public List<SaleDetail> countExchangeOrder(Map<String,Object> param);
	
	/**
	 * 根据对账单id查询所有订单详情
	 * @param saleBillId
	 * @return
	 */
	public List<SaleDetail> getBySaleBillId(Long saleBillId);
	
}
