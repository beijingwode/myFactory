package com.wode.factory.company.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseQuery;



/**
 * 基础service接口
 * @author gaoyj
 *
 * @param <T>
 */
public interface BasePageService<T> extends com.wode.common.frame.base.EntityService<T,Long>  {

    /**
     * 根据id查找记录记录，返回单条记录
     * @param id
     * @return 对象/空
     */
    List<T> selectByModel(T record);
    

	/**
	 * 分頁查詢
	 * mapper 中需要定義 findPage 及 findPage_count
	 * @param query 
	 * @return
	 */
	public PageInfo<T> findPage(BaseQuery query);
}
