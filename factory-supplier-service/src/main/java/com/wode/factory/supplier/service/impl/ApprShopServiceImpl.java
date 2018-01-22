/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.ApprShop;
import com.wode.factory.supplier.dao.ApprShopDao;
import com.wode.factory.supplier.service.ApprShopService;

@Service("apprShopService")
public class ApprShopServiceImpl extends BasePageServiceImpl<ApprShop> implements  ApprShopService{
	@Autowired
	private ApprShopDao apprShopDao;
	
	@Override
	protected ApprShopDao getBaseDao() {
		return apprShopDao;
	}

	@Override
	public ApprShop getShopApprIng(Long supplierId) {
		return apprShopDao.getShopApprIng(supplierId);
	}
}
