/**
 * 
 */
package com.wode.factory.service;

import java.util.Date;
import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.SaleDetail;
import com.wode.factory.model.SupplierCloseCmd;


/**
 * 
 * <pre>
 * 功能说明: 账期自动执行
 * 日期:	2015年11月09日
 * 开发者:高永久
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 * </pre>
 */
public interface SaleDetailService extends EntityService<SaleDetail,Long>{
	
	/**
	 * 功能说明：向数据库插入数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
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
	 * 功能说明：通过账期计算账期内订单，并形成账单明细(先款)
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
	 */
	public List<SaleDetail> countByOrderEx(SupplierCloseCmd entity);

	/**
	 * 功能说明：通过换领商品确认收货后可先激活换领币
	 * @param param
	 * @return
	 */
	public List<SaleDetail> countExchangeOrder(Date closeStart,Date closeEnd);
}
