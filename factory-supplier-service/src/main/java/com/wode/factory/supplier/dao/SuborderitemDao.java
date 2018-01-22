/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import cn.org.rapid_framework.page.Page;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.supplier.query.SuborderitemQuery;

public interface SuborderitemDao extends  EntityDao<Suborderitem,Long>{
	public Page findPage(SuborderitemQuery query);
	public void saveOrUpdate(Suborderitem entity);

	/**
	 * 分頁查詢
	 * mapper 中需要定義 findPage 及 findPage_count
	 * @param query 
	 * @return
	 */
	public List<Suborderitem> selectByModel(Suborderitem model);
	public List<Suborderitem> selectSuborderItemByrenturnOrderId(Long returnOrderId);

}
