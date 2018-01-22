package com.wode.api.util;

import java.util.HashMap;
import java.util.Map;

import com.wode.factory.outside.service.JPushService;
import com.wode.factory.outside.service.ServiceFactory;

public class JPushUtils {
	static JPushService push = ServiceFactory.getJPushService(com.wode.factory.user.util.Constant.OUTSIDE_SERVICE_URL);

	/**
	 * 功能说明：推送通知 日期: 2015年1月21日 开发者:张晨旭
	 *
	 * @param alert
	 *            推送内容
	 * @param title
	 *            推送的标题
	 * @param extras
	 *            内容(map集合)
	 * @param pushDriver
	 *            推送的设备 (android、ios)
	 * @param pushType
	 *            推送的类型(别名、标签、registrationID)
	 * @param alias
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	
	public static boolean sendNotification(String alert, String title,
			Map<String, String> extras, String pushDriver, String pushType,
			String pushName) {
		return push.sendNotification(JPushService.APP_TYPE_USER, alert, title, extras, pushDriver, pushType, pushName, false, null);
	}

	/**
	 * 功能说明：推送自定义消息
	 * 
	 * 日期: 2015年1月21日 开发者:张晨旭
	 *
	 * @param msgContent
	 *            推送内容
	 * @param title
	 *            推送的标题
	 * @param pushDriver
	 *            推送的设备(android、ios)
	 * @param pushType
	 *            推送的类型(标签、别名、registrationID)
	 * @param alias
	 *            对方的 别名/标签/registrationID
	 * @return
	 */
	/**
	 * 功能说明：
	 * 日期:	2015年1月21日
	 * 开发者:张晨旭
	 *
	 * @param msgContent
	 * @param title
	 * @param pushDriver
	 * @param pushType
	 * @param alias
	 * @return
	 */
	
	public static boolean sendMessage(String msgContent, String title,Map<String, String> extras,
			String pushDriver, String pushType,String statusId , String pushName) {
		return push.sendMessage(JPushService.APP_TYPE_USER, msgContent, title,extras, pushDriver, pushType, statusId, pushName, false, null);
	}
	/**
	 * 功能说明：推送通知(群推)
	 * 日期:	2015年3月13日
	 * 开发者:张晨旭
	 * 版本号:1.1
	 *
	 * @param alert
	 * @param title
	 * @param extras
	 * @param registrationIds
	 * @return
	 */
//	public boolean sendAllAndroidNotification(String alert, String title,Map<String, String> extras,Collection<String> registrationIds) {
//		try {
//		
//		this.getJPushClient();
//		Builder bd = PushPayload.newBuilder();
//		pl = bd.setPlatform(Platform.android())
//				.setAudience(Audience.registrationId(registrationIds))
//				.setNotification(
//						Notification.android(alert, title, extras))
//				.build();
//			PushResult pr =  pc.sendPush(pl);
//			return pr.isResultOK();
//		} catch (APIConnectionException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		} catch (APIRequestException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
//		return false;
//	}
//	public boolean sendSingleAndroidNotification(String alert, String title,Map<String, String> extras,String registrationIds) {
//		try {
//		this.getJPushClient();
//		Builder bd = PushPayload.newBuilder();
//		pl = bd.setPlatform(Platform.android())
//				.setAudience(Audience.registrationId(registrationIds))
//				.setNotification(
//						Notification.android(alert, title, extras))
//				.build();
//			PushResult pr =  pc.sendPush(pl);
//			return pr.isResultOK();
//		} catch (APIConnectionException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		} catch (APIRequestException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
//		return false;
//	}
	
	/**
	 * 功能说明：根据设备号推送(群推)
	 * 日期:	2015年3月13日
	 * 开发者:张晨旭
	 * 版本号:1.1
	 *
	 * @param msgContent
	 * @param title
	 * @param statusId
	 * @param registrationIds
	 * @return
	 */
//	public boolean sendAllAndroidMessage(String msgContent, String title,String statusId , Collection<String> registrationIds) {
//		
//		try {
//		this.getJPushClient();
//		Builder bd = PushPayload.newBuilder();
//		pl = bd.setPlatform(Platform.android())
//				.setAudience(Audience.registrationId(registrationIds))
//				.setMessage(
//						Message.newBuilder().setTitle(title)
//								.setMsgContent(msgContent).setContentType(statusId).build()) 
//				.build();
//			PushResult pr =  pc.sendPush(pl);
//			
//			return pr.isResultOK();
//		} catch (APIConnectionException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		} catch (APIRequestException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
//		return false;
//	}
	/**
	 * 功能说明：根据设备号推送(单推)
	 * 日期:	2015年3月13日
	 * 开发者:张晨旭
	 * 版本号:1.1
	 *
	 * @param msgContent
	 * @param title
	 * @param statusId
	 * @param registrationId
	 * @return
	 */
//	public boolean sendSingleAndroidMessage(String msgContent, String title,String statusId , String registrationId) {
//		try {
//		this.getJPushClient();
//		Builder bd = PushPayload.newBuilder();
//		pl = bd.setPlatform(Platform.android())
//				.setAudience(Audience.registrationId(registrationId))
//				.setMessage(
//						Message.newBuilder().setTitle(title)
//								.setMsgContent(msgContent).setContentType(statusId).build())
//				.build();
//			PushResult pr =  pc.sendPush(pl);
//			return pr.isResultOK();
//		} catch (APIConnectionException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		} catch (APIRequestException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
//		return false;
//	}
	
	public static String formatDriver(String type) {
		if("iPhone".equals(type)) return "ios";
		return type;
	}
	/**
	 * 功能说明：推送的格式
	 * 日期:	2015年3月17日
	 * 开发者:张晨旭
	 * 版本号:1.1
	 *
	 * @param json
	 * @return
	 */
	public static Map<String,String> pushFormat(String json){
		Map<String,String> map = new HashMap<String, String>();
		map.put("push", json);
		return map;
	}

}
