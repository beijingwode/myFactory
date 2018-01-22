package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CmsChannel;
import com.wode.factory.user.dao.CmsChannelDao;
import com.wode.factory.user.service.CmsChannelService;
@Service("cmsChannelService")
public class CmsChannelServiceImpl extends BaseService<CmsChannel,java.lang.Long> implements CmsChannelService {
	@Autowired
	@Qualifier("cmsChannelDao")
	private CmsChannelDao cmsChannelDao;
	@Override
	public EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return this.cmsChannelDao;
	}
	
	/* 查询全部栏目
	 * @see com.wode.factory.user.service.CmsChannelService#findAllCmsChannel()
	 */
	@Override
	public List<CmsChannel> findAllCmsChannel() {
		// TODO Auto-generated method stub
		return cmsChannelDao.findAllCmsChannel();
	}


}
