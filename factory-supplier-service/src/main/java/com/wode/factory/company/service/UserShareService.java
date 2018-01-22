/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.company.service;

import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.UserShare;

public interface UserShareService extends EntityService<UserShare,Long>{
	public List<UserShare> selectByModel(UserShare query);
}
