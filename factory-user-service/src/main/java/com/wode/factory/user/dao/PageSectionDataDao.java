/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;

import com.wode.common.frame.base.FactoryBaseDao;
import com.wode.factory.model.PageSectionData;
import com.wode.factory.user.vo.PageSectionDataVo;
import com.wode.factory.user.vo.PageTypeSettingVo;

public interface PageSectionDataDao extends  FactoryBaseDao<PageSectionData>{
    
	/**
	 * 按页获取配置数据
	 * @param pageId
	 * @return
	 */
	List<PageSectionDataVo> selectByPageId(Long pageId);

	List<PageTypeSettingVo> findPageDataForApp(Long page);
}
