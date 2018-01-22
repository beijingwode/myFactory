package com.wode.factory.company.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.dao.EntSeasonActDao;
import com.wode.factory.company.service.EntSeasonActService;
import com.wode.factory.model.EntSeasonAct;
@Service("entSeasonActService")
public class EntSeasonActServiceImpl extends BasePageServiceImpl<EntSeasonAct> implements EntSeasonActService{

	@Autowired
	EntSeasonActDao entSeasonActDao;

	@Override
	protected BasePageDao<EntSeasonAct> getBaseDao() {
		return entSeasonActDao;
	}

	 
}
