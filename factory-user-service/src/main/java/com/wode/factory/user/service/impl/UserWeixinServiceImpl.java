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
import com.wode.factory.model.UserWeixin;
import com.wode.factory.user.dao.UserWeixinDao;
import com.wode.factory.user.service.UserWeixinService;

@Service("userWeixinService")
public class UserWeixinServiceImpl extends BaseService<UserWeixin,java.lang.Long> implements  UserWeixinService{
	@Autowired
	private UserWeixinDao userWeixinDao;
	
	public UserWeixinDao getEntityDao() {
		return this.userWeixinDao;
	}

	@Override
	public List<UserWeixin> selectByModel(UserWeixin query) {
		return userWeixinDao.selectByModel(query);
	}

	@Override
	public UserWeixin getOneModelByOpenId(String openId) {
		UserWeixin q = new UserWeixin();
		q.setOpenId(openId);
		q.setLogout(0);
		List<UserWeixin> ls = this.selectByModel(q);
		if(ls!=null && !ls.isEmpty()) {
			return ls.get(0);
		}
		return null;
	}

	@Override
	public UserWeixin getOneModelByUserId(Long userId) {
		UserWeixin q = new UserWeixin();
		q.setUserId(userId);
		q.setLogout(0);
		
		List<UserWeixin> ls = this.selectByModel(q);
		if(ls!=null && !ls.isEmpty()) {
			return ls.get(0);
		}
		return null;
	}

}