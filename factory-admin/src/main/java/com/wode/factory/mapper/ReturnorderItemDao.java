/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import com.wode.factory.model.Returnorderitem;

public interface ReturnorderItemDao{
	public void saveOrUpdate(Returnorderitem entity);
	/**
	 * 根据退货ID查询退货项
	 * @param returnOrderId
	 * @return
	 */
	public Returnorderitem findByReturnOrderId(Long returnOrderId);

}
