/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CmsChannel;
import com.wode.factory.model.CmsContent;

public interface CmsChannelDao extends EntityDao<CmsChannel,Long>{
//	public Page findPage(CollectionShopQuery query);
	/**
	 * 功能说明：查询全部的栏目名称信息
	 * 日期:	2015年6月9日
	 * 开发者:张晨旭
	 *
	 * @return
	 */
	public List<CmsChannel> findAllCmsChannel();
	/**
	 * 功能说明：查询栏目对应的信息，并分页
	 * 日期:	2015年6月9日
	 * 开发者:张晨旭
	 *
	 * @param channdlId
	 * @param start
	 * @param size
	 * @return
	 */
	public List<CmsContent> findPageInfo(Integer channdlId,Integer pages,Integer size);
	
	
}
