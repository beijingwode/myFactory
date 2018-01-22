package com.wode.factory.model;
/**
 * 开放接口入参类
 * @author user
 *
 */
public class OpenRequestBase {
	
	/**
	 * appid
	 */
	private String appid;
	/**
	 * 调用方法
	 */
	private String method;
	/**
	 * 时间戳
	 */
	private String timestamp;
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 商家id
	 */
	private String supplierId;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

    
}
