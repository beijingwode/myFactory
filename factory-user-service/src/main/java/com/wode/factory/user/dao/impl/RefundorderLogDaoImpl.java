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
import com.wode.factory.model.Refundorderlog;
import com.wode.factory.user.dao.RefundorderLogDao;
import com.wode.factory.user.query.RefundorderLogQuery;

@Repository("refundorderLogDao")
public class RefundorderLogDaoImpl extends BaseDao<Refundorderlog,java.lang.Long> implements RefundorderLogDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "RefundorderLogMapper";
	}
	
	public void saveOrUpdate(Refundorderlog entity){
		if(entity.getRefundOrderLogId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(RefundorderLogQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	

}
