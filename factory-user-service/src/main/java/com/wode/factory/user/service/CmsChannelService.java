/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.CmsChannel;

@Service("cmsChannelService")
public interface CmsChannelService extends EntityService<CmsChannel,Long>{
	public EntityDao getEntityDao() ;
	
	public List<CmsChannel> findAllCmsChannel();
 }
