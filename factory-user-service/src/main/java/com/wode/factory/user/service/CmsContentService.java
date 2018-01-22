/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.CmsChannel;
import com.wode.factory.model.CmsContent;

@Service("CmsContentService")
public interface CmsContentService extends EntityService<CmsContent,Long>{
	public List<CmsContent> findPageInfo(Long channelId,Integer pages,Integer size);
	public Long findByChannelIdCount(Long channelId);
	/**
	 * 
	 * 功能说明：根据栏目id查询文章信息
	 * 日期:	2015年6月9日
	 * 开发者:宋艳垒
	 *
	 * @param cmsContent
	 * @return
	 */
	public List<CmsContent> getBychannelId(Long channelId,Long id);
}
