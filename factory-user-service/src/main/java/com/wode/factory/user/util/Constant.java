package com.wode.factory.user.util;

import java.util.ResourceBundle;

public class Constant {

	private static ResourceBundle res = ResourceBundle.getBundle("application");
	//企业接口Domian
	public static String QIYE_API_URL = res.getString("qiye.api.url");
	//共通用户Domian
	public static String COMM_USER_URL = res.getString("comm.user.url");
	//系统domain
	public static String SYSTEM_DOMAIN = res.getString("system.domain");
	//系统domain
	public static String WX_OPEN_APPID = res.getString("wxOpen.appId");
	//外围接口url
	public static String OUTSIDE_SERVICE_URL = res.getString("outside.url");
	//页面跳转同步通知页面路径
	public static String ALIPAY_RETURN_URL = res.getString("AlipayConfig.return.url");
	//服务器异步通知页面路径
	public static String ALIPAY_NOTIFY_URL = res.getString("AlipayConfig.notify.url");
	//服务器异步通知页面路径
	public static String WXPAY_NOTIFY_URL = res.getString("WxPayConfig.notify.url");
	//短信签名
	public static String SMS_SIGNATURE = "【我的福利】";
	//运营后台url
	public static String CACHE_API_URL = res.getString("cache.url");
	//APP 版本号
	public static String APP_VERSION_CODE = res.getString("app.versionCode");
	//APP 更新标致
	public static String APP_MUST_UPDATE = res.getString("app.mustUpdate");
	//APP 更新内容
	public static String APP_UPDATE_MSG = res.getString("app.updateMsg");
	//APP 更新地址
	public static String APP_DOWNLOAD_URL = res.getString("app.downloadUrl");
	
	//iosAPP 版本号
	public static String IOS_VERSION_CODE = res.getString("ios.app.versionCode");
	//iosAPP 更新标致
	public static String IOS_MUST_UPDATE = res.getString("ios.app.mustUpdate");
	//iosAPP 更新内容
	public static String IOS_UPDATE_MSG = res.getString("ios.app.updateMsg");
	//iosAPP 更新地址
	public static String IOS_DOWNLOAD_URL = res.getString("ios.app.downloadUrl");	
}
