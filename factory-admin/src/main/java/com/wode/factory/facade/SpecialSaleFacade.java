/**
 * 
 */
package com.wode.factory.facade;

import java.util.List;

import com.wode.common.util.ActResult;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.Product;
import com.wode.sys.model.SysUser;


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
public interface SpecialSaleFacade{
	
	/**
	 * 特价商品审核
	 * @param pro
	 * @param user 
	 * @return
	 */
	public void SpecialSaleCheck(Product pro, SysUser user);
}
