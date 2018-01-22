package com.wode.factory.mapper;

import java.util.List;

import com.wode.factory.model.CooperationId;
import com.wode.tongji.model.App;

public interface CooperationIdMapper{

	int insertSelective(App app);

	int batchInsert(List<CooperationId> listCoop);

}
