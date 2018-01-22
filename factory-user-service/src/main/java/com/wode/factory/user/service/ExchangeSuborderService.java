/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.math.BigDecimal;

import org.springframework.dao.DataAccessException;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.FactoryEntityService;
import com.wode.common.util.ActResult;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.query.ExchangeSuborderQuery;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.vo.ExchangeSubOrderVo;

public interface ExchangeSuborderService extends FactoryEntityService<ExchangeSuborder>{

	public ExchangeSuborder getById(String id) throws DataAccessException;
	public void deleteById(String id) throws DataAccessException;
	public void selectItems4Set(ExchangeSuborder entity);
	
	BigDecimal getOrderAmount(Long userId);
	
	PageInfo<ExchangeSuborder> findPage(ExchangeSuborderQuery query);
	
	public ActResult<Object> delete(UserFactory user, String subOrderId);
	public ExchangeSubOrderVo findExchangeOrderDetailById(SuborderQuery query);
}
