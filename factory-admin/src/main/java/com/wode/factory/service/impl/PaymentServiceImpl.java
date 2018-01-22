package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.PaymentDao;
import com.wode.factory.model.Payment;
import com.wode.factory.model.Suborder;
import com.wode.factory.service.PaymentService;
import com.wode.factory.vo.PaymentVo;
import com.wode.factory.vo.SuborderOrderVo;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentDao dao;
	

	@Override
	public PageInfo<Payment> findList(Map<String, Object> params) {

		PageHelper.startPage(params);
		List<Payment> list = dao.findList(params);
		return new PageInfo<Payment>(list);
	}
	@Override
	public PageInfo<PaymentVo> findPaymentList(Map<String, Object> params) {
		PageHelper.startPage(params);
		
		List<PaymentVo> list = dao.findPaymentList(params);
		return new PageInfo(list);
		
//		return dao.findPaymentList(params);
	}
	@Override
	public void update(Payment entity) {
		dao.update(entity);
	}

	@Override
	public Payment getById(String outTradeNo) {
		return dao.getById(outTradeNo);
	}
	@Override
	public Payment findPaymentByOrderOrSuborderId(Long orderId, String subOrderId) {
		Payment payment=null;
		 payment = dao.findPaymentByOrderId(orderId);
		if(payment==null){
			  payment = dao.findPaymentBySuborderId(subOrderId);
		}
		return payment;
	}
	@Override
	public Payment getByTradeNo(String thirdType, String thirdNo) {
		return dao.getByTradeNo(thirdType,thirdNo);
	}
	@Override
	public Payment findPayByGroupOrderIdOrGroupSubOrderId(String subOrderId) {
		Payment payment=null;
		String orderId = subOrderId.substring(0,subOrderId.indexOf("-"));
		payment = dao.findPayByGroupOrderId(Long.valueOf(orderId));
		if(payment==null){
			payment = dao.findPayByGroupSuborderId(subOrderId);
		}
		return payment;
	}
	@Override
	public List<Payment> findBySubOrderId(String subOrderId) {
		
		return dao.findBySubOrderId(subOrderId);
	}
	@Override
	public List<Payment> findByOrderId(Long orderId) {
		return dao.findByOrderId(orderId);
	}
	@Override
	public void delete(String outTradeNo) {
		dao.deleteById(outTradeNo);
		
	}

	

	
		
}
