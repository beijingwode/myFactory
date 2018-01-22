/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wode.factory.model.CooperationId;

@Service("cooperationIdService")
public interface CooperationIdService{

	int batchInsert(List<CooperationId> listCoop);
	
	
}
