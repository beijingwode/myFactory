/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.SaleDetail;
import com.wode.factory.supplier.query.SaleDetailQuery;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import cn.org.rapid_framework.page.Page;

@Service("saleDetailService")
public interface SaleDetailService extends EntityService<SaleDetail,Long>{
	
	public EntityDao getEntityDao() ;
	
	public Page findPage(SaleDetailQuery query);
	/**
	 * 根据查询条件查询对账单详情
	 * @param map
	 * @return
	 */
	public  List<SaleDetail> findlistPage(Map map);
	public  Integer findlistPageCount(Map map);
}
