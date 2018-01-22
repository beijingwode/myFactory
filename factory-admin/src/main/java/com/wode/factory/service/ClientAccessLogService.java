/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import java.util.Map;

public interface ClientAccessLogService {

	Map<Long,Long> getDetailPvCnt(Long supplierId,Long productId);;
}
