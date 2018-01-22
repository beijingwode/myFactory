package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.tongji.service.SmsTemplateService;
import com.wode.tongji.service.WeixinMessageService;

import redis.clients.jedis.JedisPubSub;

@Service
public class WxJedisListenter extends JedisPubSub {
	
	@Autowired
	private WeixinMessageService weixinMessageService;
	
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		// 订阅时触发
		System.out.println(String.format("WxJedisListenter.onSubscribe(channel:%s,subscribedChannels:%d)", channel,subscribedChannels));
	}
	
	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		System.out.println(String.format("WxJedisListenter.onUnsubscribe(channel:%s,subscribedChannels:%d)", channel,subscribedChannels));
	}
	
	@Override
	public void onMessage(String channel, String message) {
		if(RedisConstant.SUBSCRIBE_CHANNEL_USER_BIND.equals(channel)) {
			//绑定
			if(!StringUtils.isEmpty(message)) {
				weixinMessageService.pushMsg(message.trim());
			}
		}
	}
	
	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		// 模式类订阅时触发（2.8 集群环境无效） 用于hello_* 匹配hello_gao,hello_world
		System.out.println(String.format("WxJedisListenter.onPSubscribe(pattern:%s,subscribedChannels:%d)", pattern,subscribedChannels));
	}
	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		System.out.println(String.format("WxJedisListenter.onPUnsubscribe(pattern:%s,subscribedChannels:%d)", pattern,subscribedChannels));
	}
	@Override
	public void onPMessage(String pattern, String channel, String message) {
		System.out.println(String.format("WxJedisListenter.onPMessage(pattern:%s,channel:%s,message:%s)", pattern,channel,message));
	}
}
