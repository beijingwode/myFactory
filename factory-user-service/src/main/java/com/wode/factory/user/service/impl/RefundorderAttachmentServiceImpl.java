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

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.RefundorderAttachment;
import com.wode.factory.user.dao.RefundorderAttachmentDao;
import com.wode.factory.user.service.RefundorderAttachmentService;
import com.wode.common.frame.base.BaseService;

@Service("refundorderAttachmentService")
public class RefundorderAttachmentServiceImpl extends BaseService<RefundorderAttachment,java.lang.Long> implements  RefundorderAttachmentService{
	@Autowired
	@Qualifier("refundorderAttachmentDao")
	private RefundorderAttachmentDao refundorderAttachmentDao;

	@Override
	protected EntityDao getEntityDao() {
		return null;
	}

}
