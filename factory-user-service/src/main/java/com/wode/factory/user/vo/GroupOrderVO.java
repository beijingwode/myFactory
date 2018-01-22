package com.wode.factory.user.vo;

import java.util.List;

import com.wode.factory.model.GroupOrders;
import com.wode.factory.user.model.CartItem;

public class GroupOrderVO {
	
	private GroupOrders groupOrders;
	
	private List<CartItem>  subOrderiItem;
	
	public GroupOrders getGroupOrders() {
		return groupOrders;
	}
	public void setGroupOrders(GroupOrders groupOrders) {
		this.groupOrders = groupOrders;
	}
	public List<CartItem> getSubOrderiItem() {
		return subOrderiItem;
	}
	public void setSubOrderiItem(List<CartItem> subOrderiItem) {
		this.subOrderiItem = subOrderiItem;
	}
		
}
