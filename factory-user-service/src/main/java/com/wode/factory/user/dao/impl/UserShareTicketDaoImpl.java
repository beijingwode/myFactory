/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.UserShareTicket;
import com.wode.factory.user.dao.UserShareTicketDao;

@Repository("userShareTicketDao")
public class UserShareTicketDaoImpl extends FactoryBaseDaoImpl<UserShareTicket> implements UserShareTicketDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "UserShareTicketMapper";
	}
	
}
