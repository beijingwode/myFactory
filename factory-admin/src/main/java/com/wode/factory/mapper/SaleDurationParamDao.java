/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import cn.org.rapid_framework.page.Page;

import org.apache.ibatis.annotations.Param;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.SaleDurationParam;

public interface SaleDurationParamDao extends  EntityDao<SaleDurationParam,Long>{
	public Page findPage(SaleDurationParam query);
	public void saveOrUpdate(SaleDurationParam entity);

	public SaleDurationParam selectByKey(@Param(value="key")String key);
}
