package com.wode.factory.user.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.service.UserService;

public class SessonRedisUtil {
	//public static final String SESSION_LOGIN_USER = "FACTORY_LOGIN_USER_";
	
	public static final String SESSION_LOGIN = "FACTORY_LOGIN_";
	//验证码key
	public static final String SESSION_CODETEXT = "FACTORY_codeText_";
	//登录错误次数key
	public static final String SESSION_LOGINERRORCOUNT = "FACTORY_loginErrorCount_";
	//session生命周期
	public static final int SESSION_LIFE_TIME = 1200 ;

	/**
	 * @Title: setSessionOnly
	 * @Description: 仅向redis插入会话信息
	 * @param @param uuid
	 * @param @param login
	 * @return void
	 * @throws
	 */
	public static UserFactory getLoginUser(String uuid, RedisUtil redisUtil,UserService userService) {
		String userId = redisUtil.getData(SESSION_LOGIN + uuid);
		if(!StringUtils.isEmpty(userId)){
			return userService.getById(NumberUtil.toLong(userId));
		}
		return null;
	}

	/**
	 * @Title: setSessionOnly
	 * @Description: 仅向redis插入会话信息
	 * @param @param uuid
	 * @param @param login
	 * @return void
	 * @throws
	 */
	public static void setLoginId(String uuid, UserFactory login,RedisUtil redisUtil) {
		redisUtil.setData(SESSION_LOGIN + uuid, login.getId().toString(),SESSION_LIFE_TIME);
	}

	/**
	 * @Title: clearSession
	 * @Description: 清除redis中会话信息
	 * @param @param uuid
	 * @return void
	 * @throws
	 */
	public static void clearSession(String uuid,RedisUtil redisUtil) {
		//redisUtil.del(SESSION_LOGIN_USER + uuid);
		redisUtil.del(SESSION_LOGIN + uuid);
		redisUtil.del(SESSION_CODETEXT + uuid);
		redisUtil.del(SESSION_LOGINERRORCOUNT + uuid);
	}

	public static String getLoginId(HttpServletRequest request,
			HttpServletResponse response,RedisUtil redisUtil) {
		String uuid = CookieUtils.getUUID(request, response);
		return redisUtil.getData(SESSION_LOGIN + uuid);
	}

	public static void setCodeText(String uuid, String value ,RedisUtil redisUtil) {
		redisUtil.setData(SESSION_CODETEXT + uuid, value,
				SESSION_LIFE_TIME);
	}

	public static void setErrorCount(String uuid, String value ,RedisUtil redisUtil) {
		redisUtil.setData(SESSION_LOGINERRORCOUNT + uuid, value,
				SESSION_LIFE_TIME);
	}

	public static String getCodeText(String uuid,RedisUtil redisUtil) {
		return redisUtil.getData(SESSION_CODETEXT + uuid);
	}

	public static String getErrorCount(String uuid,RedisUtil redisUtil) {
		return redisUtil.getData(SESSION_LOGINERRORCOUNT + uuid);
	}

	public static void setVCode(String reg_flg, String phone_no, String code,RedisUtil redisUtil) {
		redisUtil.setData("EXPRESS_VCODE_" +reg_flg+phone_no, code,SESSION_LIFE_TIME);
	}

	public static String getVCode(String reg_flg, String phone_no,RedisUtil redisUtil) {
		return redisUtil.getData("EXPRESS_VCODE_" +reg_flg+phone_no);
	}

	public static void setData(String key, String value,RedisUtil redisUtil,int seconds) {
		redisUtil.setData(key, value, seconds );
	}

	public static void setData(String key, String value,RedisUtil redisUtil) {
		redisUtil.setData(key, value);
	}
	public static String getData(String key,RedisUtil redisUtil) {
		return redisUtil.getData(key);
	}
	public static void delData(String key,RedisUtil redisUtil) {
		redisUtil.del(key);
	}


}
