/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.user.model.BargainFlowVo;

@Service("bargainFlowVoService")
public interface BargainFlowVoService extends EntityService<BargainFlowVo,Long>{
	
	
	/**
	 * app分页显示评论
	 * @return
	 */
	public PageInfo<BargainFlowVo> findByQuery(Long empId,Long cId, Date opDate, Integer page, Integer pageSize);
    
}
