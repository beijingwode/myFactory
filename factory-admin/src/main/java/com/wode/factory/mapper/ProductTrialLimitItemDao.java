/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.FactoryBaseDao;
import com.wode.factory.model.LimitSupplierVo;
import com.wode.factory.model.ProductTrialLimitItem;
import com.wode.factory.vo.ProductTrialLimitItemProductVo;

public interface ProductTrialLimitItemDao extends  FactoryBaseDao<ProductTrialLimitItem>{

	List<ProductTrialLimitItem> getListByProductId(Long productId);

	ProductTrialLimitItem getProductTrialLimitItemByProductId(Long productId);

	Integer getTotalCount(Long groupid);

	List<LimitSupplierVo> findSupplierByProductId();

	List<ProductTrialLimitItemProductVo> getGroupLimitProductList(Map<String, Object> params);

	void delProductById(Long id);

	void insert(ProductTrialLimitItem productTrialLimitItem);

	List<ProductTrialLimitItem> getAssignValidGroupProductIdList(Map<String, Object> map);

	ProductTrialLimitItem getGroupLimitProductByMap(Map<String, Object> map);

	void deleteByGroupId(Long groupId);

	List<ProductTrialLimitItem> getValidGroupProductIdList();

}
