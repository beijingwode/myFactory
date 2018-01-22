package com.wode.factory.supplier.facade;

import com.wode.common.util.ActResult;
import com.wode.factory.model.UserDevice;
import com.wode.factory.model.UserFactory;

public interface LoginFacade {

	public static final String CACHE_APP_TICKET_ID = "CACHE_APP_TICKET_ID";
	/**
	 * 设备登录处理
	 * 
	 * @param userId
	 * @param ticket
	 * @param mobileId
	 * @param deviceName
	 * @param deviceType
	 * @return
	 */
	ActResult<String> doCommit(String ticket, String mobileId, String aliasName,String deviceName,
			String deviceType);
	
	/**
	 * 判断 用户是否登录
	 * @param ticket
	 * @return
	 */
	ActResult<UserFactory> hasLogin(String ticket);
	
	void logOut(String ticket);
	
	void doPushNotify(Long userId,UserDevice ud,String title,String msg);

	ActResult<UserFactory> hasLogin(Long uid);	
}
