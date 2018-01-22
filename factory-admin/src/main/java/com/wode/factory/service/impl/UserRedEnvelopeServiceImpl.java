package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.mapper.UserRedEnvelopeDao;
import com.wode.factory.model.UserRedEnvelope;
import com.wode.factory.service.UserRedEnvelopeService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("userRedEnvelopeServiceImpl")
public class UserRedEnvelopeServiceImpl extends EntityServiceImpl<UserRedEnvelope,Long> implements UserRedEnvelopeService {

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	UserRedEnvelopeDao userRedEnvelopeMapper;

	@Override
	public List<UserRedEnvelope> selectByModel(UserRedEnvelope query) {
		return getDao().selectByModel(query);
	}

	@Override
	public UserRedEnvelopeDao getDao() {
		return userRedEnvelopeMapper;
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
