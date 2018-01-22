/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityService;
import com.wode.common.util.ActResult;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.Orders;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.model.Cart;

public interface OrdersService  extends EntityService<Orders,Long>{
	
	/**
	 * 根据用户ID、订单ID查找订单信息
	 * @param userId
	 * @param orderId
	 * @return
	 */
	Orders findById(long userId,long orderId);

	ActResult<Object> recordFlow(Long orderId, UserFactory user);
	
	ActResult<Object> balanceFlow(Long orderId, String suborderId,BigDecimal absCash, UserFactory user);		
	/**
	 * 判断用户是否已享受员工专享
	 */
	boolean isBought(Long userId, Long product);

	GroupOrders findGroupOrdersById(Long orderId);

	void updateGroupOrder(GroupOrders groupOrders);
	
	/**
	 * 合成下单列表，并做必要的商品存在及价格变化检查
	 * 
	 * @param fromType	下单方式 weixin,app,pc,
	 * @param user		用户
	 * @param skuIds	skuId
	 * @param sku_nuns  数量skuMap
	 * @param sku_realPrice	内购价skuMap 
	 * @param sku_freights	运费skuMap 
	 * @param sku_images	主图skuMap
	 * @param sku_pageKeys	入口skuMap
	 * @param sku_froms		入车方式skuMap
	 * @param sku_froms		一起购团内已购数量skuMap
	 * @param useExchangeTicket	使用换领币总额
	 * @param useExchangeCash	使用换领币总额（已激活）
	 * @param useExchangeSelf	使用换领币总额（无需激活）
	 * @param outSkus			sku对象列表
	 * @return
	 */
	ActResult<Cart> mergeBuyCart(String fromType,UserFactory user,List<Long> skuIds,Map<Long,Integer> sku_nums,
			Map<Long,BigDecimal> sku_realPrices,Map<Long,BigDecimal> sku_freights,
			Map<Long,String> sku_images,Map<Long,String> sku_pageKeys,Map<Long,String> sku_froms,
			Map<Long,Integer> sku_groups,String useExchangeTicket, String useExchangeCash,String useExchangeSelf,
			List<ProductSpecifications> outSkus);
}
