package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.RefundorderAttachment;
import com.wode.factory.user.dao.RefundorderAttachmentDao;

@Repository("refundorderAttachmentDao")
public class RefundorderAttachmentDaoImpl extends BaseDao<RefundorderAttachment,java.lang.Long> implements RefundorderAttachmentDao {
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "RefundorderAttachmentMapper";
	}

	@Override
	public void saveOrUpdate(RefundorderAttachment entity) {
		if(entity.getRefundOrderId() == null) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public void deleteByRefundOrderId(Long refundOrderId) {
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".deleteByRefundOrderId",refundOrderId);
	}

}
