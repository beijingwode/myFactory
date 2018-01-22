/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Refundorder;
import com.wode.factory.user.dao.RefundorderDao;
import com.wode.factory.user.query.RefundorderQuery;

@Repository("refundorderDao")
public class RefundorderDaoImpl extends BaseDao<Refundorder,java.lang.Long> implements RefundorderDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "RefundorderMapper";
	}
	
	public void saveOrUpdate(Refundorder entity){
		if(entity.getRefundOrderId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(RefundorderQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public Refundorder getRefundordersById(Long refundOrderId) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getRefundordersById",refundOrderId);
	}
	

}
