package com.wode.user.task;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wode.common.util.HttpClientUtil;
import com.wode.factory.mapper.GroupBuyDao;
import com.wode.factory.mapper.GroupOrdersDao;
import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsService;

@Service
public class GroupOrderTask {

	@Autowired
	private GroupBuyDao groupBuyDao;
	@Autowired
	private GroupOrdersDao groupOrdersDao;
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	@Autowired
	private ProductService productService;
	
	@Value("#{configProperties['factory.api.url']}")
	private  String apiUrl;

	public void run() {
		/**
		 * 统计今天之前团订单结束的团，将团进行合单处理 平台 只做 定时处理 ，实际开团再api 项目中完成
		 * 
		 */
		Map<String, Object> paramPush = new HashMap<String, Object>();
		Map<Long, Integer> skuMap = new HashMap<Long, Integer>();
		List<GroupBuy> groupBuyList = groupBuyDao.findEndGroupBuy();

		String api = apiUrl + "groupOrder/tjOpenGroup.tj";
		String apigiveUp = apiUrl + "groupOrder/giveUpOrderTj.tj";

		if (null != groupBuyList && groupBuyList.size() > 0) {
			for (GroupBuy groupBuy : groupBuyList) {
				// 到时自动开团
				boolean falg = false;
				List<GroupSuborderitem> groupSuborderitemList = groupOrdersDao.findByOkOrders(groupBuy.getId());
				if (null != groupSuborderitemList && groupSuborderitemList.size() > 0) {
					for (GroupSuborderitem groupSuborderitem : groupSuborderitemList) {
						if (skuMap.containsKey(groupSuborderitem.getSkuId())) {
							skuMap.put(groupSuborderitem.getSkuId(),
									skuMap.get(groupSuborderitem.getSkuId()) + groupSuborderitem.getNumber());
						} else {
							skuMap.put(groupSuborderitem.getSkuId(), groupSuborderitem.getNumber());
						}
					}
					for (Long skuId : skuMap.keySet()) {
						ProductSpecifications sku = productSpecificationsService.getById(skuId);
						if (skuMap.get(skuId) < sku.getMinLimitNum()) {
							falg = true;
							break;
						}
					}
				}
				if (falg) {
					try {
						paramPush.put("groupId", groupBuy.getId());
						paramPush.put("closeReason", "商品数量起售数量未达到");
						HttpClientUtil.sendHttpRequest("post", apigiveUp, paramPush);
					} catch (Exception ex) {
					}
				} else {
					paramPush.put("groupId", groupBuy.getId());
					try {
						HttpClientUtil.sendHttpRequest("post", api, paramPush);
					} catch (Exception ex) {
					}
				}
			}
		}
	}
	
	public void refundShipp() {
		/**
		 * 统计今天之前团订单结束的团，将团进行合单处理  平台 只做 定时处理 ，实际开团再api 项目中完成
		 * 
		 */
		Map<String,Object> paramPush=new HashMap<String,Object>();
		List<GroupBuy> groupBuyList = groupBuyDao.findNeedRefundShipp();
		String api=apiUrl+"groupOrder/tjRefundShipp.tj";
		if (null != groupBuyList && groupBuyList.size() > 0) {
			for (GroupBuy groupBuy : groupBuyList) {
				// 到时自动开团
				paramPush.put("groupId", groupBuy.getId());
				try{
					HttpClientUtil.sendHttpRequest("post",api, paramPush);
				} catch(Exception ex) {
				}
			}
		}
	}

	public void autoCancelOrder() {
		/**
		 * 未支付订单自动取消(还原库存)
		 * 
		 */
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,-30);//把日期往后增加一天.整数往后推,负数往前移动
        //date=calendar.getTime();   //这个时间就是日期往后推一天的结果
        
		Map<String,Object> paramPush=new HashMap<String,Object>();
		String api=apiUrl+"groupOrder/autoCancelOrder.tj";
		List<GroupSuborder> gss= groupOrdersDao.findForCancel(calendar.getTime());
		if (null != gss && gss.size() > 0) {
			for (GroupSuborder gs : gss) {
				// 到时自动开团
				paramPush.put("userId",gs.getReturnOrderId());
				paramPush.put("subOrderId", gs.getSubOrderId());
				paramPush.put("closeReason", "因超时未付款，交易自动关闭");
				try{
					HttpClientUtil.sendHttpRequest("post",api, paramPush);
				} catch(Exception ex) {
				}
			}
		}
	}
}
