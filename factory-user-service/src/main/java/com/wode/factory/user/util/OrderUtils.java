package com.wode.factory.user.util;

import com.wode.common.util.StringUtils;
import com.wode.factory.model.ExchangeOrders;
import com.wode.factory.model.Orders;
import com.wode.factory.model.UserFactory;

/**
 * 
 * 此方法只封装订单相关的一些不用查库的业务逻辑
 * 目的：避免很多相同代码出现在各种工程中，不便于维护修改
 * @author JT
 *
 */
public class OrderUtils {
	
	/**
	 * 设置自提姓名手机号
	 * @param order
	 * @param user
	 */
	public static void setMobileAndAddress(Orders order,UserFactory user){
		order.setName(user.getNickName());
		if (!StringUtils.isEmpty(user.getPhone())) {
			order.setMobile(user.getPhone());
			order.setAddress("自提(电话:" + user.getPhone() + ")");
		}else{
			order.setMobile("空");
			order.setAddress("自提(电话:空 )");
		}
	}

	/**
	 * 设置自提姓名手机号
	 * @param order
	 * @param user
	 */
	public static void setMobileAndAddress(ExchangeOrders order,UserFactory user){
		order.setName(user.getNickName());
		if (!StringUtils.isEmpty(user.getPhone())) {
			order.setMobile(user.getPhone());
			order.setAddress("自提(电话:" + user.getPhone() + ")");
		}else{
			order.setMobile("空");
			order.setAddress("自提(电话:空 )");
		}
	}
}
