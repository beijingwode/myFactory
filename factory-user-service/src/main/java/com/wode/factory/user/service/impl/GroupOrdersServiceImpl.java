package com.wode.factory.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.db.DBUtils;
import com.wode.common.util.NumberUtil;
import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.model.Product;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.user.facade.ShippingFacade;
import com.wode.factory.user.service.GroupOrdersService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.vo.ProductSpecificationsVo;
import com.wode.factory.vo.GroupBuyVo;
import com.wode.factory.vo.GroupOrdersVo;

@Service("groupOrdersService")
public class GroupOrdersServiceImpl implements GroupOrdersService {
	
	@Autowired
	private Dao dao;
	@Autowired
	private UserService userService;
	
	@Autowired
    DBUtils dbUtils;
	@Autowired
	ProductSpecificationsService specificationsService;
	@Autowired
	private ProductService productService;
	
	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService productSpecificationsService;

	/**
	 * 运费
	 */
	@Autowired
	private ShippingFacade shippingFacade;

	@Autowired
	private ProductLadderService productLadderService;
	
	@Override
	public void save(GroupOrders groupOrder) {
		groupOrder.setOrderId(dbUtils.CreateID());
		dao.insert(groupOrder);
	}

	@Override
	public void update(GroupOrders order) {
		dao.update(order);
	}

	@Override
	public GroupOrders getById(Long orderId) {
		return dao.fetch(GroupOrders.class, orderId);
	}

