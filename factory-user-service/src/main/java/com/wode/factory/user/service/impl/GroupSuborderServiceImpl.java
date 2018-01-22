package com.wode.factory.user.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.db.DBUtils;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.user.service.GroupSuborderService;

@Service("groupSuborderService")
public class GroupSuborderServiceImpl implements GroupSuborderService {

	@Autowired
	private Dao dao;
	
	@Autowired
    DBUtils dbUtils;
	
	@Override
	public void save(GroupSuborder groupSuborder) {
		dao.insert(groupSuborder);
	}

	@Override
	public void update(GroupSuborder subOrder) {
		dao.update(subOrder);
	}

	@Override
	public List<GroupSuborder> findByOrderId(Long orderId) {
		return dao.query(GroupSuborder.class, Cnd.where("orderId", "=", orderId));
	}

	@Override
	public GroupSuborder findByOrderIdObj(Long orderId) {
		List<GroupSuborder> ls = this.findByOrderId(orderId);
		if(ls!=null && !ls.isEmpty()) {
			return ls.get(0);
		}
		return null;
	}

	@Override
	public GroupSuborder getById(String subOrderId) {
		return dao.fetch(GroupSuborder.class,Cnd.where("subOrderId", "=", subOrderId));
	}

}
