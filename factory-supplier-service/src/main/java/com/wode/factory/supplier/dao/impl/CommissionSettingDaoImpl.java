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
import com.wode.factory.model.CommissionSetting;
import com.wode.factory.supplier.dao.CommissionSettingDao;
import com.wode.factory.supplier.query.CommissionSettingQuery;

@Repository("commissionSettingDao")
public class CommissionSettingDaoImpl extends BaseDao<CommissionSetting,java.lang.Long> implements CommissionSettingDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "CommissionSettingMapper";
	}
	
	public void saveOrUpdate(CommissionSetting entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(CommissionSettingQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public List<CommissionSetting> getBySupplerId(Map<String, Object> map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getBySupplerId",map);
	}

	@Override
	public Integer getBySupplerIdCount(Long supplerId) {
		Number num = this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getBySupplerIdCount",supplerId);
		return num.intValue();
	}
	

}
