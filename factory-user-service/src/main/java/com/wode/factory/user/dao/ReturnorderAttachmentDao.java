/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ReturnorderAttachment;
import com.wode.factory.user.query.ReturnorderAttachmentQuery;

public interface ReturnorderAttachmentDao extends  EntityDao<ReturnorderAttachment,Long>{
	public Page findPage(ReturnorderAttachmentQuery query);
	public void saveOrUpdate(ReturnorderAttachment entity);
	public void deleteByReturnOrderId(Long returnOrderId);
}
