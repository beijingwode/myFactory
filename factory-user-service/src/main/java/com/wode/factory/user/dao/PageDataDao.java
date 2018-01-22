/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.PageTypeSetting;
import com.wode.factory.user.vo.PageTypeSettingVo;

public interface PageDataDao extends EntityDao<PageTypeSetting,Long>{
	/**
	 * 功能说明：根据页面标示和位置标示查询数据,位置标示为空则查询该页面全部的信息
	 * 日期:	2015年6月25日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param page 页面标示  （app首页，员工内购价首页）
	 * @param type 位置标示
	 * @param channelId 平台标示
	 * @return
	 */
	List<PageTypeSettingVo> findPageDataInfo(String  page,String type,int channelId, int pageNum, int pageSize);

	List<PageTypeSettingVo> findPageDataInfo(String  page,String type,int channelId);

	List<PageTypeSettingVo> findPageDataProducts(String  page,String type,int channelId, int pageNum, int pageSize);
	
}
