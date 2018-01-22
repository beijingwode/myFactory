/**
 * 
 */
package com.wode.factory.service;

import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.CommissionRefundDetail;
import com.wode.factory.model.SupplierCloseCmd;
import com.wode.factory.vo.SupplierSaleFuliVo;


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
public interface CommissionRefundDetailService extends EntityService<CommissionRefundDetail,Long>{
	
	/**
	 * 功能说明：向数据库插入数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
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
	public List<SupplierSaleFuliVo> countSupplierDaySaleFuli(String startTime,String endTime);
	/**
	 * 功能说明：通过账期计算账期内订单，并形成账单明细
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
	 */
	public List<CommissionRefundDetail> getByRefundId(Long commissionRefundId);
}
