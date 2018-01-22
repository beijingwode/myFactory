package com.wode.factory.mapper;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.UserRedEnvelopeItem;

/**
 * Created by zoln on 2015/7/24.
 */
public interface UserRedEnvelopeItemDao extends  EntityDao<UserRedEnvelopeItem,Long> {

	public List<UserRedEnvelopeItem> selectByModel(UserRedEnvelopeItem query);
}
