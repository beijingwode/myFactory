/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.math.BigDecimal;

import org.springframework.dao.DataAccessException;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.FactoryBaseDao;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.user.query.ExchangeSuborderQuery;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.vo.ExchangeSubOrderVo;

public interface ExchangeSuborderDao extends  FactoryBaseDao<ExchangeSuborder>{

	public ExchangeSuborder getById(String id) throws DataAccessException;
	public void deleteById(String id) throws DataAccessException;
	BigDecimal getOrderAmount(Long userId);
	

	PageInfo<ExchangeSuborder> findPage(ExchangeSuborderQuery query);
	public ExchangeSubOrderVo findExchangeOrderDetailById(SuborderQuery query);
}
