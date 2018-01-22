/**
 * 
 */
package com.wode.factory.user.service;

import java.util.List;

import com.wode.factory.user.vo.PageSettingVo;

/**
 * 
 * <pre>
 * 功能说明: 
 * 日期:	2015年1月19日
 * 开发者:	宋艳垒
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年1月19日
 * </pre>
 */
public interface PageSetService{
	
	/**
	 * 
	 * 功能说明：
	 * 日期:	2015年3月16日
	 * 开发者:宋艳垒
	 *
	 * @param pid
	 * @return
	 */
	public List<PageSettingVo> findByPidAndChannel(int positionId,Integer channelId); 
}
