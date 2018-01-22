package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.vo.GroupSubOrderVo;

public interface GroupOrdersService {

	List<GroupSuborder> findByOrderId(Long orderId);

	List<GroupOrders> findByGroupId(Long relationId);

	PageInfo<GroupSubOrderVo> getgroupOrderList(Map<String, Object> params);

	GroupOrders findById(Long orderId);

	List<GroupSuborderitem> findByItemsSubId(String id);

	GroupSuborder getBySubOrderId(String id);

	void update(GroupSuborder order);

	List<GroupSuborder> findSuborderIdLikeOrderId(Long orderId);

	List<GroupOrders> getByOrderId(Long orderId);

}
