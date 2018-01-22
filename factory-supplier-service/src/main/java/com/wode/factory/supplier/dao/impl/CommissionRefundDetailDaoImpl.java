/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.CommissionRefund;
import com.wode.factory.model.CommissionRefundDetail;
import com.wode.factory.supplier.dao.CommissionRefundDetailDao;
import com.wode.factory.supplier.query.CommissionRefundDetailQuery;

@Repository("commissionRefundDetailDao")
public class CommissionRefundDetailDaoImpl extends BaseDao<CommissionRefundDetail,java.lang.Long> implements CommissionRefundDetailDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "CommissionRefundDetailMapper";
	}
	
	public void saveOrUpdate(CommissionRefundDetail entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(CommissionRefundDetailQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	
	/**
	 * 根据查询条件查询对账单详情
	 * @param map
	 * @return
	 */
	public  List<CommissionRefundDetail> findlistPage(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findlistPage",map);
	}
	public  Integer findlistPageCount(Map map){
		Number num = this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".findlistPageCount", map);
		return num.intValue();
	}
}
