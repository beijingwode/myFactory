/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import com.wode.factory.user.vo.SkuVo;
import org.springframework.stereotype.Service;

import com.wode.common.stereotype.QueryCached;
import com.wode.factory.user.vo.PageTypeSettingVo;

@Service("pageDataService")
public interface PageDataService {

	List<PageTypeSettingVo> findPageDataInfo(String page,String dataType,int channelId, int pageNum, int pageSize);

	@QueryCached(keyPreFix="findPageDataInfo", timeout=60*60*2)
	List<PageTypeSettingVo> findPageDataInfo(String page,String dataType,int channelId);

	@QueryCached(keyPreFix="findAppIndexProducts", timeout=60*60*2)
	List<SkuVo> findAppIndexProducts(int pageNum, int pageSize);
	
	
	@QueryCached(keyPreFix="findAppIndexProductsByElaSeach", timeout=60*60*2)
	List<SkuVo> findAppIndexProductsByElaSeach(int pageNum, int pageSize);
 }
