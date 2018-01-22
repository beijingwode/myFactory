/**
 * 
 */
package com.wode.factory.facade;

import com.wode.factory.model.ApprShop;
import com.wode.factory.model.ApprSupplier;


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
public interface ApprCheckFacade{

	/**
	 * 功能说明：自动结算
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param supplierId 供应商Id
	 * @param execDate 首次结算日
	 */
	public void apprToSupplier(ApprSupplier appr);

	/**
	 * 功能说明：自动结算
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param supplierId 供应商Id
	 * @param execDate 首次结算日
	 */
	public void apprToShop(ApprShop appr);
}
