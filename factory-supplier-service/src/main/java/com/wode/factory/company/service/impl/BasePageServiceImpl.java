package com.wode.factory.company.service.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseQuery;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.service.BasePageService;

abstract public class BasePageServiceImpl<T> extends BaseService<T,Long> implements BasePageService<T>{

	protected abstract BasePageDao<T> getBaseDao();

	protected EntityDao<T,Long> getEntityDao() {
		return getBaseDao();
	}
	
	@Override
	public List<T> selectByModel(T record) {
		return getBaseDao().selectByModel(record);
	}

	@Override
	public PageInfo<T> findPage(BaseQuery query) {
		return getBaseDao().findPage(query);
	}
}
