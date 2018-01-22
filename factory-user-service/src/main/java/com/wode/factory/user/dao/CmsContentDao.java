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
import com.wode.factory.model.CmsContent;

public interface CmsContentDao extends EntityDao<CmsContent,Long>{
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
	public List<CmsContent> findPageInfo(Map<String,Object> map);
	public Long findByChannelIdCount(CmsContent cmsContent);
	
	/**
	 * 
	 * 功能说明：根据栏目id查询文章信息
	 * 日期:	2015年6月9日
	 * 开发者:宋艳垒
	 * @param id 
	 *
	 * @param cmsContent
	 * @return
	 */
	public List<CmsContent> getBychannelId(Long channelId,Long id);
	
	
	
	
}
