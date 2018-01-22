package com.wode.factory.user.vo;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.Orders;
import com.wode.factory.model.SuborderitemLimitTicket;
import com.wode.factory.model.UserLimitTicket;
import com.wode.factory.user.model.CartItem;

public class OrderVO {
	
	private Orders orders;
		
	private List<CartItem>  subOrderiItem;
	
	private Map<Long,UserLimitTicket> userLimitTicketMap;
	
	private Map<String,List<SuborderitemLimitTicket>> mapSuborderitemLimitTicket;
	
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	
	public List<CartItem> getSubOrderiItem() {
		return subOrderiItem;
	}
	public void setSubOrderiItem(List<CartItem> subOrderiItem) {
		this.subOrderiItem = subOrderiItem;
	}
	public Map<Long,UserLimitTicket> getUserLimitTicketMap() {
		return userLimitTicketMap;
	}
	public void setUserLimitTicketMap(Map<Long,UserLimitTicket> useTickts) {
		this.userLimitTicketMap = useTickts;
	}
	public Map<String, List<SuborderitemLimitTicket>> getMapSuborderitemLimitTicket() {
		return mapSuborderitemLimitTicket;
	}
	public void setMapSuborderitemLimitTicket(Map<String, List<SuborderitemLimitTicket>> mapSuborderitemLimitTicket) {
		this.mapSuborderitemLimitTicket = mapSuborderitemLimitTicket;
	}
	
}
