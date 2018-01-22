/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.wode.factory.company.query.UserExchangeTicketVo;
import com.wode.factory.company.service.BasePageService;
import com.wode.factory.model.UserExchangeTicket;

public interface UserExchangeTicketService extends BasePageService<UserExchangeTicket>{
	public int updateEnds(UserExchangeTicket entity) throws DataAccessException;

	public List<UserExchangeTicketVo> findListByMap(Map<String, Object> map);

}
