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
import com.wode.factory.model.ProductTrialLimitGroup;
import com.wode.factory.model.ProductTrialLimitItem;

public interface ProductTrialLimitGroupService extends FactoryEntityService<ProductTrialLimitGroup>{

	PageInfo getGroupManageListByMap(Map<String, Object> params);

	void addGroupMsg(ProductTrialLimitGroup productTrialLimitGroup);

	ProductTrialLimitGroup getGroupMsgById(Long id);

	void editGroupMsg(ProductTrialLimitGroup productTrialLimitGroup);

	List<ProductTrialLimitGroup> findGroupOperatorList();

	void updateStatus(Long id,Integer status);

	List<ProductTrialLimitItem> getProductListByMap();

	void updateGroupStatus();

	void delGroup(Long id);

}
