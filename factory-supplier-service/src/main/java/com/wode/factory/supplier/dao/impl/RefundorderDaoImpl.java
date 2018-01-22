/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.open.RefundOrders;
import com.wode.factory.supplier.dao.RefundorderDao;

@Repository("refundorderDao")
public class RefundorderDaoImpl extends BaseDao<Refundorder,java.lang.Long> implements RefundorderDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "RefundorderMapper";
	}

	@Override
	public void saveOrUpdate(Refundorder entity) throws DataAccessException {
		if(entity.getRefundOrderId() == null) 
			save(entity);
		else 
			update(entity);
		
	}
	
	public Refundorder getRefundorById(Long id){
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getRefundorById",id);
	}

	@Override
	public List<RefundOrders> getRefundorWithSuborder(Map map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getRefundorWithSuborder",map);
	}

	@Override
	public List<Map> selectAttachmentsByRefundOrderId(Map map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".selectAttachmentsByRefundOrderId",map);
	}
}
