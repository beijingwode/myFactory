/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserBlackEnvelope;
import com.wode.factory.user.dao.UserBlackEnvelopeDao;
import com.wode.factory.user.service.UserBlackEnvelopeService;

@Service("userBlackEnvelopeService")
public class UserBlackEnvelopeServiceImpl extends BaseService<UserBlackEnvelope,java.lang.Long> implements  UserBlackEnvelopeService{
	@Autowired
	private UserBlackEnvelopeDao UserBlackEnvelopeDao;

	@Autowired
	private RedisUtil redisUtil;
	
	public UserBlackEnvelopeDao getEntityDao() {
		return this.UserBlackEnvelopeDao;
	}

	@Override
	public List<UserBlackEnvelope> selectByModel(UserBlackEnvelope query) {
		return UserBlackEnvelopeDao.selectByModel(query);
	}
	
	@Override
	public UserBlackEnvelope getById(Long id) throws DataAccessException {
		String jsonS=redisUtil.getData("REDIS_ENVELOPE_" + id);
		if(StringUtils.isEmpty(jsonS)) {
			UserBlackEnvelope e =super.getById(id);
			
			redisUtil.setData("REDIS_ENVELOPE_" + id, JsonUtil.toJsonString(e), 60*60*6); //6小时缓存
			return e;
		} else {
			return JsonUtil.getObject(jsonS, UserBlackEnvelope.class);
		}
	}
}