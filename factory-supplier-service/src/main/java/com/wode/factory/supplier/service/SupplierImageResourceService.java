/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.SupplierImageResource;
import com.wode.factory.supplier.query.SupplierImageResourceQuery;

@Service("supplierImageResourceService")
public interface SupplierImageResourceService extends EntityService<SupplierImageResource,Long>{
	
	public EntityDao getEntityDao() ;
	public PageInfo<SupplierImageResourceQuery> selectPageInfo(SupplierImageResourceQuery imageResourceQuery);
	public PageInfo<SupplierImageResourceQuery> findDateGroupBy(SupplierImageResourceQuery imageResourceQuery);
	
	public Map<String,List<SupplierImageResource>> findPageImage(List<SupplierImageResourceQuery> dateS);
	
}
