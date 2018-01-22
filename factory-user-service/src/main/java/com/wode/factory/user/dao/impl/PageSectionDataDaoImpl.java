/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.PageSectionData;
import com.wode.factory.user.dao.PageSectionDataDao;
import com.wode.factory.user.vo.PageSectionDataVo;
import com.wode.factory.user.vo.PageTypeSettingVo;

@Repository("pageSectionDataDao")
public class PageSectionDataDaoImpl extends FactoryBaseDaoImpl<PageSectionData> implements PageSectionDataDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "PageSectionDataMapper";
	}

	@Override
	public List<PageSectionDataVo> selectByPageId(Long pageId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByPageId", pageId);
	}

	@Override
	public List<PageTypeSettingVo> findPageDataForApp(Long page) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findPageDataForApp", page);
	}
	
}
