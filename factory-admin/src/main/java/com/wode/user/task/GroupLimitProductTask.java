package com.wode.user.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.redis.RedisUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.ProductTrialLimitItem;
import com.wode.factory.service.ProductTrialLimitGroupService;
import com.wode.factory.service.ProductTrialLimitItemService;
import com.wode.tongji.model.SmsTemplate;

@Service
public class GroupLimitProductTask {

	@Autowired
	private ProductTrialLimitItemService productTrialLimitItemService;
	
	@Autowired
	private ProductTrialLimitGroupService productTrialLimitGroupService;
	
	@Autowired
	private RedisUtil redisUtil;

	public void run() {
		productTrialLimitGroupService.updateGroupStatus();//更新分组状态是否已过期
//		Set<String> allKeys = redisUtil.getAllKeys(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+"*");
		//处理限购商品集合
		List<ProductTrialLimitItem> list = productTrialLimitGroupService.getProductListByMap();
		for (ProductTrialLimitItem productTrialLimitItem : list) {
			redisUtil.setData(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+productTrialLimitItem.getProductId(), String.valueOf(productTrialLimitItem.getProductId()),24*60*60);
		}
	}

}