	@Override
	@Transactional
	public String avgFreight(GroupOrders groupOrders) {
		if(null != groupOrders) {
			//新增团员本身的运费
			BigDecimal newMemberShipping = groupOrders.getTotalShipping(); 			
			//查询团购
			GroupBuy groupBuy = dao.fetch(GroupBuy.class, groupOrders.getGroupId());
			// 原团运费
			BigDecimal oldTotalShipping = groupBuy.getTotalShipping();
			// 原团商品总额
			BigDecimal oldAllTotalPrice = groupBuy.getAllTotalPrice();
			// 原团运费合计
			BigDecimal oldAllTotalShipping = groupBuy.getAllTotalShipping();
			// 原团商品实际总额
			BigDecimal oldTotalPrice = groupBuy.getTotalPrice();
			//本次节省金额
			BigDecimal saveAmount =BigDecimal.ZERO;
			
			if(oldTotalShipping==null) {
				oldTotalShipping = BigDecimal.ZERO;
			}
			if(oldAllTotalPrice==null) {
				oldAllTotalPrice = BigDecimal.ZERO;
			}
			if(oldAllTotalShipping==null) {
				oldAllTotalShipping = BigDecimal.ZERO;
			}
			if(oldTotalPrice==null) {
				oldTotalPrice = BigDecimal.ZERO;
			}
			//原来节省
			saveAmount = oldAllTotalPrice.add(oldAllTotalShipping).subtract(oldTotalPrice.add(oldTotalShipping));
			
			BigDecimal newGroupTotalShipping =BigDecimal.ZERO;
			BigDecimal newAllTotalPricel =BigDecimal.ZERO;
			BigDecimal newAllTotalShipping =BigDecimal.ZERO;
			BigDecimal newTotalPrice =BigDecimal.ZERO;
			//现在节省
			BigDecimal newSaveAmount =BigDecimal.ZERO;
			
			GroupBuy newGroupBuy = null;
			//查询同一个团购支付过的订单
			List<GroupOrders> groupOrdersList  = dao.query(GroupOrders.class, Cnd.where("group_id","=",groupOrders.getGroupId())
					                        .and("status","=","1").asc("orderId"));
			
			boolean needMerge = false;
			GroupOrders oldGroupOrders = null;
			BigDecimal mergeAdd = BigDecimal.ZERO;
			
			if(null != groupOrdersList && groupOrdersList.size() >0) {
				//每一个笔支付成功的运费 总运费合计
				groupBuy.setAllTotalShipping(oldAllTotalShipping.add(groupOrders.getTotalShipping()));
				
				//如果就一个订单，不平均运费
				if(groupOrdersList.size() == 1) {
					groupOrders.setNowShipping(groupOrders.getTotalShipping());
					//dao.update(groupOrders);
					//groupBuy.setStatus(1);
					//dao.update(groupBuy);
					newGroupBuy = successGroupBuy(groupOrders,groupBuy,groupOrders.getTotalShipping());
					// 现团运费
					newGroupTotalShipping = newGroupBuy.getTotalShipping();
					// 现团商品总额
					newAllTotalPricel = newGroupBuy.getAllTotalPrice();
					// 现团运费合计
					newAllTotalShipping = newGroupBuy.getAllTotalShipping();
					// 现团商品实际总额
					newTotalPrice = newGroupBuy.getTotalPrice();
					//现在节省
					newSaveAmount = newAllTotalPricel.add(newAllTotalShipping).subtract(newTotalPrice.add(newGroupTotalShipping));
					saveAmount = newSaveAmount.subtract(saveAmount);
					
					groupOrders.setSaveAmount(saveAmount);
					dao.update(groupOrders);
					return newGroupBuy.getIm_groupId();					
				}
				
				////////////////////////////////////////////////////////////////////////////////////////////////////
				// 循环判断是否已参过团
				for (GroupOrders groupOrders2 : groupOrdersList) {
					if(groupOrders2.getUserId().equals(groupOrders.getUserId())) {
						if(!groupOrders2.getOrderId().equals(groupOrders.getOrderId())) {
							// 用户相同 但 订单id 不同
							needMerge = true;
							oldGroupOrders= groupOrders2;
							break;
						}
					}
				}
				////////////////////////////////////////////////////////////////////////////////////////////////////

				////////////////////////////////////////////////////////////////////////////////////////////////////
				//计算加入此订单后所需的运费
				BigDecimal newTotalShipping = getNowShipping(groupBuy,groupOrdersList,null ,null,null);
				if(!NumberUtil.isGreaterZero(newTotalShipping)) {
					// 如果已达包邮
					// 则所有成员运费为0
					for (GroupOrders groupOrders2 : groupOrdersList) {
						groupOrders2.setNowShipping(BigDecimal.ZERO);
						dao.update(groupOrders2);
					}
					groupOrders.setNowShipping(BigDecimal.ZERO);
				} else {
				////////////////////////////////////////////////////////////////////////////////////////////////////
								
				///////////////////////////////////////////////////////////////////////////////////////////////////
				// 分配运费处理
					if(needMerge) {
						// 如果是用户追单  追加的总运费 直接拨给该用户
						mergeAdd=newTotalShipping.subtract(oldTotalShipping);
					} else {
						//已经分配的运费
						BigDecimal useShipping = BigDecimal.ZERO;
						int i = 1;//循环下标
						//如果多个订单，循环计算运费
						for (GroupOrders gOrders : groupOrdersList) {
							//现在运费包邮全包邮
							if(newTotalShipping.compareTo(BigDecimal.ZERO) == 0) {
								gOrders.setNowShipping(BigDecimal.ZERO);
							} else {
								//规则
								//最后一个订单，用所有运费-已经被评分的
								if(i == groupOrdersList.size()) {
									gOrders.setNowShipping(newTotalShipping.subtract(useShipping));
								} else {
								
									//如果本身包邮不需要评分运费
									if(gOrders.getNowShipping().compareTo(BigDecimal.ZERO) != 0) {
										//平分运费
										//团占比=（原来团运费/（原来团运费+ 新增团员本身的运费））
										// 原订单先需运费=（原需团支付运费 / 原来团运费 ） * 团占比 *新运费
										//          = 原需团支付运费*新运费/（原来团运费+ 新增团员本身的运费）
										gOrders.setNowShipping(gOrders.getNowShipping().multiply(newTotalShipping)
												.divide(oldTotalShipping.add(newMemberShipping),2,BigDecimal.ROUND_HALF_DOWN)
												);
										useShipping = useShipping.add(gOrders.getNowShipping());
									}
								}
							}
							dao.update(gOrders);
							if(gOrders.getOrderId().equals(groupOrders.getOrderId())) {
								groupOrders.setNowShipping(gOrders.getNowShipping());
							}
							i++;
						}
					}
				}
				///////////////////////////////////////////////////////////////////////////////////////////////////
				

				///////////////////////////////////////////////////////////////////////////////////////////////////
				// 追单合并出处理
				if(needMerge) {
					groupOrders.setStatus(0); // 作废新单 
					dao.update(groupOrders);
					
					// 总价合并
					oldGroupOrders.setTotalProduct(oldGroupOrders.getTotalProduct().add(groupOrders.getTotalProduct()));
					// 总运费合并
					oldGroupOrders.setTotalShipping(oldGroupOrders.getTotalShipping().add(groupOrders.getTotalShipping()));
					// 总折扣合并
					oldGroupOrders.setTotalAdjustment(oldGroupOrders.getTotalAdjustment().add(groupOrders.getTotalAdjustment()));
					// realPrice合并
					oldGroupOrders.setRealPrice(oldGroupOrders.getRealPrice().add(groupOrders.getRealPrice()));
					// 追加运费
					if(NumberUtil.isGreaterZero(mergeAdd)) {
						oldGroupOrders.setNowShipping(oldGroupOrders.getNowShipping().add(mergeAdd));
					} 
					dao.update(oldGroupOrders);
					
					// 变更  t_group_suborder,t_group_suborderitem 中的订单id
					dao.update("t_group_suborder", Chain.make("orderId", oldGroupOrders.getOrderId()), Cnd.where("orderId","=",groupOrders.getOrderId()));
					dao.update("t_group_suborderitem", Chain.make("order_id", oldGroupOrders.getOrderId()), Cnd.where("order_id","=",groupOrders.getOrderId()));

					groupBuy.setTotalShipping(newTotalShipping);
					//设置商品总价及实际金额
					newGroupBuy = dealSubOrderItem(groupBuy);
					
					dao.update(newGroupBuy);
					// 现团运费
					newGroupTotalShipping = newGroupBuy.getTotalShipping();
					// 现团商品总额
					newAllTotalPricel = newGroupBuy.getAllTotalPrice();
					// 现团运费合计
					newAllTotalShipping = newGroupBuy.getAllTotalShipping();
					// 现团商品实际总额
					newTotalPrice = newGroupBuy.getTotalPrice();
					newSaveAmount = newAllTotalPricel.add(newAllTotalShipping).subtract(newTotalPrice.add(newGroupTotalShipping));
					saveAmount = newSaveAmount.subtract(saveAmount);
					groupOrders.setSaveAmount(saveAmount);; // 作废新单 
					dao.update(groupOrders);
					return groupBuy.getIm_groupId();
				} else {					
					newGroupBuy = successGroupBuy(groupOrders,groupBuy,newTotalShipping);
					// 现团运费
					newGroupTotalShipping = newGroupBuy.getTotalShipping();
					// 现团商品总额
					newAllTotalPricel = newGroupBuy.getAllTotalPrice();
					// 现团运费合计
					newAllTotalShipping = newGroupBuy.getAllTotalShipping();
					// 现团商品实际总额
					newTotalPrice = newGroupBuy.getTotalPrice();
					
					newSaveAmount = newAllTotalPricel.add(newAllTotalShipping).subtract(newTotalPrice.add(newGroupTotalShipping));
					saveAmount = newSaveAmount.subtract(saveAmount);
					groupOrders.setSaveAmount(saveAmount);; // 作废新单 
					dao.update(groupOrders);
					return newGroupBuy.getIm_groupId();
				}
				///////////////////////////////////////////////////////////////////////////////////////////////////
			}
		}
		return null;
	}

