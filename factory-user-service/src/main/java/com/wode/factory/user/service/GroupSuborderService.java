package com.wode.factory.user.service;

import java.util.List;

import com.wode.factory.model.GroupSuborder;

public interface GroupSuborderService {
	
	public void save(GroupSuborder groupSuborder);

	public void update(GroupSuborder subOrder);

	public List<GroupSuborder> findByOrderId(Long orderId);

	public GroupSuborder findByOrderIdObj(Long orderId);
	
	public GroupSuborder getById(String subOrderId);
}
