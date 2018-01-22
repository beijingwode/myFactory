/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.dao;

import java.util.Map;

import com.wode.common.mongo.MongoBaseDao;
import com.wode.factory.model.ClientAccessLog;

public interface ClientAccessLogDao extends  MongoBaseDao<ClientAccessLog>{

	Map<Long,Long> getDetailPvCnt(Long supplierId,Long productId);
}
