package com.wode.factory.service;

import com.wode.common.util.ActResult;

/**
 * 用户余额共通操作
 * @author 谷子夜
 *
 */
public interface AccountService {

	/**
	 * 用户退货申请成功之后，返款给用户至余额
	 * @param refundOrderId 退款单ID
	 * @param userId  用户ID
	 * @param status  状态值，用于区分调度任务跟正常流程
	 * @return
	 */
	ActResult<String> refundToUser(Long refundOrderId, Long userId, int status);
}
