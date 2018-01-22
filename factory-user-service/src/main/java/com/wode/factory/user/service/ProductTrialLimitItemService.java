/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.ProductTrialLimitGroup;
import com.wode.factory.model.ProductTrialLimitItem;

public interface ProductTrialLimitItemService extends FactoryEntityService<ProductTrialLimitItem>{

	List<ProductTrialLimitItem> getListByProductId(Long productId);

	Integer getProductTrialLimitItemByProductId(List<Long> list);
}
