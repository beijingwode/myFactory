package com.wode.factory.user.service;

import org.springframework.stereotype.Service;

import com.wode.common.util.ActResult;
import com.wode.factory.model.Product;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;

public interface CartService {

	void newAddCartItem(ActResult<Object> ret, Cart cart, CartItem cartItem);
	
}
