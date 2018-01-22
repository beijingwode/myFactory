/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.RefundorderAttachment;

public interface RefundorderAttachmentDao extends  EntityDao<RefundorderAttachment,Long>{

	public void saveOrUpdate(RefundorderAttachment entity);

	public void deleteByRefundOrderId(Long refundOrderId);

}
