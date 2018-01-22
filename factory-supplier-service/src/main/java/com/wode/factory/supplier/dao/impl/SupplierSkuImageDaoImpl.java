/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.SupplierSkuImage;
import com.wode.factory.supplier.dao.SupplierSkuImageDao;
import com.wode.factory.supplier.query.SupplierSkuImageQuery;

@Repository("supplierSkuImageDao")
public class SupplierSkuImageDaoImpl extends BaseDao<SupplierSkuImage,java.lang.Long> implements SupplierSkuImageDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierSkuImageMapper";
	}
	
	public void saveOrUpdate(SupplierSkuImage entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public Page findPage(SupplierSkuImage query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * 分页查询
	 */
	@Override
	public PageInfo<SupplierSkuImageQuery> selectPageInfo(SupplierSkuImageQuery skuImageQuery) {
		List<SupplierSkuImageQuery> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".findPage", skuImageQuery, new RowBounds(skuImageQuery.getPageNumber(), skuImageQuery.getPageSize()));
		return new PageInfo<SupplierSkuImageQuery>(list);
	}

	@Override
	public void insert(SupplierSkuImage skuImage) {
		// TODO Auto-generated method stub
		getSqlSession().insert(getIbatisMapperNamesapce()+".insert", skuImage);
	}

}
