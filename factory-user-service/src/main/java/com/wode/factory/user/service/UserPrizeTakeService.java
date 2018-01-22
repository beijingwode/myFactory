/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.Map;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.UserPrizeTake;

public interface UserPrizeTakeService extends FactoryEntityService<UserPrizeTake>{

	UserPrizeTake findUserPrizeByMap(Map<String, Object> map);

}
