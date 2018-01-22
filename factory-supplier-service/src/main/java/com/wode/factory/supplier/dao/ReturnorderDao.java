/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.Map;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Returnorder;
import com.wode.factory.supplier.query.ReturnorderQuery;

public interface ReturnorderDao extends  EntityDao<Returnorder,Long>{
	public Page findPage(ReturnorderQuery query);
	public void saveOrUpdate(Returnorder entity);
	//public Returnorder returnOrderByMap(Map<String, Object> map);
	public Returnorder returnOrderById(Long id);
	public void updatebymap(Map<String, Object> map);

}
