package com.wode.api.facade;

import java.util.Map;

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
	/**
	 * 判断 用户是否登录
	 * @param ticket
	 * @return
	 */
	ActResult<UserFactory> hasLogin(Long uid);
	
	void logOut(String ticket);
	
	void doFisrtCommit(Long userId,String userName,UserDevice ud);
	
	void doPushNotify(Long userId,UserDevice ud,String title,String msg);	
	
	void doPushWxBanlanceMsg(Long userId,String amount,String date,String note,String cId);	
	void doPushWxTemplateMsg(Long userId, String type, Map<String,Object> param);
}
