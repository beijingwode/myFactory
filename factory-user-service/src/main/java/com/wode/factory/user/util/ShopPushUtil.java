package com.wode.factory.user.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.HttpClientUtil;
import com.wode.factory.constant.RedisConstant;

public class ShopPushUtil {
	public static final String PUSH_TYPE_ORDER_PAY = "order_pay";
	public static final String PUSH_TYPE_ORDER_URGED = "urged_delivery";
	
	public static void pushMsg(Long uid,String title,String msg) {

		Map<String,Object> paramPush=new HashMap<String,Object>();
		paramPush.put("title", title);
		paramPush.put("msg", msg);
		//app 推送
		paramPush.put("userId", uid);
		HttpClientUtil.sendHttpRequest("post", com.wode.factory.user.util.Constant.QIYE_API_URL+"/app/user/pushMsg", paramPush);
	}
	
//	private static void pushMsg4order(String subOrderId,String title,String msg) {
//
//		Map<String,Object> paramPush=new HashMap<String,Object>();
//		paramPush.put("title", title);
//		paramPush.put("msg", msg);
//		//app 推送
//		paramPush.put("subOrderId", subOrderId);
//		HttpClientUtil.sendHttpRequest("post", com.wode.factory.user.util.Constant.QIYE_API_URL+"/app/user/pushMsg4order", paramPush);
//	}

	public static void pushMsg4order(RedisUtil redis,List<String> subOrderIds,String pushType) {

		if(PUSH_TYPE_ORDER_URGED.equals(pushType)) {
			redis.push(RedisConstant.SUBSCRIBE_CHANNEL_ORDER_URGED, subOrderIds.get(0));
		} else if(PUSH_TYPE_ORDER_PAY.equals(pushType)) {
			StringBuffer sb = new StringBuffer();
			for (String string : subOrderIds) {
				sb.append(string).append(",");
			}
			if(sb.length()>1) {
				redis.push(RedisConstant.SUBSCRIBE_CHANNEL_ORDER_PAY, sb.substring(0,sb.length()-1));
			}
		}
	}
}
