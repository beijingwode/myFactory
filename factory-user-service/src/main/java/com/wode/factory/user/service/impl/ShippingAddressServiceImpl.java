/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.user.dao.ShippingAddressDao;
import com.wode.factory.user.query.ShippingAddressQuery;
import com.wode.factory.user.service.ShippingAddressService;

import cn.org.rapid_framework.page.Page;

@Service("shippingAddressService")
public class ShippingAddressServiceImpl extends
		BaseService<ShippingAddress, java.lang.Long> implements
		ShippingAddressService {
	@Autowired
	@Qualifier("shippingAddressDao")
	private ShippingAddressDao shippingAddressDao;
	@Autowired
	private RedisUtil redisUtil;

	public EntityDao getEntityDao() {
		return this.shippingAddressDao;
	}

	public Page findPage(ShippingAddressQuery query) {
		return shippingAddressDao.findPage(query);
	}

	@Override
	public List<ShippingAddress> findByUserId(long userId) {
		String json = redisUtil.getData(RedisConstant.REDIS_USER_SHIPPING_ADDRESS+userId);
		if(!StringUtils.isEmpty(json)) {
			return JsonUtil.getList(json, ShippingAddress.class);
		} else {
			List<ShippingAddress> ls = shippingAddressDao.findByUserId(userId);
			redisUtil.setData(RedisConstant.REDIS_USER_SHIPPING_ADDRESS+userId, JsonUtil.toJsonString(ls));
			return ls;
		}
	}

	@Override
	public ShippingAddress saveOrupdateAddress(ShippingAddress shippingAddress) {
		// 判断默认地址------2015-11-12 gzy
		List<ShippingAddress> list = shippingAddressDao.findByUserId(shippingAddress.getUserId());
		if (list.size() < 1) {
			shippingAddress.setSend(1);
			shippingAddress.setOrderNo(0);
		}
		if (shippingAddress.getId() != null) {
			ShippingAddress shippingA = shippingAddressDao.getById(shippingAddress.getId());
			if (shippingAddress.getSend() == null) {
				shippingAddress.setSend(shippingA.getSend());
			}
		}

		if (shippingAddress.getSend() != null && shippingAddress.getSend() == 1) {
			shippingAddress.setOrderNo(0);
			for (ShippingAddress sa : list) {
				if (sa.getSend()==null || sa.getSend() == 1) {
					sa.setOrderNo(list.size());
					sa.setSend(0);
					shippingAddressDao.update(sa);
				}
			}
		} else {
			if (StringUtils.isNullOrEmpty(shippingAddress.getId()))
				shippingAddress.setOrderNo(list.size());
		}
		shippingAddressDao.saveOrUpdate(shippingAddress);
		redisUtil.del(RedisConstant.REDIS_USER_SHIPPING_ADDRESS+shippingAddress.getUserId());
		return shippingAddress;
	}

	@Override
	public ActResult deleteAddress(long userId, long shippingAddressId) {
		ShippingAddressQuery query = new ShippingAddressQuery();
		query.setUserId(userId);
		query.setId(shippingAddressId);
		ShippingAddress shippingAddress = shippingAddressDao.findByQuery(query);
		if (shippingAddress != null) {
			shippingAddressDao.deleteById(shippingAddressId);
			redisUtil.del(RedisConstant.REDIS_USER_SHIPPING_ADDRESS+userId);
			return ActResult.success(null);
		} else {
			return ActResult.fail("参数有误,没有权限操作");
		}

	}

	@Override
	public ShippingAddress getById(Long userId, Long addressId) {
		if(!StringUtils.isEmpty(userId)) {
			List<ShippingAddress> ls = this.findByUserId(userId);
			for (ShippingAddress shippingAddress : ls) {
				if(shippingAddress.getId().equals(addressId)) {
					return shippingAddress;
				}
			}
		}
		return super.getById(addressId);
	}

}
