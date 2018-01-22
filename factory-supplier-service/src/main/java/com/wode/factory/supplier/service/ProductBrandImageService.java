/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import com.wode.factory.company.service.BasePageService;
import com.wode.factory.model.ProductBrandImage;

public interface ProductBrandImageService extends BasePageService<ProductBrandImage>{

	public void deleteByShop(Long shopId);
	public void copyByShop(Long supplierId,Long shopId);
}
