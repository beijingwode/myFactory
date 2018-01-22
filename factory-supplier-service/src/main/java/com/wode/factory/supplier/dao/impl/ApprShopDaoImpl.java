/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.ApprShop;
import com.wode.factory.supplier.dao.ApprShopDao;

@Repository("apprShopDao")
public class ApprShopDaoImpl extends BasePageDaoImpl<ApprShop> implements ApprShopDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ApprShopMapper";
	}
	
	@Override
	public Long getId(ApprShop model) {
		return model.getId();
	}
	public ApprShop getShopApprIng(Long supplierId) {
		List<ApprShop> lst = this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getShopApprIng",supplierId);
		if(lst!=null && !lst.isEmpty()) return lst.get(0);
		
		return null;
	}
}
