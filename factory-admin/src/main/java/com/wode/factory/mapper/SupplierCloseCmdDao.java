/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.SupplierCloseCmd;

public interface SupplierCloseCmdDao extends  EntityDao<SupplierCloseCmd,Long>{
	
	/**
	 * 功能说明：向数据库插入数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
	 */
	public void insert(SupplierCloseCmd entity);
	
	/**
	 * 功能说明：根据条件检索数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param SupplierCloseCmd
	 * @return
	 */
	public List<SupplierCloseCmd> find(SupplierCloseCmd entity);
	
	/**
	 * 通过时间查询数据
	 * @param map
	 * @return
	 */
	public List<SupplierCloseCmd> findByCreateTime(Map<String, Object> map);

	public void deleteBySupplierId(Long supplierId);

}
