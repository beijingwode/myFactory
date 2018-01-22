/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.SaleBill;
import com.wode.factory.supplier.query.SaleBillQuery;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import cn.org.rapid_framework.page.Page;

@Service("saleBillService")
public interface SaleBillService extends EntityService<SaleBill,Long>{
	
	public EntityDao getEntityDao() ;
	
	public Page findPage(SaleBillQuery query);
	
	/**
	 * 根据查询条件查询对账单
	 * @param map
	 * @return
	 */
	public  List<SaleBill> findlistPage(Map map);
	public  Integer findlistPageCount(Map map);
	
	
}
