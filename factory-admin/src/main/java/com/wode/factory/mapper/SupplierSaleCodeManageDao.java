/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.SupplierSaleCodeManage;

public interface SupplierSaleCodeManageDao extends  EntityDao<SupplierSaleCodeManage,Long>{
	/**
	 * 功能说明：向数据库插入数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
	 */
	public void insert(SupplierSaleCodeManage entity);

}
