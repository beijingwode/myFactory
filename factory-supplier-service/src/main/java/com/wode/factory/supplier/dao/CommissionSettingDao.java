/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CommissionSetting;
import com.wode.factory.supplier.query.CommissionSettingQuery;

public interface CommissionSettingDao extends  EntityDao<CommissionSetting,Long>{
	public Page findPage(CommissionSettingQuery query);
	public void saveOrUpdate(CommissionSetting entity);
	public List<CommissionSetting> getBySupplerId(Map<String, Object> map);
	public Integer getBySupplerIdCount(Long supplerId);

}
