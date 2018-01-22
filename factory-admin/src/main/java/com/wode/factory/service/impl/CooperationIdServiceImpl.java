/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wode.factory.mapper.CooperationIdMapper;
import com.wode.factory.model.CooperationId;
import com.wode.factory.service.CooperationIdService;

@Service("cooperationIdService")
public class CooperationIdServiceImpl implements CooperationIdService{
	@Resource
	private CooperationIdMapper cooperationIdMapper;

	@Override
	public int batchInsert(List<CooperationId> listCoop) {
		return cooperationIdMapper.batchInsert(listCoop);
	}
}
