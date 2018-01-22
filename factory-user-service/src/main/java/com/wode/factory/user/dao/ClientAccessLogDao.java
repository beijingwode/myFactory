/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.mongo.MongoBaseDao;
import com.wode.factory.model.ClientAccessLog;

public interface ClientAccessLogDao extends  MongoBaseDao<ClientAccessLog>{
	Long[] getDayPvCnt(String date);
	List<JSONObject> getDaySearchKeyCnt(String date);
}
