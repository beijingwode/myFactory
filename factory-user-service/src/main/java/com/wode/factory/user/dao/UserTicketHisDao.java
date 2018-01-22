/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.UserTicketHis;
import com.wode.factory.user.query.UserTicketHisQuery;

public interface UserTicketHisDao extends  EntityDao<UserTicketHis,Long>{
	public List<UserTicketHis> selectByModel(UserTicketHis query);

	public PageInfo<UserTicketHis> findPageListByUserid(UserTicketHisQuery query);
}
