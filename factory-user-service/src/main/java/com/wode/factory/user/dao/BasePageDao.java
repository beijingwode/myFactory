package com.wode.factory.user.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseQuery;
import com.wode.common.frame.base.EntityDao;

public interface BasePageDao<E> extends EntityDao<E, Long> {

	/**
	 * 分頁查詢
	 * mapper 中需要定義 findPage 及 findPage_count
	 * @param query 
	 * @return
	 */
	public PageInfo<E> findPage(BaseQuery query);

	/**
	 * 分頁查詢
	 * mapper 中需要定義 findPage 及 findPage_count
	 * @param query 
	 * @return
	 */
	public List<E> selectByModel(E model);
	

	public Long getId(E model);
}
