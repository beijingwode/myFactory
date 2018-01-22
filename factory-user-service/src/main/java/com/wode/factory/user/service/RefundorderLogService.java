/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.Refundorderlog;
import com.wode.factory.user.query.RefundorderLogQuery;

@Service("refundorderLogService")
public interface RefundorderLogService extends EntityService<Refundorderlog,Long>{
	
	public EntityDao getEntityDao() ;
	
	public Page findPage(RefundorderLogQuery query);
	
}
