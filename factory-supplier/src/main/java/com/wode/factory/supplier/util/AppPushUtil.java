package com.wode.factory.supplier.util;

import java.util.HashMap;
import java.util.Map;

import com.wode.common.util.HttpClientUtil;

public class AppPushUtil {
	/** 
     * 模板类型  新订单通知
     */  
    public static final String TEMPLATE_TYPE_NEW_ORDERS = "new_order";  
	/** 
     * 模板类型  返现通知
     */  
    public static final String TEMPLATE_TYPE_COMMENT_PRIZE = "comment_prize";  
	/** 
     * 模板类型  商品已发出通知
     */  
    public static final String TEMPLATE_TYPE_ORDER_SEND = "order_send";  
	/** 
     * 模板类型  到账提醒
     */  
    public static final String TEMPLATE_TYPE_BALANCE = "balace";  
	/** 
     * 模板类型  返利提醒
     */  
    public static final String TEMPLATE_TYPE_TRIAL_COMMENT = "trial_comment";  
    
    /**
     * 推送团购 已发货消息
     * @param groupBuyId
     * @param orderStatus
     */
	public static void pushGroupBuyMsg(Long groupBuyId,Integer orderStatus) {

		Map<String,Object> paramPush=new HashMap<String,Object>();
		paramPush.put("groupBuyId", groupBuyId);
		paramPush.put("orderStatus", orderStatus);
		HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_API_URL+"user/pushGroupBuyMsg", paramPush);
	}

	public static void pushMsg(Long uid,String title,String msg) {

		Map<String,Object> paramPush=new HashMap<String,Object>();
		paramPush.put("title", title);
		paramPush.put("msg", msg);
		//app 推送
		paramPush.put("userId", uid);
		HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_API_URL+"user/pushMsg", paramPush);
	}
	
	/**
	 * 新订单微信通知
	 * @param uid			用户id
	 * @param shopName		店铺名称
	 * @param productName	商品名称
	 * @param createDate	创建时间
	 * @param amount		金额
	 * @param subOrderId	订单id
	 */
	public static void pushWxNewOrder(Long uid,String shopName,String productName,String createDate,String amount,String subOrderId) {

		Map<String,Object> paramPush=new HashMap<String,Object>();
		paramPush.put("shopName", shopName);
		paramPush.put("productName", productName);
		paramPush.put("createDate", createDate);
		paramPush.put("amount", amount);
		paramPush.put("subOrderId", subOrderId);

		pushWxTempletMsg(AppPushUtil.TEMPLATE_TYPE_NEW_ORDERS,uid,paramPush);
	}
	
	/**
	 * 发放订单发货通知
	 * @param uid			用户id
	 * @param subOrderId	订单id
	 * @param productName	商品信息
	 * @param num			数量
	 * @param expressName	快递公司
	 * @param expressNo		快递单号
	 */
	public static void pushWxOrderSend(Long uid,String subOrderId,String productName,String num,String expressName,String expressNo) {

		Map<String,Object> paramPush=new HashMap<String,Object>();
		paramPush.put("subOrderId", subOrderId);
		paramPush.put("productName", productName);
		paramPush.put("num", num);
		paramPush.put("expressName", expressName);
		paramPush.put("expressNo", expressNo);

		pushWxTempletMsg(AppPushUtil.TEMPLATE_TYPE_ORDER_SEND,uid,paramPush);
	}

	/**
	 * 发放福利通知
	 * @param uid		用户id
	 * @param amount	金额
	 * @param date		日期
	 * @param note		备注
	 * @param cId		货币类型
	 */
	public static void pushWxBalace(Long uid,String amount,String date,String note,String cId) {
		
		Map<String,Object> paramPush=new HashMap<String,Object>();
		paramPush.put("amount", amount);
		paramPush.put("date", date);
		paramPush.put("note", note);
		paramPush.put("cId", cId);

		pushWxTempletMsg(AppPushUtil.TEMPLATE_TYPE_BALANCE,uid,paramPush);
	}

	/**
	 * 发放福利通知
	 * @param uid		用户id
	 * @param amount	金额
	 * @param date		日期
	 * @param note		备注
	 * @param cId		货币类型
	 */
	public static void pushWxCommentPrize(Long uid,String amount,String subOrderId) {
		
		Map<String,Object> paramPush=new HashMap<String,Object>();
		paramPush.put("amount", amount);
		paramPush.put("subOrderId", subOrderId);

		pushWxTempletMsg(AppPushUtil.TEMPLATE_TYPE_COMMENT_PRIZE,uid,paramPush);
	}
	
	private static void pushWxTempletMsg(String type,Long userId,Map<String,Object> paramPush) {

		paramPush.put("type", type);
		paramPush.put("userId", userId);
		HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_API_URL+"wx/templateMsgSend", paramPush);
	}
}
