package com.wode.factory.vo;

import java.io.Serializable;
import java.util.List;

import org.nutz.dao.entity.annotation.One;

import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.GroupBuyUser;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.model.Shop;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserImGroup;

public class GroupBuyUserVo extends GroupBuyUser implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 8725336396811019936L;
	
	//购物团
	@One(target = GroupBuy.class, field = "groupId")
	private GroupBuy groupBuy;
	private GroupOrders groupOrders;
	private GroupSuborderitem groupSuborderitem;
	private List<GroupSuborderitem> groupSuborderitemList;
	private UserImGroup userImGroup;
	public GroupBuy getGroupBuy() {
		return groupBuy;
	}

	public void setGroupBuy(GroupBuy groupBuy) {
		this.groupBuy = groupBuy;
	}

	public GroupOrders getGroupOrders() {
		return groupOrders;
	}

	public void setGroupOrders(GroupOrders groupOrders) {
		this.groupOrders = groupOrders;
	}

	public GroupSuborderitem getGroupSuborderitem() {
		return groupSuborderitem;
	}

	public void setGroupSuborderitem(GroupSuborderitem groupSuborderitem) {
		this.groupSuborderitem = groupSuborderitem;
	}

	public List<GroupSuborderitem> getGroupSuborderitemList() {
		return groupSuborderitemList;
	}

	public void setGroupSuborderitemList(List<GroupSuborderitem> groupSuborderitemList) {
		this.groupSuborderitemList = groupSuborderitemList;
	}

	public UserImGroup getUserImGroup() {
		return userImGroup;
	}

	public void setUserImGroup(UserImGroup userImGroup) {
		this.userImGroup = userImGroup;
	}
	
	
}
