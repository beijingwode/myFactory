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
import com.wode.factory.model.ReturnorderAttachment;
import com.wode.factory.user.dao.ReturnorderAttachmentDao;
import com.wode.factory.user.query.ReturnorderAttachmentQuery;

@Repository("returnorderAttachmentDao")
public class ReturnorderAttachmentDaoImpl extends BaseDao<ReturnorderAttachment,java.lang.Long> implements ReturnorderAttachmentDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ReturnorderAttachmentMapper";
	}
	
	public void saveOrUpdate(ReturnorderAttachment entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ReturnorderAttachmentQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public void deleteByReturnOrderId(Long returnOrderId) {
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".deleteByReturnOrderId",returnOrderId);
		
	}

}
