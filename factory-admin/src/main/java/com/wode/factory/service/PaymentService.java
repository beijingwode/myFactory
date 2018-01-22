package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.Payment;
import com.wode.factory.model.Suborder;
import com.wode.factory.vo.PaymentVo;

/**
 *
 */
public interface PaymentService  {

	PageInfo<Payment> findList(Map<String, Object> params);
	public void update(Payment entity);
	public Payment getById(String outTradeNo);
	PageInfo<PaymentVo> findPaymentList(Map<String, Object> params);
	Payment findPaymentByOrderOrSuborderId(Long orderId, String subOrderId);
	Payment getByTradeNo(String thirdType, String thirdNo);
	Payment findPayByGroupOrderIdOrGroupSubOrderId(String subOrderId);
	List<Payment> findBySubOrderId(String subOrderId);
	List<Payment> findByOrderId(Long orderId);
	void delete(String outTradeNo);
	
}
