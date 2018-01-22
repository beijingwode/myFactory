package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ServiceReceipt;

/**
 * Created by zoln on 2015/7/24.
 */
public interface ServiceReceiptDao extends  EntityDao<ServiceReceipt,Long> {
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
	 * 分页条件查询对账单信息
	 * @param map
	 * @return
	 */
	public List<ServiceReceipt> findPageList(Map<String, Object> map);
}
