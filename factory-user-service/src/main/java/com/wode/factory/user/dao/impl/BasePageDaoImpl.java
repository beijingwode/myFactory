package com.wode.factory.user.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseDao;
import com.wode.common.frame.base.BaseQuery;
import com.wode.factory.user.dao.BasePageDao;

public abstract class BasePageDaoImpl<T> extends BaseDao<T,Long> implements BasePageDao<T>  {

	@Override
	public PageInfo<T> findPage(BaseQuery query) {

		List<T> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".findPage",query,new RowBounds(query.getPageNumber(), query.getPageSize()));
		return new PageInfo<T>(list);
	}

	@Override
	public List<T> selectByModel(T model){
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", model);
	}

	@Override
	public void saveOrUpdate(T entity) throws DataAccessException {
		if(getId(entity) == null) 
			save(entity);
		else 
			update(entity);
	}
}
