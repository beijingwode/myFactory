/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.common.stereotype.QueryCached;
import com.wode.factory.model.PageSectionData;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.vo.PageSectionDataVo;
import com.wode.factory.user.vo.PageTypeSettingVo;

public interface PageSectionDataService extends FactoryEntityService<PageSectionData>{
    
	/**
	 * 按页获取配置数据
	 * @param pageId
	 * @return
	 */
	List<PageSectionDataVo> selectByPageId(Long pageId);
	
	@QueryCached(keyPreFix="findPageDataForApp", timeout=60*60*2)
	List<PageTypeSettingVo> findPageDataForApp(String pageKey);

	List<PageTypeSettingVo> findIndexPageData(UserFactory uf);
}
