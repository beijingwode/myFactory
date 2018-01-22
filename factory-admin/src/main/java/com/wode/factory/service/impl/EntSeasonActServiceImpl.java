package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.EntSeasonActDao;
import com.wode.factory.mapper.FactoryBaseDao;
import com.wode.factory.model.EntSeasonAct;
import com.wode.factory.service.EntSeasonActService;
@Service("entSeasonActService")
public class EntSeasonActServiceImpl extends FactoryEntityServiceImpl<EntSeasonAct> implements EntSeasonActService{

	@Autowired
	EntSeasonActDao entSeasonActDao;

	@Override
	public FactoryBaseDao<EntSeasonAct> getDao() {
		return entSeasonActDao;
	}

	@Override
	public Long getId(EntSeasonAct entity) {
		return entity.getId();
	}

	@Override
	public void setId(EntSeasonAct entity, Long id) {
		entity.setId(id);
	}

	 
}
