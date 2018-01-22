package com.wode.factory.service.impl;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.UserBalanceMapper;
import com.wode.factory.model.UserBalance;
import com.wode.factory.service.UserBalanceService;

@Service("userBalanceService")
public class UserBalanceServiceImpl implements  UserBalanceService{

	@Autowired
	UserBalanceMapper userBalanceMapper;
//	
//	@Override
//	public ActResult<String> refundToUser(Long refundOrderId, Long userId,int status,String currencyName) {
//		ActResult<String> ar = new ActResult<String>();
//		Refundorder refundorder = refundOrderDao.getById(refundOrderId);
//		if(!StringUtils.isNullOrEmpty(refundorder)&&refundorder.getStatus()==status){
//			Returnorder returnorder = returnOrderDao.getById(refundorder.getReturnOrderId());
//			if(!StringUtils.isNullOrEmpty(returnorder) && returnorder.getStatus()==1){
//				if(!StringUtils.isNullOrEmpty(userId)&&StringUtils.isEquals(returnorder.getUserId(), userId)){
//					Currency currency = currencyDao.getByName(currencyName);
//					UserBalance ub = userBalanceMapper.findByUserAndCurrencyId(userId,currency.getId());
//					if(!StringUtils.isNullOrEmpty(ub)){
//						ub.setBalance(refundorder.getRefundPrice().add(ub.getBalance()));
//					}else{
//						ub.setBalance(refundorder.getRefundPrice());
//						ub.setUserId(returnorder.getUserId());
//					}
//					userBalanceMapper.saveOrUpdate(ub);
//					if(!StringUtils.isNullOrEmpty(refundorder)&&refundorder.getStatus()==1){
//						refundorder.setReason("因您超时未处理，交易支持退款");
//					}
//					refundorder.setStatus(10);
//					refundOrderDao.saveOrUpdate(refundorder);
//					ar.setMsg("用户："+userId+" 退货退款成功，退款："+refundorder.getRefundPrice()+" 元，退款单ID："+refundOrderId+" ，退货单ID："+returnorder.getReturnOrderId());
//				}else{
//					ar.setSuccess(false);
//					ar.setMsg("退货单中UserId与传入参数userId不匹配");
//				}
//			}else{
//				ar.setSuccess(false);
//				ar.setMsg("returnOrderId："+refundorder.getReturnOrderId()+" 在退款表中不存在匹配数据");
//			}
//		}else{
//			ar.setSuccess(false);
//			ar.setMsg("refundOrderId："+refundorder.getReturnOrderId()+" 在退款表中不存在匹配数据");
//		}
//		return ar;
//	}

	@Override
	public UserBalance findByUserAndCurrencyId(Long userId, Long currencyId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("currencyId", currencyId);
		return userBalanceMapper.findByUserAndCurrencyId(map);
	}

	@Override
	public void saveOrUpdate(UserBalance entity) {
		userBalanceMapper.update(entity);
	}

	@Override
	public void deleteByUserId(Long userId) {
		userBalanceMapper.deleteByUserId(userId);
	}
}
