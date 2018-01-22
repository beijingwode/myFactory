package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.wode.common.db.DBUtils;
import com.wode.factory.mapper.FactoryBaseDao;
import com.wode.factory.service.FactoryEntityService;

public abstract class FactoryEntityServiceImpl<E> extends EntityServiceImpl<E,Long> implements FactoryEntityService<E>  {

	@Autowired
	DBUtils dbUtils;
	abstract public FactoryBaseDao<E> getDao();
	
	public abstract Long getId(E entity);
	public abstract void setId(E entity,Long id);

	@Override
	public List<E> selectByModel(E query) {
		return getDao().selectByModel(query);
	}

	@Override
	public void saveOrUpdate(E entity) {
		if(getId(entity) == null) {
			setId(entity, dbUtils.CreateID());
			getDao().insert(entity);
		} else {
			getDao().update(entity);
		}
	}

	@Override
	public E save(E entity)
			throws DataAccessException {

		if(getId(entity) == null) {
			setId(entity, dbUtils.CreateID());
		}
		getDao().insert(entity);
		return entity;
	}

	@Override
	public void removeById(Long id) throws DataAccessException {
		getDao().delete(id);
	}
}
