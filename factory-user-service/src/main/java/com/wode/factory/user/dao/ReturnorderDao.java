/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Returnorder;
import com.wode.factory.user.query.ReturnorderQuery;

public interface ReturnorderDao extends EntityDao<Returnorder, Long> {
    public PageInfo findPage(ReturnorderQuery query);

    public void saveOrUpdate(Returnorder entity);

	public Returnorder getReturnOrdersById(Long returnOrderId);

}
