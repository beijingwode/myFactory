package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Promotion;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.service.CartService;
import com.wode.factory.user.service.InventoryService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.PromotionService;

@Service("cartService")
public class CartServiceImpl implements CartService {
		
	@Qualifier("inventoryService")
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private PromotionService promotionService;
	
	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	
	@Override
	public void newAddCartItem(ActResult<Object> ret, Cart cart, CartItem cartItem) {
		String partNumber = cartItem.getPartNumber();
		int quantity = cartItem.getQuantity();
		Integer availableNumber = 0;
		if (StringUtils.isEmpty(cartItem.getPromotionProductId()))
			availableNumber = inventoryService.getInventoryFromRedis(Long
					.parseLong(cartItem.getPartNumber()));// 可买数量
		else {
			Promotion promotion = promotionService.getById(cartItem
					.getPromotionId());
			ActResult<Object> ac = inventoryService.lockPromotionStock(cartItem.getPromotionProductId(), cartItem.getQuantity(), promotion);// 可买数量
			if (!ac.isSuccess()) {
				ret.setSuccess(false);
				ret.setMsg("库存不足");
				return;
			}
		}
		if (null == availableNumber || availableNumber < 1) {
			ret.setSuccess(false);
			ret.setMsg("库存不足");
			return;
		}
		if (cartItem.getQuantity() > availableNumber) {
			ret.setSuccess(false);
			ret.setMsg("库存不足");
			return;
		}
		CartItem inItem = cart.getItem(Long.valueOf(partNumber));// 购物车的产品项
		if (inItem != null) {// 已购买
			// 库存检查
			if ((quantity) > availableNumber) {
				ret.setSuccess(false);
				ret.setMsg("库存不足");
				return;
			}
			if ((quantity) > 0) {// 防止数量为负数
				inItem.setQuantity(quantity);
				inItem.setPrice(cartItem.getPrice());
				inItem.setMaxCompanyTicket(cartItem.getMaxCompanyTicket());
				inItem.setRealPrice(cartItem.getRealPrice());
				inItem.setIsLadder(cartItem.getIsLadder());
				
				ret.setSuccess(true);
			}
		} else {// 未购买
			cart.addItem(cartItem);
			ret.setSuccess(true);
		}
	}
}
