package com.wode.factory.service;

import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.UserRedEnvelopeItem;

/**
 *
 */
public interface UserRedEnvelopeItemService extends EntityService<UserRedEnvelopeItem,Long> {
	public List<UserRedEnvelopeItem> selectByModel(UserRedEnvelopeItem query);
}