	/**
	 * 团购订单成功后后续处理
	 * @param groupOrders
	 * @param groupBuy 
	 */
	private GroupBuy successGroupBuy(GroupOrders groupOrders, GroupBuy groupBuy,BigDecimal newTotalShipping) {

		//团长创建团购并支付成功
		groupBuy.setJoinNum(groupBuy.getJoinNum()+1);
		groupBuy.setTotalShipping(newTotalShipping);
		//处理已支付的订单修改购物团的商品总价及实际支付价
		groupBuy = dealSubOrderItem(groupBuy);
		
		dao.update(groupBuy);
		//创建团购订单团聊
		//String imgroupId = userContactsService.createGroupBuyGroup(groupOrders.getUserId(), "" + groupOrders.getGroupId());
		return groupBuy;
	}
	/**
	 * 处理已支付的订单修改购物团的商品总价及实际支付价
	 * @param groupBuy
	 * @return
	 */
	private GroupBuy dealSubOrderItem(GroupBuy groupBuy) {
		List<GroupSuborderitem> sList = getSuborderItemByGroupIdAndStatus(groupBuy,1);
		if(sList!=null && sList.size()>0) {
			BigDecimal allTotalProductPrice = BigDecimal.ZERO;//购买商品总价值（内购价乘数量）
			BigDecimal totalPrice = BigDecimal.ZERO;//购买商品实际总价值（阶梯价乘数量）
			for (GroupSuborderitem groupSuborderitem : sList) {
				
				ProductSpecificationsVo sku = this.specificationsService.findByIdCache(groupSuborderitem.getSkuId(), null);
				if(sku!=null ) {//计算商品总价
					allTotalProductPrice = allTotalProductPrice.add(sku.getInternalPurchasePrice().multiply(new BigDecimal(groupSuborderitem.getNumber())));
				}
				BigDecimal price = productLadderService.getPriceBySkuidAndPrice(groupSuborderitem.getSkuId(), groupSuborderitem.getNumber());
				if(price!=null && NumberUtil.isGreaterZero(price)) {//满足阶梯价
					totalPrice=totalPrice.add(price.multiply(new BigDecimal(groupSuborderitem.getNumber())));
				}else {
					totalPrice=totalPrice.add(groupSuborderitem.getInternalPurchasePrice().multiply(new BigDecimal(groupSuborderitem.getNumber())));
				}
			}
			groupBuy.setTotalPrice(totalPrice);
			groupBuy.setAllTotalPrice(allTotalProductPrice);
		}
		
		return groupBuy;
	}

