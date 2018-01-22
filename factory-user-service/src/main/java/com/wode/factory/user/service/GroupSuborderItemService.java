package com.wode.factory.user.service;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.model.Shop;


public interface GroupSuborderItemService {
	
	public void save(GroupSuborderitem groupSuborderitem);

	public void update(GroupSuborderitem subOrderItem);

	public List<GroupSuborderitem> findBySubOrderId(String subOrderId);

	public Shop findByShopId(Long shopId);

	public GroupSuborderitem findBySubOrderIdObj(String subOrderId);

	public List<GroupSuborderitem> findByOrderId(Long orderId);

	public Integer getCountByUserAndProduct(Long id, Long userId);

	/**
	 * 根据团id和商品id以及skuid获取商品的总数量
	 * @param groupId productId skuId
	 * @return
	 */
	public Integer getSuborderItemSum(String groupId, Long productId, String skuId);

	public List<GroupSuborderitem> findBySkuIdAndGroupId(Long groupId, Long skuId);

}
