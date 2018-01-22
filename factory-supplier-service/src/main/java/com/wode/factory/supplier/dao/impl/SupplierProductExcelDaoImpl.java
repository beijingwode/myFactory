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
import com.wode.factory.model.SupplierProductExcel;
import com.wode.factory.supplier.dao.SupplierProductExcelDao;
import com.wode.factory.supplier.query.SupplierProductExcelQuery;
import com.wode.factory.supplier.query.SupplierSkuImageQuery;

@Repository("supplierProductExcelDao")
public class SupplierProductExcelDaoImpl extends BaseDao<SupplierProductExcel,java.lang.Long> implements SupplierProductExcelDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierProductExcelMapper";
	}
	
	public void saveOrUpdate(SupplierProductExcel entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public Page findPage(SupplierProductExcel query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<SupplierProductExcelQuery> selectPageInfo(SupplierProductExcelQuery productExcelQuery) {
		List<SupplierProductExcelQuery> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".findPage", productExcelQuery, new RowBounds(productExcelQuery.getPageNumber(), productExcelQuery.getPageSize()));
		return new PageInfo<SupplierProductExcelQuery>(list);
	}

	@Override
	public SupplierProductExcel getUndisposedByTimeAsc() {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".getUndisposedByTimeAsc");
	}
	

}
