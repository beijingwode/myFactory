/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.LimitSupplierVo;
import com.wode.factory.model.ProductTrialLimitItem;
import com.wode.factory.vo.ProductTrialLimitItemProductVo;

public interface ProductTrialLimitItemService extends FactoryEntityService<ProductTrialLimitItem>{

	List<ProductTrialLimitItem> getListByProductId(Long productId);

	ProductTrialLimitItem getProductTrialLimitItemByProductId(Long productId);

	PageInfo<ProductTrialLimitItemProductVo> getGroupLimitProductList(Map<String, Object> params);

	List<LimitSupplierVo> findSupplierByProductId();

	void delProductById(Long id, Long productId, Long groupId);

	void addGroupLimitProduct(ProductTrialLimitItem productTrialLimitItem);

	ProductTrialLimitItem getGroupLimitProductByMap(Map<String, Object> map);
}
