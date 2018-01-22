/**
 * 
 */
package com.wode.factory.service;

import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.vo.SupplierCategoryVo;


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
public interface SupplierCategoryVoService extends EntityService<SupplierCategoryVo,Long>{

	/**
	 * 功能说明：根据条件检索数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param SupplierCloseCmd
	 * @return
	 */
	public List<SupplierCategoryVo> find(SupplierCategoryVo entity);
}
