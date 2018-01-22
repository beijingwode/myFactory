/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.common.util.ActResult;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserShareTicket;
import com.wode.factory.user.dao.UserShareTicketDao;
import com.wode.factory.user.service.SupplierExchangeProductService;
import com.wode.factory.user.service.UserExchangeTicketService;
import com.wode.factory.user.service.UserShareTicketService;

@Service("userShareTicketService")
public class UserShareTicketServiceImpl extends FactoryEntityServiceImpl<UserShareTicket> implements  UserShareTicketService{
	@Autowired
	private UserShareTicketDao dao;
	@Autowired
	private UserExchangeTicketService uets;
	@Autowired
	private SupplierExchangeProductService seps;
	@Override
	public UserShareTicketDao getDao() {
		return dao;
	}

	@Override
	public Long getId(UserShareTicket entity) {
		return entity.getId();
	}

	@Override
	public void setId(UserShareTicket entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}

	@Override
	@Transactional
	public ActResult<Map<String,Object>> autoTicket(Long shareId,EnterpriseUser emp) {
		BigDecimal rtnCnt=BigDecimal.ZERO;
		boolean rtnBool=false;
		ActResult<Map<String,Object>> rtn = new ActResult<Map<String,Object>>();
		
		Date now = new Date();
		Date limitStart=now;
		Date limitEnd=now;
		
		UserShareTicket q=new UserShareTicket();
		q.setShareId(shareId);
		List<UserShareTicket> ls = this.selectByModel(q);
		for (UserShareTicket userShareTicket : ls) {
			UserExchangeTicket uq = new UserExchangeTicket();
			uq.setUserId(emp.getId());
			uq.setExchangeProductId(userShareTicket.getTicketId());
			List<UserExchangeTicket> le= uets.selectByModel(uq);
			SupplierExchangeProduct sep = seps.getById(userShareTicket.getTicketId());
			if(le.isEmpty()) {
				if(sep != null && sep.getLimitEnd().compareTo(now)>0) {
					UserExchangeTicket userTicket = new UserExchangeTicket();
					userTicket.setExchangeProductId(sep.getId());
					userTicket.setUserId(emp.getId());
					userTicket.setNickname(emp.getName());
					userTicket.setDuty(emp.getDuty());
					userTicket.setEmpAvgCnt(sep.getEmpAvgCnt());
					userTicket.setEmpAvgAmount(sep.getEmpAvgAmount());
					userTicket.setLimitStart(sep.getLimitStart());
					userTicket.setStatus(0);
					userTicket.setCreateDate(now);
					userTicket.setLimitEnd(sep.getLimitEnd());
					userTicket.setActiveAmount(BigDecimal.ZERO);
					userTicket.setUsedAmount(BigDecimal.ZERO);
					userTicket.setUsedActive(BigDecimal.ZERO);
					userTicket.setLeftCnt(BigDecimal.ONE);
					userTicket.setPrepayAmount(BigDecimal.ZERO);
					userTicket.setTicketNote("限购买换领商品");
					userTicket.setProductId(sep.getProductId());
					userTicket.setProductName(sep.getProductName());
					
					uets.save(userTicket);
					rtnBool=true;
					rtnCnt=rtnCnt.add(sep.getEmpAvgAmount());
					if(limitStart.after(sep.getLimitStart())) {
						limitStart=sep.getLimitStart();
					}
					if(limitEnd.before(sep.getLimitEnd())) {
						limitEnd=sep.getLimitEnd();
					}
					//处理更新员工数量
					seps.updateEmpCnt(sep.getId());
					
				}
			} else {
				rtnCnt=rtnCnt.add(sep.getEmpAvgAmount());
				if(limitStart.after(sep.getLimitStart())) {
					limitStart=sep.getLimitStart();
				}
				if(limitEnd.before(sep.getLimitEnd())) {
					limitEnd=sep.getLimitEnd();
				}
			}
		}
		rtn.setSuccess(rtnBool);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("amount", rtnCnt);
		data.put("limitStart", limitStart);
		data.put("limitEnd", limitEnd);		
		rtn.setData(data);
		return rtn;
	}
}