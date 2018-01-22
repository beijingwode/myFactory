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
import com.wode.factory.model.SaleDetail;
import com.wode.factory.supplier.dao.SaleDetailDao;
import com.wode.factory.supplier.query.SaleDetailQuery;

@Repository("saleDetailDao")
public class SaleDetailDaoImpl extends BaseDao<SaleDetail,java.lang.Long> implements SaleDetailDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SaleDetailMapper";
	}
	
	public void saveOrUpdate(SaleDetail entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(SaleDetailQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	

	/**
	 * 根据查询条件查询对账单详情
	 * @param map
	 * @return
	 */
	public  List<SaleDetail> findlistPage(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findlistPage",map);
	}
	public  Integer findlistPageCount(Map map){
		Number num = this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".findlistPageCount",map);
		return num.intValue();
	}
}
