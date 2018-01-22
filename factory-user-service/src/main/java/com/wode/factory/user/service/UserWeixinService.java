/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.UserWeixin;

public interface UserWeixinService extends EntityService<UserWeixin,Long>{
	public List<UserWeixin> selectByModel(UserWeixin query);

	public UserWeixin getOneModelByOpenId(String openId);
	public UserWeixin getOneModelByUserId(Long userId);
}
