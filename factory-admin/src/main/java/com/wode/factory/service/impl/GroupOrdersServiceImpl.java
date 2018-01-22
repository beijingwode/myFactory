package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.GroupOrdersDao;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.service.GroupOrdersService;
import com.wode.factory.vo.GroupSubOrderVo;
@Service("groupOrdersService")
public class GroupOrdersServiceImpl implements GroupOrdersService {
	@Autowired
	private GroupOrdersDao groupOrdersDao;

	@Override
	public List<GroupSuborder> findByOrderId(Long orderId) {
		return groupOrdersDao.findByOrderId(orderId);
	}

	@Override
	public List<GroupOrders> findByGroupId(Long relationId) {
		return groupOrdersDao.findByGroupId(relationId);
	}

	@Override
	public PageInfo<GroupSubOrderVo> getgroupOrderList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<GroupSubOrderVo> ordersList = groupOrdersDao.getgroupOrderList(params);
		return new PageInfo<GroupSubOrderVo>(ordersList);
	}

	@Override
	public GroupOrders findById(Long orderId) {
		return groupOrdersDao.findById(orderId);
	}

	@Override
	public List<GroupSuborderitem> findByItemsSubId(String id) {
		return groupOrdersDao.findByItemsSubId(id);
	}

	@Override
	public GroupSuborder getBySubOrderId(String id) {
		return groupOrdersDao.getBySubOrderId(id);
	}

	@Override
	public void update(GroupSuborder order) {
		groupOrdersDao.update(order);
		
	}

	@Override
	public List<GroupSuborder> findSuborderIdLikeOrderId(Long orderId) {
		return groupOrdersDao.findSuborderIdLikeOrderId(orderId);
	}

	@Override
	public List<GroupOrders> getByOrderId(Long orderId) {
		return groupOrdersDao.getByOrderId(orderId);
	}
}
