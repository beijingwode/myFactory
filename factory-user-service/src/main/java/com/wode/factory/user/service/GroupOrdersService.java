package com.wode.factory.user.service;


import java.math.BigDecimal;
import java.util.List;

import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserImGroup;
import com.wode.factory.user.vo.GroupOrderVO;
import com.wode.factory.vo.GroupBuyVo;
import com.wode.factory.vo.GroupOrdersVo;

public interface GroupOrdersService {
	
	public void save(GroupOrders groupOrder);

	public void update(GroupOrders order);

	public GroupOrders getById(Long orderId);
	
	/**
	 * 根据团id 和 用户id查询团订单
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public List<GroupOrders> getByGroupIDAndUserId(Long groupId, Long userId);

	/**
	 * 根据团订单id 计算省的运费
	 * @param GroupOrderId
	 * @return
	 */
	public String avgFreight(GroupOrders groupOrder);


	public List<GroupOrdersVo> getByGroupIdAndUserId(Long groupId, Long id);

	public int findByTuanYuanCount(Long groupId);

	public GroupBuy getByBuyId(Long imId);


	BigDecimal getSaveShippingFee(UserFactory user,Long groupId, Long[] skuIds, Integer[] nums);

	public GroupBuyVo findByBuyId(Long imGroupId,Long groupId);

	public GroupOrders getByOrderIdAndUserId(String orderId, Long id);

	public List<GroupOrdersVo> getByGroupID(Long groupId, Long id);

	public List<GroupOrders> getByGroupIdAndStatus(Long groupId, int staus);
	
	public GroupOrders getByGroupIdAndUserIdObj(Long groupId, Long id);
}
