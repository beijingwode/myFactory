package com.wode.factory.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPubSub;

import com.wode.common.redis.RedisUtil;
import com.wode.factory.service.ClientAccessLogService;
import com.wode.factory.service.ProductService;

@Service
public class ProductJedisListenter extends JedisPubSub {
	@Autowired
	private ProductService ps;

	@Resource
	private RedisUtil redis;
	@Autowired
	private ClientAccessLogService clientAccessLogService;


	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


	public void onSubscribe(String channel, int subscribedChannels) {
		logger.info(" onSubscribe:" + channel);
		onMessage("update_product", "");
	}


	// 取得订阅的消息后的处理
	public void onMessage(String channel, String message) {

		logger.info(channel + " in  message: " + message);

		String productid = redis.lpop("update_product");
		while (productid != null) {
			logger.info("reCache:" + productid);
			Long pId=Long.valueOf(productid);
			ps.cache(pId,clientAccessLogService.getDetailPvCnt(null, pId));
			ps.createProductHtml(pId);
			productid = redis.lpop("update_product");
		}


	}


	public void onPMessage(String pattern, String channel, String message) {

		logger.info(channel + "=" + message);

		String productid = redis.lpop("update_product");

		Long pId=Long.valueOf(productid);
		ps.cache(pId,clientAccessLogService.getDetailPvCnt(null, pId));
		ps.createProductHtml(pId);
	}


}
