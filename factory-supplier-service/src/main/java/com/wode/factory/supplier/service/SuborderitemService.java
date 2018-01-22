/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.supplier.query.SuborderitemQuery;

import cn.org.rapid_framework.page.Page;

public interface SuborderitemService extends EntityService<Suborderitem,Long>{
	
	
	public Page findPage(SuborderitemQuery query);

    /**
     * 根据id查找记录记录，返回单条记录
     * @param id
     * @return 对象/空
     */
    List<Suborderitem> selectByModel(Suborderitem record);
    
    List<Suborderitem> selectSuborderItemByrenturnOrderId(Long returnOrderId);
}
