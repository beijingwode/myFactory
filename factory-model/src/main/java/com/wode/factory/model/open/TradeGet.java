package com.wode.factory.model.open;

import java.io.Serializable;

import com.wode.factory.model.OpenRequestBase;

public class TradeGet extends OpenRequestBase implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7488128428703981352L;
	
	/**
	 * 订单id 
	 */
	private String trade_id;

	public String getTrade_id() {
		return trade_id;
	}

	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}
	
	

}
