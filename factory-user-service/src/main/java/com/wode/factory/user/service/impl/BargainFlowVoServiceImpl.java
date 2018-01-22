/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseService;
import com.wode.factory.user.dao.BargainFlowVoDao;
import com.wode.factory.user.model.BargainFlowVo;
import com.wode.factory.user.query.BargainFlowVoQuery;
import com.wode.factory.user.service.BargainFlowVoService;

@Service("bargainFlowVoService")
public class BargainFlowVoServiceImpl extends BaseService<BargainFlowVo, java.lang.Long>
        implements BargainFlowVoService {
    @Autowired
    @Qualifier("bargainFlowVoDao")
    private BargainFlowVoDao bargainFlowVoDao;

	@Override
	protected BargainFlowVoDao getEntityDao() {
		return bargainFlowVoDao;
	}

	@Override
	public PageInfo<BargainFlowVo> findByQuery(Long empId, Long currencyId,
			Date opDate, Integer page, Integer pageSize) {
		BargainFlowVoQuery query = new BargainFlowVoQuery();
        query.setEmpId(empId);
        query.setCurrencyId(currencyId);
        query.setOpDate(opDate);
        query.setPageNumber(page);
        query.setPageSize(pageSize);
        return getEntityDao().findByQuery(query);
	}


}
