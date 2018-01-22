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
import com.wode.factory.model.SupplierImageResource;
import com.wode.factory.supplier.dao.SupplierImageResourceDao;
import com.wode.factory.supplier.query.SupplierImageResourceQuery;

@Repository("supplierImageResourceDao")
public class SupplierImageResourceDaoImpl extends BaseDao<SupplierImageResource,java.lang.Long> implements SupplierImageResourceDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierImageResourceMapper";
	}
	
	public void saveOrUpdate(SupplierImageResource entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public Page findPage(SupplierImageResource query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<SupplierImageResourceQuery> selectPageInfo(SupplierImageResourceQuery imageResourceQuery) {
		List<SupplierImageResourceQuery> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".findPage", imageResourceQuery, new RowBounds(imageResourceQuery.getPageNumber(), imageResourceQuery.getPageSize()));
		return new PageInfo<SupplierImageResourceQuery>(list);
	}

	@Override
	public PageInfo<SupplierImageResourceQuery> findDateGroupBy(SupplierImageResourceQuery imageResourceQuery) {
		List<SupplierImageResourceQuery> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".findDateGroupBy", imageResourceQuery, new RowBounds(imageResourceQuery.getPageNumber(), imageResourceQuery.getPageSize()));
		return new PageInfo<SupplierImageResourceQuery>(list);
	}

	@Override
	public List<SupplierImageResource> findPageImage(SupplierImageResource supplierImageResource) {
		List<SupplierImageResource> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".findPage",supplierImageResource);
		return list;
	}

}
