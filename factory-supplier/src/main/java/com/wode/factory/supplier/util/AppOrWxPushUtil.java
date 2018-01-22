package com.wode.factory.supplier.util;


import com.wode.common.redis.RedisUtil;
import com.wode.factory.constant.RedisConstant;

public class AppOrWxPushUtil {
	public static final String PUSH_TYPE_USER_BIND = "userBind";
	
	
	public static void pushMsgAll(RedisUtil redis,String uid,String pushType) {

		if(PUSH_TYPE_USER_BIND.equals(pushType)) {
			redis.push(RedisConstant.SUBSCRIBE_CHANNEL_USER_BIND, uid);
		} 
	}
}
