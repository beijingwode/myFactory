package com.wode.factory.user.vo;

import java.util.List;

import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.GroupBuyUser;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborderitem;

public class GroupAndOrderVO {
	
	private GroupOrders groupOrders;
	
	private GroupBuy groupBuy;
	
	private String shopName;
	
	private GroupBuyUser groupBuyUser;
	
	private List<GroupSuborderitem> groupSuborderitems;

	public GroupOrders getGroupOrders() {
		return groupOrders;
	}

	public void setGroupOrders(GroupOrders groupOrders) {
		this.groupOrders = groupOrders;
	}

	public GroupBuy getGroupBuy() {
		return groupBuy;
	}

	public void setGroupBuy(GroupBuy groupBuy) {
		this.groupBuy = groupBuy;
	}

	public GroupBuyUser getGroupBuyUser() {
		return groupBuyUser;
	}

	public void setGroupBuyUser(GroupBuyUser groupBuyUser) {
		this.groupBuyUser = groupBuyUser;
	}

	public List<GroupSuborderitem> getGroupSuborderitems() {
		return groupSuborderitems;
	}

	public void setGroupSuborderitems(List<GroupSuborderitem> groupSuborderitems) {
		this.groupSuborderitems = groupSuborderitems;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
}
