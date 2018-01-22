package com.wode.factory.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.constant.Constant;
import com.wode.common.db.DBUtils;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.controller.HtmlAction;
import com.wode.factory.controller.ProductController;
import com.wode.factory.model.Product;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.SmsService;
import com.wode.factory.service.ClientAccessLogService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.SubOrderService;
import com.wode.factory.service.SuborderitemLimitTicketService;
import com.wode.tongji.model.SmsTemplate;
import com.wode.tongji.service.SmsTemplateService;

import redis.clients.jedis.JedisPubSub;

@Service
public class OrderJedisListenter extends JedisPubSub {

	private Logger logger = LoggerFactory.getLogger(OrderJedisListenter.class);
	@Autowired
	DBUtils dbUtils;
	@Autowired
	private SubOrderService subOrderService;
	@Autowired
	private SuborderitemLimitTicketService suborderitemLimitTicketService;
	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ClientAccessLogService clientAccessLogService;
	
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		// 订阅时触发
		System.out.println(String.format("OrderJedisListenter.onSubscribe(channel:%s,subscribedChannels:%d)", channel,subscribedChannels));
	}
	
	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		System.out.println(String.format("OrderJedisListenter.onUnsubscribe(channel:%s,subscribedChannels:%d)", channel,subscribedChannels));
	}

	@Override
	public void onMessage(String channel, String message) {
		if(RedisConstant.SUBSCRIBE_CHANNEL_ORDER_PAY.equals(channel)) {
			// 订单支付成功 
			if(!StringUtils.isEmpty(message)) {
				String outId = dbUtils.CreateID()+"";
				SmsService sms= ServiceFactory.getSmsService(Constant.OUTSIDE_SERVICE_URL);
				SmsTemplate t =  smsTemplateService.getById(2L);
				String[] subOrderIds=message.split(",");
				for (String subOrderId : subOrderIds) {
					subOrderService.pushOrderPayAndECard(subOrderId.trim(), t, sms, outId);
				}
			}
		} else if(RedisConstant.SUBSCRIBE_CHANNEL_ORDER_CREATE.equals(channel)) {
			// 创建订单，limit4_orderId
			if(!StringUtils.isEmpty(message)) {
				Map<String,Object> map = new HashMap<String,Object>();
				// 专享券抵扣商品
				String[] proIds=message.split(",");
				logger.debug(message);
				if("limit4".equals(proIds[0])) {
					for(int i=1;i<proIds.length;i++) {
						Long productId = NumberUtil.toLong(proIds[i]);
						Integer skuCnt = suborderitemLimitTicketService.selectProCnt(productId);
						logger.debug(skuCnt+"");
						if(skuCnt!=null) {
							// 根据商品id 以及累计销售数量，判断专享商品库存，已超过，则商品销售变成普通
							Product product = productService.getById(productId);
							logger.debug(product.getEmpLevel()+"");
							if(skuCnt >= product.getEmpLevel() && product.getSaleKbn()!=null && product.getSaleKbn()==4) {//更新商品为普通商品
								map.put("productId", productId);
								productService.updateProductSaleKbnByMap(map);
								logger.debug("end-updateProductSaleKbn");
								Map<Long,Long> pPvs=  clientAccessLogService.getDetailPvCnt(null, productId);
								productService.cache(productId, pPvs);
								logger.debug("product rebuild cache success!"+productId);
							}
						}
					}
				}
			}
		} else if(RedisConstant.SUBSCRIBE_CHANNEL_ORDER_URGED.equals(channel)) {
			// 催单
			if(!StringUtils.isEmpty(message)) {
				subOrderService.urgedDelivery(message.trim());
			}
		} else {
			System.out.println(String.format("OrderJedisListenter.onMessage(channel:%s,message:%s)", channel,message));
		}
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		// 模式类订阅时触发（2.8 集群环境无效） 用于hello_* 匹配hello_gao,hello_world
		System.out.println(String.format("OrderJedisListenter.onPSubscribe(pattern:%s,subscribedChannels:%d)", pattern,subscribedChannels));
	}
	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		System.out.println(String.format("OrderJedisListenter.onPUnsubscribe(pattern:%s,subscribedChannels:%d)", pattern,subscribedChannels));
	}
	@Override
	public void onPMessage(String pattern, String channel, String message) {
		System.out.println(String.format("OrderJedisListenter.onPMessage(pattern:%s,channel:%s,message:%s)", pattern,channel,message));
	}

}
