/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.Returnorder;

public interface ReturnorderDao{
	public void saveOrUpdate(Returnorder entity);
	/**
	 * 根据状态和最后确认时间查询退货
	 * @param status
	 * @param date
	 * @return 
	 */
	public List<Returnorder> findByStrtusAndLasttime(Map<String, Object> map);
	public void update(Returnorder returnorder);
	public Returnorder getById(Long returnOrderId);

}
