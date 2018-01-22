package com.wode.factory.user.util;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Product;
import com.wode.factory.user.model.CartItem;

/**
 * 购物车工具类
 * 
 * @author jt
 *
 */
@Component
public class CartUtil {
	@Qualifier("redis")
	@Autowired
	private RedisUtil redisUtil;

	public Long getCartShopId(Long productId) {
		String proJson = redisUtil.getMapData(RedisConstant.PRODUCT_PRE + productId, RedisConstant.PRODUCT_REDIS_INFO);
		if (!StringUtils.isEmpty(proJson)) {
			Product p = JsonUtil.getObject(proJson, Product.class);
			if (p != null) {
				return p.getShopId();
			}
		}
		return null;
	}

	/**
	 * 设置购物车中的店铺id
	 * 
	 * @param cartList
	 * @return
	 */
	public Map<Long, List<CartItem>> setCartItemShopId(Map<Long, List<CartItem>> cartList) {
		if (null != cartList && !cartList.isEmpty()) {
			for (Long key : cartList.keySet()) {
				List<CartItem> cartItemList = cartList.get(key);
				if (null != cartItemList && !cartItemList.isEmpty()) {
					for (CartItem cartItem : cartItemList) {
						cartItem.setShopId(getCartShopId(cartItem.getProductId()));
					}
				}
			}
		}
		return cartList;
	}

}
