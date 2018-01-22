/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Returnorderlog;
import com.wode.factory.user.query.ReturnorderlogQuery;

public interface ReturnorderlogDao extends  EntityDao<Returnorderlog,Long>{
	public Page findPage(ReturnorderlogQuery query);
	public void saveOrUpdate(Returnorderlog entity);

}
