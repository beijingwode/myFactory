/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.mongo.MongoBaseService;
import com.wode.factory.dao.ClientAccessLogDao;
import com.wode.factory.model.ClientAccessLog;
import com.wode.factory.service.ClientAccessLogService;

@Service("clientAccessLogService")
public class ClientAccessLogServiceImpl extends MongoBaseService<ClientAccessLog>
        implements ClientAccessLogService {

    @Autowired
    private ClientAccessLogDao clientAccessLogDao;
    
	@Override
	public ClientAccessLogDao getMongoDao() {
		return clientAccessLogDao;
	}

	@Override
	public Map<Long, Long> getDetailPvCnt(Long supplierId, Long productId) {
		return getMongoDao().getDetailPvCnt(supplierId,productId);
	}


}
