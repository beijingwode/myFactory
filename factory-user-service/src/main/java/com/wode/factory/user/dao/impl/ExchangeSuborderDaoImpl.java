/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.user.dao.ExchangeSuborderDao;
import com.wode.factory.user.query.ExchangeSuborderQuery;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.vo.ExchangeSubOrderVo;

@Repository("exchangeSuborderDao")
public class ExchangeSuborderDaoImpl extends FactoryBaseDaoImpl<ExchangeSuborder> implements ExchangeSuborderDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "ExchangeSuborderMapper";
	}

	@Override
	public ExchangeSuborder getById(String id) throws DataAccessException {

		ExchangeSuborder object = getSqlSession().selectOne(getFindByPrimaryKeyStatement(), id);
        return object;
	}

	@Override
	public void deleteById(String id) throws DataAccessException {
		getSqlSession().delete(getDeleteStatement(), id);
	}

	@Override
	public BigDecimal getOrderAmount(Long userId) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".getOrderAmount", userId);
	}

	@Override
	public PageInfo<ExchangeSuborder> findPage(ExchangeSuborderQuery query) {
        List<ExchangeSuborder> list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findPage", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
        return new PageInfo<ExchangeSuborder>(list);
	}

	@Override
	public ExchangeSubOrderVo findExchangeOrderDetailById(SuborderQuery query) {
		 return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".findExchangeOrderDetailById", query);
	}
}
