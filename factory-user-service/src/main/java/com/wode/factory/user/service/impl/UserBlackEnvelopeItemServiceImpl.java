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
import com.wode.factory.model.UserBlackEnvelopeItem;
import com.wode.factory.user.dao.UserBlackEnvelopeItemDao;
import com.wode.factory.user.service.UserBlackEnvelopeItemService;

@Service("userBlackEnvelopeItemService")
public class UserBlackEnvelopeItemServiceImpl extends BaseService<UserBlackEnvelopeItem,java.lang.Long> implements  UserBlackEnvelopeItemService{
	@Autowired
	private UserBlackEnvelopeItemDao UserBlackEnvelopeItemDao;
	
	public UserBlackEnvelopeItemDao getEntityDao() {
		return this.UserBlackEnvelopeItemDao;
	}

	@Override
	public List<UserBlackEnvelopeItem> selectByModel(UserBlackEnvelopeItem query) {
		return UserBlackEnvelopeItemDao.selectByModel(query);
	}
}