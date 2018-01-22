/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.vo.SupplierCategoryVo;

public interface SupplierCategoryVoDao extends  EntityDao<SupplierCategoryVo,Long>{
	

	/**
	 * 功能说明：根据条件检索数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param SupplierCloseCmd
	 * @return
	 */
	public List<SupplierCategoryVo> find(SupplierCategoryVo entity);
}
