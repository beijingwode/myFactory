/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.SupplierLimitTicket;
import com.wode.factory.user.dao.SupplierLimitTicketDao;

@Repository("supplierLimitTicketDao")
public class SupplierLimitTicketDaoImpl extends FactoryBaseDaoImpl<SupplierLimitTicket> implements SupplierLimitTicketDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierLimitTicketMapper";
	}

	@Override
	public SupplierLimitTicket getByIdAndDateStatus(Long limitTicketId) {
		Map<String,Object> map = new HashMap<String,Object>();
		Date date = new Date();
		map.put("limitTicketId", limitTicketId);
		map.put("date", date);
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".getByIdAndDateStatus",map);
	}


	@Override
	public List<SupplierLimitTicket> selectLimit4BySupId(Long supplierId) {
		Map<String,Object> map = new HashMap<String,Object>();
		Date date = new Date();
		map.put("supplierId", supplierId);
		map.put("date", date);
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectLimit4BySupId",map);
	}

	@Override
	public List<SupplierLimitTicket> getWithOutTicketMap(Long supplierId, Long userId, Integer limitType,
			String skuIds) {
		Map<String,Object> map = new HashMap<String,Object>();
		Date date = new Date();
		map.put("supplierId", supplierId);
		map.put("userId", userId);
		map.put("limitType", limitType);
		map.put("skuIds", skuIds);
		map.put("date", date);
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getWithOutTicketMap",map);
	}
}
