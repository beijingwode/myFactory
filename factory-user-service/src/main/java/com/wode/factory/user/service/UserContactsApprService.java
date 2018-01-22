/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.user.model.UserContactsAppr;
import com.wode.factory.user.vo.UserContactsApprVo;

public interface UserContactsApprService extends FactoryEntityService<UserContactsAppr>{

	public List<UserContactsApprVo> selectVoByModel(UserContactsAppr query);
}
