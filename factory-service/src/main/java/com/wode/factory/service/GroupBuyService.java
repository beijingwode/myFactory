package com.wode.factory.service;

import java.math.BigDecimal;
import java.util.List;

import org.nutz.dao.QueryResult;

import com.wode.common.util.ActResult;
import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.UserFactory;
import com.wode.factory.vo.GroupBuyVo;

public interface GroupBuyService {

	ActResult<GroupBuyVo> create(GroupBuyVo gbVo, UserFactory loginUser,String productIds);

	public GroupBuyVo getGroupBuyById(String groupId);


	void update(GroupBuyVo groupBuyVo);


	List<GroupBuyVo> findGroupBuyByShopAndUserId(Long shopId, Long userId);

	GroupBuyVo getById(Long groupId);
	
	/**
	 * 我的一起购分页查询
	 * @param fromType	fromType=1 是app 以外是微信
	 * @param userId	用户id
	 * @param page		第几页
	 * @param pageSize	每页有多少数据
	 * @return
	 */
	QueryResult query(Integer fromType,Long userId, Integer page, Integer pageSize);

	GroupBuy findByImGroupId(Long id);

	/**
	 * 根据团Id计算团订单共节省金额(团订单不存在返回null)
	 * @param groupId
	 * @return
	 */
	BigDecimal calculateSaveProductAmonut(Long groupId);
}
