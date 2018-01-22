/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CmsContent;
import com.wode.factory.supplier.dao.CmsContentDao;
import com.wode.factory.supplier.query.CmsContentQuery;
import com.wode.factory.supplier.service.CmsContentService;

@Service("cmsContentService")
public class CmsContentServiceImpl extends BaseService<CmsContent,java.lang.Long> implements  CmsContentService{
	@Autowired
	@Qualifier("cmsContentDao")
	private CmsContentDao cmsContentDao;
	
	public EntityDao getEntityDao() {
		return this.cmsContentDao;
	}
	
	public Page findPage(CmsContentQuery query) {
		return cmsContentDao.findPage(query);
	}
	
}
