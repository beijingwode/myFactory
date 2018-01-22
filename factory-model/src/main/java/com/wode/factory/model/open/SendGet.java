
package com.wode.factory.model.open;

import java.io.Serializable;

import com.wode.factory.model.OpenRequestBase;

public class SendGet extends OpenRequestBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1416715316775810491L;
	/**
	 * 订单id
	 */
	private String trade_id;
	/**
	 * 快递公司
	 */
	private String express_com;
	/**
	 * 运单号
	 */
	private String express_no;
	public String getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}
	public String getExpress_com() {
		return express_com;
	}
	public void setExpress_com(String express_com) {
		this.express_com = express_com;
	}
	public String getExpress_no() {
		return express_no;
	}
	public void setExpress_no(String express_no) {
		this.express_no = express_no;
	}
	
	
}
