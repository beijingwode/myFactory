package com.wode.factory.service;

import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.CommissionRefundCash;

/**
 *
 */
public interface CommissionRefundCashService extends EntityService<CommissionRefundCash,Long> {

	public List<CommissionRefundCash> selectByModel(CommissionRefundCash query);
}
