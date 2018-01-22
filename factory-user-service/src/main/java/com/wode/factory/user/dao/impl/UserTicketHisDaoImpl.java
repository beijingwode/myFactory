/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseDao;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserTicketHis;
import com.wode.factory.user.dao.UserTicketHisDao;
import com.wode.factory.user.query.UserTicketHisQuery;

@Repository("userTicketHisDao")
public class UserTicketHisDaoImpl extends BaseDao<UserTicketHis,java.lang.Long> implements UserTicketHisDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserTicketHisMapper";
	}
	
	public void saveOrUpdate(UserTicketHis entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public List<UserTicketHis> selectByModel(UserTicketHis query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", query);
	}
	
	@Override
	public PageInfo<UserTicketHis> findPageListByUserid(UserTicketHisQuery query) {
		List<UserTicketHis> list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findPageListByUserid", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
		return new PageInfo<UserTicketHis>(list);
	}
}
