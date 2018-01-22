/**
 * 
 */
package com.wode.factory.service;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.CommissionRefund;


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
public interface CommissionRefundService extends EntityService<CommissionRefund,Long>{
	
	/**
	 * 功能说明：向数据库插入数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
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
