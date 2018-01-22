package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.UserRedEnvelopeItemDao;
import com.wode.factory.model.UserRedEnvelopeItem;
import com.wode.factory.service.UserRedEnvelopeItemService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("userRedEnvelopeItemServiceImpl")
public class UserRedEnvelopeItemServiceImpl extends EntityServiceImpl<UserRedEnvelopeItem,Long> implements UserRedEnvelopeItemService {

	@Autowired
	UserRedEnvelopeItemDao userRedEnvelopeItemMapper;

	@Override
	public List<UserRedEnvelopeItem> selectByModel(UserRedEnvelopeItem query) {
		return getDao().selectByModel(query);
	}

	@Override
	public UserRedEnvelopeItemDao getDao() {
		return userRedEnvelopeItemMapper;
	}
}
