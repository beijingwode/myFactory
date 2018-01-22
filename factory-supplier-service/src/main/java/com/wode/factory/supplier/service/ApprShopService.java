/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import com.wode.factory.company.service.BasePageService;
import com.wode.factory.model.ApprShop;

public interface ApprShopService extends BasePageService<ApprShop>{
	ApprShop getShopApprIng(Long supplierId);
}
