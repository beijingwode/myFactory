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
import com.wode.factory.model.UserRedEnvelope;
import com.wode.factory.user.dao.UserRedEnvelopeDao;
import com.wode.factory.user.service.UserRedEnvelopeService;

@Service("userRedEnvelopeService")
public class UserRedEnvelopeServiceImpl extends BaseService<UserRedEnvelope,java.lang.Long> implements  UserRedEnvelopeService{
	@Autowired
	private UserRedEnvelopeDao userRedEnvelopeDao;

	@Autowired
	private RedisUtil redisUtil;
	
	public UserRedEnvelopeDao getEntityDao() {
		return this.userRedEnvelopeDao;
	}

	@Override
	public List<UserRedEnvelope> selectByModel(UserRedEnvelope query) {
		return userRedEnvelopeDao.selectByModel(query);
	}

	@Override
	public UserRedEnvelope getById(Long id) throws DataAccessException {
		String jsonS=redisUtil.getData("REDIS_ENVELOPE_" + id);
		if(StringUtils.isEmpty(jsonS)) {
			UserRedEnvelope e =super.getById(id);
			
			redisUtil.setData("REDIS_ENVELOPE_" + id, JsonUtil.toJsonString(e), 60*60*6); //6小时缓存
			return e;
		} else {
			return JsonUtil.getObject(jsonS, UserRedEnvelope.class);
		}
	}
}