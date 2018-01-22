package com.wode.factory.service;

import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.UserRedEnvelope;

/**
 *
 */
public interface UserRedEnvelopeService extends EntityService<UserRedEnvelope,Long> {

	public List<UserRedEnvelope> selectByModel(UserRedEnvelope query);
}
