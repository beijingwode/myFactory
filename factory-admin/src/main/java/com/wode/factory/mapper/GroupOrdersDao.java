package com.wode.factory.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.vo.GroupSubOrderVo;

/**
 * Created by zoln on 2015/7/24.
 */
public interface GroupOrdersDao  {
	List<GroupSuborder> findForCancel(@Param("createTime") Date createTime);

	List<GroupSuborder> findByOrderId(Long orderId);

	List<GroupOrders> findByGroupId(Long relationId);

	List<GroupSubOrderVo> getgroupOrderList(Map<String, Object> params);

	GroupOrders findById(Long orderId);

	List<GroupSuborderitem> findByItemsSubId(String id);

	GroupSuborder getBySubOrderId(String id);

	void update(GroupSuborder order);

	List<GroupSuborder> findSuborderIdLikeOrderId(Long orderId);

	List<GroupOrders> getByOrderId(Long orderId);

	List<GroupSuborderitem> findByOkOrders(Long groupId);
}
