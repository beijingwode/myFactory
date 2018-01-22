/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.user.dao.BargainFlowVoDao;
import com.wode.factory.user.model.BargainFlowVo;
import com.wode.factory.user.query.BargainFlowVoQuery;

@Repository("bargainFlowVoDao")
public class BargainFlowVoImpl extends BaseDao<BargainFlowVo, java.lang.Long> implements BargainFlowVoDao {

    @Override
    public String getIbatisMapperNamesapce() {
        return "BargainFlowVoMapper";
    }
    
    @Override
    public PageInfo<BargainFlowVo> findByQuery(BargainFlowVoQuery query) {
		if(query.getCurrencyId() == 0) {
	    	List<BargainFlowVo> list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findCashByQuery", query , new RowBounds(query.getPageNumber(), query.getPageSize()));
	    	return new PageInfo<BargainFlowVo>(list);
		} else {
	    	List<BargainFlowVo> list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findTicketByQuery", query , new RowBounds(query.getPageNumber(), query.getPageSize()));
	    	return new PageInfo<BargainFlowVo>(list);
		}
    }

	@Override
	public void saveOrUpdate(BargainFlowVo entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}
}
