package com.wode.factory.model.open;

import java.io.Serializable;

import com.wode.factory.model.OpenRequestBase;

public class SkusGet extends OpenRequestBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1416715316775810491L;

	/**
	 * 商品id
	 */
	private String p_id; 
	/**
	 * 商品名称
	 */
	private String p_title;
	/**
	 * 商品外部编码
	 */
	private String outer_id;
	
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getP_title() {
		return p_title;
	}
	public void setP_title(String p_title) {
		this.p_title = p_title;
	}
	public String getOuter_id() {
		return outer_id;
	}
	public void setOuter_id(String outer_id) {
		this.outer_id = outer_id;
	}
	
	
}
