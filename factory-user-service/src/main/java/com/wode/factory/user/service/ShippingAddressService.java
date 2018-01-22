/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wode.common.util.ActResult;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.user.query.ShippingAddressQuery;

import cn.org.rapid_framework.page.Page;

@Service("shippingAddressService")
public interface ShippingAddressService{
	
	public Page findPage(ShippingAddressQuery query);
	
	public List<ShippingAddress> findByUserId(long userId);
	
	//更新地址信息
	public ShippingAddress saveOrupdateAddress(ShippingAddress shippingAddress);
		
	public ActResult deleteAddress(long userId,long shippingAddressId);

	ShippingAddress getById(Long userId,Long addressId);
}
