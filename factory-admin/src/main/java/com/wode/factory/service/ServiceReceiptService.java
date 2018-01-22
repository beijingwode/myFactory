package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.ServiceReceipt;

/**
 *
 */
public interface ServiceReceiptService extends EntityService<ServiceReceipt,Long> {
	/**
	 * 功能说明：向数据库插入数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
	 */
	public void insert(ServiceReceipt entity);
	

	/**
	 * @param 		
	 * @param page		页数
	 * @param pageSize  每页显示条数
	 * @return
	 */
	PageInfo<ServiceReceipt> getPage(Map<String, Object> params);
}
