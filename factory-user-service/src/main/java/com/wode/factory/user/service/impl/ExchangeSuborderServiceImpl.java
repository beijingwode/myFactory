/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.ExchangeSuborderitem;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.dao.ExchangeSuborderDao;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.query.ExchangeSuborderQuery;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.service.ExchangeSuborderService;
import com.wode.factory.user.service.ExchangeSuborderitemService;
import com.wode.factory.user.service.ExpressComService;
import com.wode.factory.user.vo.ExchangeSubOrderVo;

@Service("exchangeSuborderService")
public class ExchangeSuborderServiceImpl extends FactoryEntityServiceImpl<ExchangeSuborder> implements  ExchangeSuborderService{
	@Autowired
	private ExchangeSuborderDao dao;
	@Autowired
	private SupplierDao supplierDao;
	
	@Autowired
	private ExchangeSuborderitemService exchangeSuborderitemService;
	
	@Override
	public ExchangeSuborderDao getDao() {
		return dao;
	}


	@Override
	public Long getId(ExchangeSuborder entity) {
		return -1L;
	}

	@Override
	public void setId(ExchangeSuborder entity, Long id) {
		
	}

	@Override
	public ExchangeSuborder getById(String id) throws DataAccessException {
		return dao.getById(id);
	}

	@Override
	public void deleteById(String id) throws DataAccessException {
		dao.deleteById(id);
	}

	@Override
	public void selectItems4Set(ExchangeSuborder entity) {
		ExchangeSuborderitem que2 = new ExchangeSuborderitem();
		que2.setOrderId(entity.getOrderId());
		que2.setSubOrderId(entity.getSubOrderId());
		entity.setSubOrderItems(exchangeSuborderitemService.selectByModel(que2));
	}


	@Override
	public BigDecimal getOrderAmount(Long userId) {
		BigDecimal amount = dao.getOrderAmount(userId);
		return amount==null?BigDecimal.ZERO:amount;
	}

	@Override
	public PageInfo<ExchangeSuborder> findPage(ExchangeSuborderQuery query) {
		PageInfo<ExchangeSuborder> rtn = dao.findPage(query);
		for (ExchangeSuborder sub : rtn.getList()) {
			Supplier s= supplierDao.getById(sub.getSupplierId());
			if(s!=null) {
				sub.setSupplierName(s.getComName());
			}
			this.selectItems4Set(sub);
		}
		return rtn;
	}


	@Override
	public ActResult<Object> delete(UserFactory user, String subOrderId) {
		ExchangeSuborder query = new ExchangeSuborder();
		query.setUserId(user.getId());
		query.setSubOrderId(subOrderId);
		List<ExchangeSuborder> sublist = dao.selectByModel(query);
		if(sublist==null ||sublist.isEmpty()) {
			return ActResult.fail("无权操作");
		}else {
			ExchangeSuborder exchangeSuborder = sublist.get(0);
			if(exchangeSuborder!=null) {
				if (exchangeSuborder.getExchangeStatus() != -1 && exchangeSuborder.getExchangeStatus() != 3) {
					return ActResult.fail("无权操作");
				}else {
					exchangeSuborder.setDeleteFlag(true);
					this.update(exchangeSuborder);//修改匹配订单
				}
			}
			return ActResult.success(exchangeSuborder);
		}
	}


	@Override
	public ExchangeSubOrderVo findExchangeOrderDetailById(SuborderQuery query) {
		ExchangeSubOrderVo sov = dao.findExchangeOrderDetailById(query);
		List<ExchangeSuborderitem> list = exchangeSuborderitemService.getItemsListBySubOrderId(query.getSubOrderId());
		sov.setSubOrderItems(list);
		return sov;
	}
}