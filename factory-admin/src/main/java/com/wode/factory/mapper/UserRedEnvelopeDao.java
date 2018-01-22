package com.wode.factory.mapper;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.UserRedEnvelope;

/**
 * Created by zoln on 2015/7/24.
 */
public interface UserRedEnvelopeDao extends  EntityDao<UserRedEnvelope,Long> {
	public List<UserRedEnvelope> selectByModel(UserRedEnvelope query);
}
