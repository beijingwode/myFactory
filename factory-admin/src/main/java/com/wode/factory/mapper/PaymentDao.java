package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Payment;
import com.wode.factory.vo.PaymentVo;

/**
 * Created by zoln on 2015/7/24.
 */
public interface PaymentDao extends  EntityDao<Payment,String> {
	public List<Payment> findList(Map<String, Object> params);

	public List<PaymentVo> findPaymentList(Map<String, Object> params);

	public Payment findPaymentByOrderId(Long orderId);

	public Payment findPaymentBySuborderId(String subOrderId);

	public List<Payment> findHistoryData(Map<String, Object> map);

	public Payment getByTradeNo(@Param("thirdType") String thirdType, @Param("thirdNo") String thirdNo);

	public Payment findPayByGroupOrderId(Long orderId);

	public Payment findPayByGroupSuborderId(String subOrderId);

	public Payment findPayLikeGroupSuborderId(String subOrderId);

	public List<Payment> findBySubOrderId(String subOrderId);

	public List<Payment> findByOrderId(Long orderId);

}
