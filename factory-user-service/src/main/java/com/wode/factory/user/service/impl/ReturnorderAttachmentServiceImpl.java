/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ReturnorderAttachment;
import com.wode.factory.user.dao.ReturnorderAttachmentDao;
import com.wode.factory.user.query.ReturnorderAttachmentQuery;
import com.wode.factory.user.service.ReturnorderAttachmentService;

@Service("returnorderAttachmentService")
public class ReturnorderAttachmentServiceImpl extends BaseService<ReturnorderAttachment,java.lang.Long> implements  ReturnorderAttachmentService{
	@Autowired
	@Qualifier("returnorderAttachmentDao")
	private ReturnorderAttachmentDao returnorderAttachmentDao;
	
	public EntityDao getEntityDao() {
		return this.returnorderAttachmentDao;
	}
	
	public Page findPage(ReturnorderAttachmentQuery query) {
		return returnorderAttachmentDao.findPage(query);
	}
	
}
