/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseService;
import com.wode.factory.model.UserTicketHis;
import com.wode.factory.user.dao.UserTicketHisDao;
import com.wode.factory.user.query.UserTicketHisQuery;
import com.wode.factory.user.service.UserTicketHisService;

@Service("userTicketHisService")
public class UserTicketHisServiceImpl extends BaseService<UserTicketHis,java.lang.Long> implements  UserTicketHisService{
	@Autowired
	private UserTicketHisDao UserTicketHisDao;
	
	public UserTicketHisDao getEntityDao() {
		return this.UserTicketHisDao;
	}

	@Override
	public List<UserTicketHis> selectByModel(UserTicketHis query) {
		return UserTicketHisDao.selectByModel(query);
	}

	@Override
	public PageInfo<UserTicketHis> findPageListByUserid(Long userId,Long ticketId,Integer page, Integer pageSize) {
		UserTicketHisQuery query = new UserTicketHisQuery();
		query.setUserId(userId);
		query.setTicketId(ticketId);
		query.setPageNumber(page);
		query.setPageSize(pageSize);
		query.setSortColumns("op_date");
		return UserTicketHisDao.findPageListByUserid(query);
	}
}