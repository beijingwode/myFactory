/**
 * 
 */
package com.wode.factory.service;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.SupplierDuration;


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
public interface SupplierDurationVoService extends EntityService<SupplierDuration,Long>{
	public void saveSupplierDuration(SupplierDuration sd);

	public String getNewFinanceCode();
	
	public boolean isPayFirst(SupplierDuration sd);
}
