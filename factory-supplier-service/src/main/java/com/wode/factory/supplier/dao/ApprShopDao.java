/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.model.ApprShop;

public interface ApprShopDao extends  BasePageDao<ApprShop>{
	ApprShop getShopApprIng(Long supplierId);
}
