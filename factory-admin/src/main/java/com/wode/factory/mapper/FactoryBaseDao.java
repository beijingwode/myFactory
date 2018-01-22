package com.wode.factory.mapper;

import java.util.List;

import com.wode.common.frame.base.EntityDao;

/**
 * Created by zoln on 2015/7/24.
 */
public interface FactoryBaseDao<E> extends  EntityDao<E,Long> {
	public List<E> selectByModel(E query);
	void insert(E entity);
	void delete(Long id);	
	
}
