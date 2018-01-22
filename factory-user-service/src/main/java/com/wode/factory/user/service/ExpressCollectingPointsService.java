/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityService;
import com.wode.common.util.ActResult;
import com.wode.factory.model.Account;

@Service("expressCollectingPointsService")
public interface ExpressCollectingPointsService{
	public ActResult<Object> collectingPoints(Double lng,Double lat,String address,Boolean status);
}