	private List<GroupSuborderitem> getSuborderItemByGroupIdAndStatus(GroupBuy groupBuy, int i) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT gsi.subOrderItemId,gsi.subOrderId,gsi.order_id,gsi.productId,gsi.partNumber,gsi.skuId,gsi.group_id,gsi.internal_purchase_price,SUM(gsi.number) number");
		sb.append(" FROM t_group_suborderitem gsi LEFT JOIN t_group_suborder gs on (gs.subOrderId=gsi.subOrderId)");
		sb.append(" WHERE gsi.group_id=@groupId AND gs.`status`=@status ");	// 团下所有子单，当前用户
		sb.append(" GROUP BY gsi.skuId");	// 团下所有子单
		
		Sql sql = Sqls.create(sb.toString());
		sql.params().set("groupId",groupBuy.getId());
		sql.params().set("status",1);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(GroupSuborderitem.class));
		dao.execute(sql);
		List<GroupSuborderitem> sList = sql.getList(GroupSuborderitem.class);
		return sList;
	}

	/**
	 * 获取现在运费
	 * @return
	 */
	private BigDecimal getNowShipping(GroupBuy groupBuy,List<GroupOrders> groupOrdersList,Long[] skuIds, Integer[] nums,UserFactory newUser) {
		Long sid = 0L;
		Map<Long,BigDecimal> freightMap = new HashMap<Long,BigDecimal>();			
		Map<Long,Integer> skuMap = new HashMap<Long,Integer>();			
		Map<Long,Product> productMap = new HashMap<Long,Product>();
		Map<Long,Integer> numMap = new HashMap<Long,Integer>();
		Map<Long,BigDecimal> amountMap = new HashMap<Long,BigDecimal>();
		List<Long> pids = new ArrayList<Long>();
		// 核算新用户
		if(null !=skuIds ) {
			for (int z = 0 ;z< skuIds.length;z++) {
				if(skuMap.containsKey(skuIds[z])) {
					skuMap.put(skuIds[z], skuMap.get(skuIds[z]) + nums[z]);
				}else {
					skuMap.put(skuIds[z], nums[z]);
				}
			}
		}
		for (GroupOrders groupOrders : groupOrdersList) {
    		List<GroupSuborderitem> groupSuborderitemList = dao.query(GroupSuborderitem.class, Cnd.where("order_id","=",groupOrders.getOrderId()));
    		if(null != groupSuborderitemList && groupSuborderitemList.size() >0) {
    			//循环订单商品行这里先加进去，处理在其他地方处理
    			for (GroupSuborderitem groupSuborderitem : groupSuborderitemList) {
    				if(skuMap.containsKey(groupSuborderitem.getSkuId())) {
        				skuMap.put(groupSuborderitem.getSkuId(), skuMap.get(groupSuborderitem.getSkuId()) + groupSuborderitem.getNumber());
        			}else {
        				skuMap.put(groupSuborderitem.getSkuId(), groupSuborderitem.getNumber());
        			}
				}
    		}
		}
		for (Long skuId : skuMap.keySet()) {
			ProductSpecificationsVo sku = this.specificationsService.findByIdCache(skuId, null);
			Product p = productService.findById(sku.getProductId(),false);
			specificationsService.resetPrice(sku, p, null, false,skuMap.get(skuId));
			BigDecimal skuAmout = sku.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(skuMap.get(skuId)));
			if(freightMap.containsKey(p.getId())) {
				numMap.put(p.getId(),numMap.get(p.getId()) + skuMap.get(skuId));
				amountMap.put(p.getId(),amountMap.get(p.getId()).add(skuAmout));
			} else {
				sid = p.getSupplierId();
				// 默认先放0
				freightMap.put(p.getId(), BigDecimal.ZERO);
				pids.add(p.getId());
				productMap.put(p.getId(),p);
				numMap.put(p.getId(), skuMap.get(skuId));
				amountMap.put(p.getId(), skuAmout);
			}
		 }
		
		/*for (GroupOrders groupOrders : groupOrdersList) {
			UserFactory user = userService.getById(groupOrders.getUserId());
    		List<GroupSuborderitem> groupSuborderitemList = dao.query(GroupSuborderitem.class, Cnd.where("order_id","=",groupOrders.getOrderId()));
    		if(null != groupSuborderitemList && groupSuborderitemList.size() >0) {
    			//循环订单商品行这里先加进去，处理在其他地方处理
    			for (GroupSuborderitem groupSuborderitem : groupSuborderitemList) {

        			ProductSpecificationsVo sku = this.specificationsService.findByIdCache(groupSuborderitem.getSkuId(), null);
        			Product p = productService.findById(sku.getProductId(),false);
        			specificationsService.resetPrice(sku, p, user, false,groupSuborderitem.getNumber());
        			BigDecimal skuAmout = sku.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(groupSuborderitem.getNumber()));
        			if(freightMap.containsKey(p.getId())) {
        				numMap.put(p.getId(),numMap.get(p.getId()) + groupSuborderitem.getNumber());
        				amountMap.put(p.getId(),amountMap.get(p.getId()).add(skuAmout));
        			} else {
        				sid = p.getSupplierId();
        				// 默认先放0
        				freightMap.put(p.getId(), BigDecimal.ZERO);
        				pids.add(p.getId());
        				productMap.put(p.getId(),p);
        				numMap.put(p.getId(), groupSuborderitem.getNumber());
        				amountMap.put(p.getId(), skuAmout);
        			}
				}
    		}
		}*/
		
		/*if(null !=skuIds ) {
    		for (int z = 0 ;z< skuIds.length;z++) {
    			ProductSpecificationsVo sku = this.specificationsService.findByIdCache(skuIds[z], null);
    			Product p = productService.findById(sku.getProductId(),false);
    			//团内已购商品数量
    			Integer groupNum = groupSuborderItemService.getSuborderItemSum(groupBuy.getId()+"",p.getId(),sku.getId()+"");
    			specificationsService.resetPrice(sku, p, newUser, false,nums[z]+groupNum);
    			BigDecimal skuAmout = sku.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(nums[z]));
    			if(freightMap.containsKey(p.getId())) {
    				numMap.put(p.getId(),numMap.get(p.getId()) + nums[z]);
    				amountMap.put(p.getId(),amountMap.get(p.getId()).add(skuAmout));
    			} else {
    				sid = p.getSupplierId();
    				// 默认先放0
    				freightMap.put(p.getId(), BigDecimal.ZERO);
    				pids.add(p.getId());
    				productMap.put(p.getId(),p);
    				numMap.put(p.getId(), nums[z]);
    				amountMap.put(p.getId(), skuAmout);
    			}
    		}
    	}*/
		
		// 商家单位统计计算运费
		shippingFacade.calculateSupplierShippingFee("0", groupBuy.getAid(), userService.getById(groupBuy.getUserId()),sid, pids, productMap, numMap, amountMap, freightMap,null);
		
		// 所有运费合计
		BigDecimal nowShipping = BigDecimal.ZERO;
		for (BigDecimal pshippin : freightMap.values()) {
			nowShipping=nowShipping.add(pshippin);
		}
		
		return nowShipping;
	}

	@Override
	public List<GroupOrders> getByGroupIDAndUserId(Long groupId, Long userId) {
		Cnd cnd = Cnd.where("group_id","=",groupId);
		if(null != userId) {
			cnd.and("userId","=",userId);
		}
		return dao.query(GroupOrders.class, cnd);
	}

	@Override
	public List<GroupOrdersVo> getByGroupID(Long groupId,Long id) {
		return dao.query(GroupOrdersVo.class,Cnd.where("group_id","=",groupId).and("status", "<>", "0"));
	}
	@Override
	public List<GroupOrdersVo> getByGroupIdAndUserId(Long groupId, Long id) {
		return dao.query(GroupOrdersVo.class,Cnd.where("group_id","=",groupId).and("status", "<>", "0").and("userId","=",id));
	}

	@Override
	public int findByTuanYuanCount(Long groupId) {
		 int i = dao.count("t_group_buy_user",Cnd.where("group_id", "=", groupId).and("status", ">=", "1"));
		return i;
	}

	@Override
	public GroupBuy getByBuyId(Long imId) {
		
		return dao.fetch(GroupBuy.class,Cnd.where("im_group_id", "=", imId));
	}


	
	@Override
	public BigDecimal getSaveShippingFee(UserFactory user,Long groupId, Long[] skuIds, Integer[] nums) {
		BigDecimal result = BigDecimal.ZERO;
		// 查询团购
		GroupBuy groupBuy = dao.fetch(GroupBuy.class, groupId);
		// 查询同一个团购支付过的订单
		List<GroupOrders> groupOrdersList = dao.query(GroupOrders.class,
				Cnd.where("group_id", "=", groupId).and("status", "=", "1").asc("orderId"));
		
		if (null != groupOrdersList && groupOrdersList.size() > 0) {
			// 计算现在本人的运费
			BigDecimal nowShipping = BigDecimal.ZERO;

			Long sid = 0L;
			Map<Long,BigDecimal> freightMap = new HashMap<Long,BigDecimal>();			
			Map<Long,Product> productMap = new HashMap<Long,Product>();
			Map<Long,Integer> numMap = new HashMap<Long,Integer>();
			Map<Long,BigDecimal> amountMap = new HashMap<Long,BigDecimal>();
			List<Long> pids = new ArrayList<Long>();

			for (int z = 0; z < skuIds.length; z++) {
				ProductSpecificationsVo sku = this.specificationsService.findByIdCache(skuIds[z], null);
				Product p = productService.findById(sku.getProductId(),false);
				specificationsService.resetPrice(sku, p, user, false,nums[z]);
				BigDecimal skuAmout = sku.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(nums[z]));
				if(freightMap.containsKey(p.getId())) {
					numMap.put(p.getId(),numMap.get(p.getId()) + nums[z]);
					amountMap.put(p.getId(),amountMap.get(p.getId()).add(skuAmout));
				} else {
					sid = p.getSupplierId();
					// 默认先放0
					freightMap.put(p.getId(), BigDecimal.ZERO);
					pids.add(p.getId());
					productMap.put(p.getId(),p);
					numMap.put(p.getId(), nums[z]);
					amountMap.put(p.getId(), skuAmout);
				}
			}
			
			shippingFacade.calculateSupplierShippingFee("0", groupBuy.getAid(), user,sid, pids, productMap, numMap, amountMap, freightMap,null);
			
			for (BigDecimal pshippin : freightMap.values()) {
				nowShipping=nowShipping.add(pshippin);
			}
			
			// 计算本人运费结束
			if (nowShipping.compareTo(BigDecimal.ZERO) == 0) {
				return BigDecimal.ZERO;
			}
			/*------------------------*/

			// 计算加入此订单后所需的运费
			BigDecimal newTotalShipping = getNowShipping(groupBuy,groupOrdersList, skuIds, nums,user);
			boolean needMerge = false;
			for (GroupOrders gOrders : groupOrdersList) {
				if(user.getId().equals(gOrders.getUserId())) {
					needMerge=true;
					break;
				}
			}
			
			if(needMerge) {
				// 追单，只需承担新增团运费
				BigDecimal mergeAdd =newTotalShipping.subtract(groupBuy.getTotalShipping());
				if(!NumberUtil.isGreaterZero(mergeAdd)) {
					mergeAdd= BigDecimal.ZERO;
				}

				return nowShipping.subtract(mergeAdd);
			} else {

				// 计算加入此订单后所需的运费结束
				// 已经分配的运费
				BigDecimal useShipping = BigDecimal.ZERO;
				// 元团运费
				BigDecimal oldTotalShipping = groupBuy.getTotalShipping();
				// 如果多个订单，循环计算运费
				for (GroupOrders gOrders : groupOrdersList) {
					// 现在运费包邮
					if (newTotalShipping.compareTo(BigDecimal.ZERO) == 0) {
						return nowShipping;
					}
					// 规则
					// 如果本身包邮不需要评分运费
					if (gOrders.getNowShipping().compareTo(BigDecimal.ZERO) != 0) {
						// 平分运费
						// 团占比（原来团运费/（原来团运费+ 新增团员本身的运费））
						// 原订单先需运费=（原需团支付运费 / 原来团运费 ） * 团占比 *新运费
						//          = 原需团支付运费*新运费/（原来团运费+ 新增团员本身的运费）
						gOrders.setNowShipping(gOrders.getNowShipping().multiply(newTotalShipping)
								.divide(oldTotalShipping.add(nowShipping), 2, BigDecimal.ROUND_HALF_DOWN));
						useShipping = useShipping.add(gOrders.getNowShipping());
					}
				}
				return nowShipping.subtract(newTotalShipping.subtract(useShipping));
			}
		}
		return result;
	}

	@Override
	public GroupBuyVo findByBuyId(Long imGroupId,Long groupId) {
		if(imGroupId!=null){
			return dao.fetch(GroupBuyVo.class,Cnd.where("im_group_id","=",imGroupId));
		}else{
			return dao.fetch(GroupBuyVo.class,Cnd.where("id","=",groupId));
		}
	}

	@Override
	public GroupOrders getByOrderIdAndUserId(String orderId, Long id) {
		return dao.fetch(GroupOrders.class,Cnd.where("orderId","=",orderId).and("userId","=",id));
	}

	@Override
	public GroupOrders getByGroupIdAndUserIdObj(Long groupId, Long id) {
		return dao.fetch(GroupOrders.class,Cnd.where("group_id","=",groupId).and("userId","=",id));
	}

	@Override
	public List<GroupOrders> getByGroupIdAndStatus(Long groupId, int staus) {
		return dao.query(GroupOrders.class,Cnd.where("group_id","=",groupId).and("status","=",staus));
	}
}
