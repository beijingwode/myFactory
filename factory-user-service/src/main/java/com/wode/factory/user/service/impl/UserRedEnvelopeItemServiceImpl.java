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

import com.wode.common.frame.base.BaseService;
import com.wode.factory.model.UserRedEnvelopeItem;
import com.wode.factory.user.dao.UserRedEnvelopeItemDao;
import com.wode.factory.user.service.UserRedEnvelopeItemService;

@Service("userRedEnvelopeItemService")
public class UserRedEnvelopeItemServiceImpl extends BaseService<UserRedEnvelopeItem,java.lang.Long> implements  UserRedEnvelopeItemService{
	@Autowired
	private UserRedEnvelopeItemDao userRedEnvelopeItemDao;
	
	public UserRedEnvelopeItemDao getEntityDao() {
		return this.userRedEnvelopeItemDao;
	}

	@Override
	public List<UserRedEnvelopeItem> selectByModel(UserRedEnvelopeItem query) {
		return userRedEnvelopeItemDao.selectByModel(query);
	}
}