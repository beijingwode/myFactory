package com.wode.factory.service.impl;

import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Account;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;
import com.wode.factory.service.AccountService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service("accountServiceFactory")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private Dao dao;
	
	@Transactional
	@Override
	public ActResult<String> refundToUser(Long refundOrderId, Long userId,int status) {
		ActResult<String> ar = new ActResult<String>();
		Refundorder refundorder = dao.fetch(Refundorder.class, refundOrderId);
		if(!StringUtils.isNullOrEmpty(refundorder)&&refundorder.getStatus()==status){
			Returnorder returnorder = dao.fetch(Returnorder.class, refundorder.getReturnOrderId());
			if(!StringUtils.isNullOrEmpty(returnorder) && returnorder.getStatus()==1){
				if(!StringUtils.isNullOrEmpty(userId)&&StringUtils.isEquals(returnorder.getUserId(), userId)){
					Account account = dao.fetch(Account.class, Cnd.where("user_id", "=", returnorder.getUserId()));
					if(!StringUtils.isNullOrEmpty(account)){
						account.setBalance(refundorder.getRefundPrice().add(BigDecimal.valueOf(account.getBalance())).doubleValue());
						account.setUpdateTime(new Date());
						dao.update(account);
					}else{
						account = new Account();
						//account.setId(new DBUtils().CreateID());
						account.setBalance(refundorder.getRefundPrice().doubleValue());
						account.setUserId(returnorder.getUserId());
						account.setUpdateTime(new Date());
						dao.insert(account);
					}	
					if(!StringUtils.isNullOrEmpty(refundorder)&&refundorder.getStatus()==1){
						refundorder.setReason("因您超时未处理，交易支持退款");
					}
					refundorder.setStatus(10);
					dao.update(refundorder);
					ar.setMsg("用户："+userId+" 退货退款成功，退款："+refundorder.getRefundPrice()+" 元，退款单ID："+refundOrderId+" ，退货单ID："+returnorder.getReturnOrderId());
				}else{
					ar.setSuccess(false);
					ar.setMsg("退货单中UserId与传入参数userId不匹配");
				}
			}else{
				ar.setSuccess(false);
				ar.setMsg("returnOrderId："+refundorder.getReturnOrderId()+" 在退款表中不存在匹配数据");
			}
		}else{
			ar.setSuccess(false);
			ar.setMsg("refundOrderId："+refundorder.getReturnOrderId()+" 在退款表中不存在匹配数据");
		}
		return ar;
	}
}
