/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.SupplierLimitTicket;

public interface SupplierLimitTicketService extends FactoryEntityService<SupplierLimitTicket>{

	SupplierLimitTicket getByIdAndDateStatus(Long limitTicketId);

	// 获取商家换领
	List<SupplierLimitTicket> selectLimit4BySupId(Long supplierId);
	/**
	 * 获取未领取的优惠券
	 * @param supplierId
	 * @param userId
	 * @param limitType
	 * @param skuIds
	 * @return
	 */
	List<SupplierLimitTicket> getWithOutTicketMap(Long supplierId,Long userId,Integer limitType,String skuIds);
}
