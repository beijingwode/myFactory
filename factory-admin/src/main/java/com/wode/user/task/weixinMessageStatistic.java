package com.wode.user.task;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.WeixinMessage;
import com.wode.tongji.mapper.WeixinMessageMapper;
@Service
public class weixinMessageStatistic {
	
	@Autowired
	WeixinMessageMapper dao;
	@Autowired
	private RedisUtil redisUtil;
	
	public void run() {
		Calendar date = Calendar.getInstance();
		List<WeixinMessage> list = dao.findAll();
		if(list!=null) {
			for (WeixinMessage weixinMessage : list) {
				if("2".equals(weixinMessage.getMsgType())) {//临时消息
					if(weixinMessage.getLimitEnd().after(date.getTime()) && weixinMessage.getLimitStart().before(date.getTime())) {
						setWxMessageRedis(weixinMessage);
					}
				}else {
					setWxMessageRedis(weixinMessage);
				}
			}
		}
	}
	
	private void setWxMessageRedis(WeixinMessage weixinMessage) {
		redisUtil.delMapData(RedisConstant.WX_OPEN_MESSAGE_MAP, (weixinMessage.getEventType()+weixinMessage.getMsgType()));
		redisUtil.setMapData(RedisConstant.WX_OPEN_MESSAGE_MAP, (weixinMessage.getEventType()+weixinMessage.getMsgType()),JsonUtil.toJsonString(weixinMessage));
		redisUtil.setTime(RedisConstant.WX_OPEN_MESSAGE_MAP,3600*24+10);
	}
}
