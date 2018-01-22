
package com.wode.factory.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.CmsContent;
import com.wode.factory.user.dao.CmsContentDao;
import com.wode.factory.user.service.CmsContentService;

/**
 * 
 * <pre>
 * 功能说明: 
 * 日期:	2015年6月9日
 * 开发者:	宋艳垒
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年6月9日
 * </pre>
 */
@Service("CmsContentService")
public class CmsContentServiceImpl extends BaseService<CmsContent,java.lang.Long> implements CmsContentService{
	
	@Autowired
	private CmsContentDao cmsContentDao;

	@Override
	protected EntityDao getEntityDao() {
		return cmsContentDao;
	}

	@Override
	public List<CmsContent> getBychannelId(Long channelId,Long id) {
		return cmsContentDao.getBychannelId(channelId,id);
	}

	@Override
	public List<CmsContent> findPageInfo(Long channelId, Integer pages,
			Integer size) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("channelId", channelId);
		map.put("start", pages<=1?0:(pages*size-size));
		map.put("size", size);
		List<CmsContent> content = cmsContentDao.findPageInfo(map);
		for(CmsContent con : content){
			
			if(StringUtils.length(con.getTxt())>100)
				con.setTxt(StringUtils.tosubstring(con.getTxt(),100, ". . ."));
			
		}
		return content;
	}
	@Override
	public Long findByChannelIdCount(Long channelId) {
		CmsContent cmsContent = new CmsContent();
		cmsContent.setChannelid(channelId);
		return cmsContentDao.findByChannelIdCount(cmsContent);
	}
	
}
