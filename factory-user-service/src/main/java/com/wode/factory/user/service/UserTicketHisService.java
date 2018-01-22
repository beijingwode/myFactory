/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.UserTicketHis;

public interface UserTicketHisService extends EntityService<UserTicketHis,Long>{
	public List<UserTicketHis> selectByModel(UserTicketHis query);

	public PageInfo<UserTicketHis> findPageListByUserid(Long userId,Long ticketId,Integer pageSize, Integer pageNum);
}
