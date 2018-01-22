/**
 * 
 */
package com.wode.factory.facade;

import java.util.Date;
import java.util.List;

import com.wode.common.util.ActResult;
import com.wode.factory.model.SaleBill;
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
public interface SupplierCloseFacade{
	
	/**
	 * 功能说明：自动结算
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
	 */
	public ActResult<Long> execClose(SupplierCloseCmd entity,String supplierName,String billId);

	/**
	 * 
	 * 功能说明：生成管理对账单
	 * 日期:	2016年12月02日
	 * 开发者:gaoyj
	 * 版本号:1.0
	 * 
	 * @param relationType		关联内容0:未关联 7:货款+运费+佣金 6:货款+运费 5:货款+佣金 4:货款 3:运费+佣金 2:运费 1:佣金
	 * @param note				备注、结算内容
	 * @param supplierId		商家ID
	 * @param supplierName		商家名称
	 * @param billId			对账单ID
	 * @param list				关联对象列表
	 * @return
	 */
	public ActResult<SaleBill> execRelation(Integer relationType,String title,String note, Long supplierId,String supplierName,
			String billId,List<SaleBill> list,Long createUserid,String updUser,Date startTime,Date endTime,Integer payType,String payNote);
	
	/**
	 * 
	 * 功能说明：自动结算
	 * 日期:	2015年11月09日
	 * 开发者:高
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd  
	 * @param result 处理结果 ture：成功/false：失败
	 * @param saleBillId 成功时的对账单id
	 * @param errMsg 失败时的错误信息
	 */
	public void dealCloseCmd(SupplierCloseCmd entity,boolean result, Long saleBillId,String errMsg);

	/**
	 * 功能说明：自动结算
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param supplierId 供应商Id
	 * @param execDate 首次结算日
	 */
	public void makeFistDuration(Long supplierId, Date execDate);
	
}
