package com.wode.factory.supplier.facade;

import com.wode.common.util.ActResult;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;
import com.wode.factory.model.Suborder;

public interface OrderRefundFacade {

	/**
	 * 用户退货申请成功之后，返款给用户至余额
	 * @param refundorder
	 * @param returnorder
	 * @param suborder
	 * @param empId
	 * @param updName
	 * @param newReason
	 * @return
	 * @throws Exception
	 */
	ActResult<String> refundToUser(Refundorder refundorder,
			Returnorder returnorder, Suborder suborder, Long empId,
			String updName,String newReason) throws Exception;
	
	/**
	 * 拒绝用户退款申请
	 * @param refundorder
	 * @param returnorder
	 * @param suborder
	 * @param refuseNote
	 * @param updName
	 * @return
	 */
	ActResult<String> refuseApply(Refundorder refundorder,
			Returnorder returnorder, Suborder suborder,
			String refuseNote,String updName);
	
}
