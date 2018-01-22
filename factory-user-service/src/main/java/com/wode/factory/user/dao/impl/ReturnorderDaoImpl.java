/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Returnorder;
import com.wode.factory.user.dao.ReturnorderDao;
import com.wode.factory.user.query.ReturnorderQuery;

import java.util.List;

@Repository("returnorderDao")
public class ReturnorderDaoImpl extends BaseDao<Returnorder, java.lang.Long> implements ReturnorderDao {

    @Override
    public String getIbatisMapperNamesapce() {
        return "ReturnorderMapper";
    }

    public void saveOrUpdate(Returnorder entity) {
        if (entity.getReturnOrderId() == null)
            save(entity);
        else
            update(entity);
    }

    public PageInfo findPage(ReturnorderQuery query) {
        List<Returnorder> list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findPage", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
        return new PageInfo(list);
    }

	@Override
	public Returnorder getReturnOrdersById(Long returnOrderId) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getReturnOrdersById",returnOrderId);
	}


}
