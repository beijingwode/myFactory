/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.SupplierDuration;

public interface SupplierDurationVoDao extends  EntityDao<SupplierDuration,Long>{
	public void saveDurationVo(SupplierDuration sd);
	
	public String selectMaxFinanceCode();

	public void deleteBySupplierId(Long supplierId);
}
