package com.wode.factory.service;

import java.util.List;

import com.wode.common.frame.base.EntityService;

/**
 *
 */
public interface FactoryEntityService<T> extends EntityService<T,Long> {

	public List<T> selectByModel(T query);
}
