package com.wode.factory.supplier.facade;

import java.math.BigDecimal;

import com.wode.common.util.ActResult;

public interface SuborderFacade {

	/**
	 * 修改订单运费
	 * @param supplierId 当前商户Id
	 * @param suborder 子单Id
	 * @param freight 新运费
	 * @param updUser 更新者
	 * @return
	 */
	ActResult<BigDecimal> changeFreight(Long supplierId,String suborderId, Double freight,String updUser);
	
	
}
