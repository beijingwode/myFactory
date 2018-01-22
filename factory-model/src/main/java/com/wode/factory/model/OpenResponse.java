package com.wode.factory.model;

public class OpenResponse {
	
	private boolean success;
	private String message ="";
	private Object data;
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	public static OpenResponse success(Object data){
		OpenResponse ret=	new OpenResponse();
		ret.setSuccess(true);
		ret.setData(data);
		return ret;	
	}
	
	public static OpenResponse fail(String msg){
		OpenResponse ret=	new OpenResponse();
		ret.setSuccess(false);
		ret.setMessage(msg);
		return ret;	
	}
	
	public static OpenResponse successSetMsg(String msg){
		OpenResponse ret=	new OpenResponse();
		ret.setSuccess(true);
		ret.setMessage(msg);
		return ret;	
	}
}
