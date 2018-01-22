package com.wode.factory.service;

import java.util.List;

import com.wode.factory.model.GroupBuyUser;
import com.wode.factory.vo.GroupBuyUserVo;
import com.wode.factory.vo.GroupBuyVo;

public interface GroupBuyUserService {

	Integer getApplyByShop(Long shopId,Long userId, String productIds);

	Integer getAllCount(Long id);

	GroupBuyUserVo getByUserIdAndGroupId(Long id, String shoppingGroupId);

	List<GroupBuyVo> getGroupBuyList(Long userId, Long shopId,String productIds);

//	Object test(Long id);

	GroupBuyUser save(GroupBuyUser entity);

	void updateStatusByUserId(Long userId, Long groupId);

	void update(GroupBuyUserVo groupBuyUserVo);
}
