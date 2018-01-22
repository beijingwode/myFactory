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
import com.wode.factory.model.ProductTrialLimitGroup;
import com.wode.factory.model.ProductTrialLimitItem;

public interface ProductTrialLimitGroupDao extends FactoryBaseDao<ProductTrialLimitGroup>{

	List<ProductTrialLimitGroup> getGroupManageListByMap(Map<String, Object> params);

	void insert(ProductTrialLimitGroup productTrialLimitGroup);

	ProductTrialLimitGroup getById(Long id);

	void editGroupMsg(ProductTrialLimitGroup productTrialLimitGroup);

	List<ProductTrialLimitGroup> findGroupOperatorList();

	void updateStatus(Map<String, Object> map);

	Integer findGroupIsExsit(Map<String, Object> map);

	void updateGroupStatus();

	ProductTrialLimitGroup getGroupById(Long groupId);

	void delete(Long id);


}
