package com.wode.factory.model.open;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.wode.factory.model.OpenRequestBase;

public class OrderListGet extends OpenRequestBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1416715316775810491L;
	
	/**
	 * 交易创建时间开始
	 */
	private String start_created;
	/**
	 * 交易创建时间结束
	 */
	private String end_created;
	/**
	 * 交易状态(默认为待发货订单)
	 */
	private String status;
	
	public static Map<String, Object> statusMap = new HashMap<String, Object>();
	
	static{
		statusMap.put("WAIT_BUYER_PAY", 0);
		statusMap.put("WAIT_SELLER_SEND_GOODS", 1);
		statusMap.put("WAIT_BUYER_CONFIRM_GOODS", 2);
		statusMap.put("TRADE_BUYER_SIGNED", 8);
		statusMap.put("TRADE_FINISHED", 4);
		statusMap.put("TRADE_CLOSED", -1);
	}
	
	public static String forEachStatusMap(Object value){
		for (String str : statusMap.keySet()) {
			if(statusMap.get(str).equals(value)){
				return str;
			}
		}
		return "";
	}

	public String getStart_created() {
		return start_created;
	}

	public void setStart_created(String start_created) {
		this.start_created = start_created;
	}

	public String getEnd_created() {
		return end_created;
	}

	public void setEnd_created(String end_created) {
		this.end_created = end_created;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
//	public static void main(String[] args) {
//		OrderListGet.statusMap.get(status)
//	}
	
	
}
