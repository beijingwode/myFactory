/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.ManagerOrderRecord;
import com.wode.factory.model.ProductTrialLimitItem;
import com.wode.factory.user.dao.ManagerOrderRecordDao;

@Repository("managerOrderRecordDao")
public class ManagerOrderRecordDaoImpl extends FactoryBaseDaoImpl<ManagerOrderRecord> implements ManagerOrderRecordDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "ManagerOrderRecordMapper";
	}

	@Override
	public List<ManagerOrderRecord> getManagerOrderRecordList(Map<String, Object> query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce() + ".getManagerOrderRecordList", query,new RowBounds((Integer)query.get("page"), (Integer)query.get("pageSize")));
	}
	
}
