package com.wode.factory.user.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Payment;
import com.wode.factory.outside.service.AlipayService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxPayService;
import com.wode.factory.user.facade.OrdersFacade;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.PaymentService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.MapEntryConverter;

/**
 * 支付
 * 
 * @author zhengxiongwu
 *
 */
@Controller
@RequestMapping("/paymentCallBack")
public class PaymentCallBackController{

	@Autowired
	private OrdersFacade ordersFacade;
	
	@Qualifier("ordersService")
	@Autowired
	private OrdersService ordersService;
	
	@Qualifier("suborderService")
	@Autowired
	private SuborderService suborderService;
	
	@Qualifier("paymentService")
	@Autowired
	private PaymentService paymentService;

	static WxPayService wxPay = ServiceFactory.getWxPayService(Constant.OUTSIDE_SERVICE_URL);
	static AlipayService alipay = ServiceFactory.getAlipayService(Constant.OUTSIDE_SERVICE_URL);
	
	private static Logger logger= LoggerFactory.getLogger(PaymentCallBackController.class);

	@RequestMapping("/wePayResult")
	public void wePayResult(HttpServletRequest request, HttpServletResponse response) {
		String xmlResponse;
		try {
			BufferedReader bufferedReader = request.getReader();
			StringBuilder sb = new StringBuilder();
			while((xmlResponse = bufferedReader.readLine()) != null) {
				sb.append(xmlResponse);
			}
			xmlResponse =  sb.toString();
			ActResult<String> act = wxPay.handlePayResult(xmlResponse);
			Map<String, String> map = new HashMap<String, String>();
			if (act.isSuccess()) {
				map.put("return_code", WxPayService.ReturnCode.SUCCESS);
				
				Payment payment = paymentService.getById(Long.parseLong(act.getData()));
				payment.setTradeNo(act.getMsg());
				payment.setStatus(2);//支付成功已回调
				if(com.wode.common.util.StringUtils.isEmpty(payment.getWay())) {
					payment.setWay("wxpay");
				}
				ordersFacade.updateOrderToPay(payment);
			} else {
				map.put("return_code", WxPayService.ReturnCode.FAIL);
			}
			XStream xs = new XStream(new DomDriver("UTF-8",new XmlFriendlyNameCoder("-_", "_")));
			xs.registerConverter(new MapEntryConverter());
			xs.alias("xml", Map.class);
			response.getWriter().write(xs.toXML(map));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 微信退款回调
	 */
	@RequestMapping("/weRefundResult")
	public void weRefundResult(HttpServletRequest request, HttpServletResponse response) {
		String xmlResponse;
		try {
			BufferedReader bufferedReader = request.getReader();
			StringBuilder sb = new StringBuilder();
			while((xmlResponse = bufferedReader.readLine()) != null) {
				sb.append(xmlResponse);
			}
			xmlResponse =  sb.toString();
			ActResult<String> act = wxPay.handleRefundResult(xmlResponse);
			Map<String, String> map = new HashMap<String, String>();
			if (act.isSuccess()) {
				map.put("return_code", WxPayService.ReturnCode.SUCCESS);
				map.put("return_msg", "OK");
				Payment payment = paymentService.getById(Long.parseLong(act.getData()));
				if(payment.getStatus()!=2){
					String msg = act.getMsg();
					String[] split = msg.split("_");
					String tradeNo = split[0];
					payment.setTradeNo(tradeNo);
					String status = split[1];
					if("SUCCESS".equals(status)){
						payment.setStatus(2);//支付成功已回调
					}else if("CHANGE".equals(status)){
						payment.setStatus(-1);//-1 退款失败
					}
					if(com.wode.common.util.StringUtils.isEmpty(payment.getWay())) {
						payment.setWay("wxpay");
					}
					paymentService.saveOrUpdate(payment);
				}
				//ordersFacade.updateOrderToPay(payment);
			} else {
				map.put("return_code", WxPayService.ReturnCode.FAIL);
			}
			XStream xs = new XStream(new DomDriver("UTF-8",new XmlFriendlyNameCoder("-_", "_")));
			xs.registerConverter(new MapEntryConverter());
			xs.alias("xml", Map.class);
			response.getWriter().write(xs.toXML(map));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//异步通知(新版app/web 支付回调)
	@RequestMapping(value = { "/alipayNotify" })
	public void alipayNotify(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		    String name = (String) iter.next();
		    String[] values = (String[]) requestParams.get(name);
		    String valueStr = "";
		    for (int i = 0; i < values.length; i++) {
		        valueStr = (i == values.length - 1) ? valueStr + values[i]
		                    : valueStr + values[i] + ",";
		  }
		    //乱码解决，这段代码在出现乱码时使用。
		    //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
		    params.put(name, valueStr);
		 }
		
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean flag = alipay.rsaCheckV1(params);
				
		if(flag) {
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号
			String notify_type = request.getParameter("notify_type");
			if("batch_refund_notify".equals(notify_type)) {
				//TODO 退款通知
				
				// do something
				return;
			} else if("trade_status_sync".equals(notify_type)){
				
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

				//支付宝交易号
				String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

				//交易状态
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

				//交易状态
				String price = request.getParameter("total_amount");

				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					//计算得出通知验证结果
					Payment payment = paymentService.getById(Long.parseLong(out_trade_no));
					if(payment.getTotalFee().compareTo(new BigDecimal(price))>=0) {					
						if(payment.getStatus() != null && payment.getStatus()!=2) {
							payment.setTradeNo(trade_no);
							payment.setStatus(2);//支付成功已回调
							if(StringUtils.isEmpty(payment.getWay())) {
								payment.setWay("zhifubao");
							}
							ordersFacade.updateOrderToPay(payment);
						}
					}
				} else if("TRADE_CLOSED".equals(trade_status)) {
					String out_biz_no = new String(request.getParameter("out_biz_no").getBytes("ISO-8859-1"),"UTF-8");
					String refund_fee = request.getParameter("refund_fee");
					
					Payment payment = paymentService.getById(Long.parseLong(out_biz_no));
					if(payment!=null && payment.getTotalFee().compareTo(new BigDecimal(refund_fee))>=0) {					
						if(payment.getStatus() != null && payment.getStatus()!=2) {
							payment.setTradeNo(trade_no);
							payment.setStatus(2);//支付成功已回调
							if(StringUtils.isEmpty(payment.getWay())) {
								payment.setWay("zhifubao");
							}
							payment.setStatus(2);
							payment.setUpdateTime(new Date());
							paymentService.update(payment);
						}
					}
				}
			}

			logger.info("验证成功");
			//response.getOutputStream().flush();
			response.getOutputStream().println("success");
		}else{
			logger.info("验证失败");
			response.getOutputStream().println("fail");
		}
	}
	

	@RequestMapping(value = { "/alipayResult" })
	public String alipayResult(HttpServletRequest request) throws UnsupportedEncodingException{
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		logger.info("alipay callback:"+JsonUtil.toJsonString(params));
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean flag = alipay.rsaCheckV1(params);
		
		if(flag) {
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号

			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号

			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			
			//计算得出通知验证结果
			Payment payment = paymentService.getById(Long.parseLong(out_trade_no));
			
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				payment.setTradeNo(trade_no);
				if(StringUtils.isEmpty(payment.getWay())) {
					payment.setWay("zhifubao");
				}
				ordersFacade.updateOrderToPay(payment);
			}
			logger.info("验证成功");
			if(payment.getOrderId() != null){
				return "redirect:/member/paySuccess?orderId=" + payment.getOrderId()+"&payType="+payment.getPayType();
			}else{
				return "redirect:/member/paySuccess?subOrderId=" + payment.getSubOrderId()+"&payType="+payment.getPayType();
			}
		} else{
			logger.info("验证失败");
			return "redirect:/index.html";
		}
	}
	

	//异步通知
	@RequestMapping(value = { "/alipayNotify1" })
	public void alipayNotifyResult(HttpServletRequest request,HttpServletResponse response) {
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		try {
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		logger.info("alipay callback:"+JsonUtil.toJsonString(params));
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String notify_type = request.getParameter("notify_type");
		if("batch_refund_notify".equals(notify_type)) {
			//TODO 退款通知
			
			// do something
			return;
		}
				
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String price = request.getParameter("price");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		//计算得出通知验证结果
		Payment payment = paymentService.getById(Long.parseLong(out_trade_no));
		boolean verify_result = alipay.verify(params);
		if(verify_result && payment.getTotalFee().compareTo(new BigDecimal(price))>=0){//验证成功
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				if(payment.getStatus() != null && payment.getStatus()!=2) {
					payment.setTradeNo(trade_no);
					payment.setStatus(2);//支付成功已回调
					if(StringUtils.isEmpty(payment.getWay())) {
						payment.setWay("zhifubao");
					}
					ordersFacade.updateOrderToPay(payment);
				}
			}
			logger.info("验证成功");
			response.getOutputStream().println("success");
		}else{
			logger.info("验证失败");
			response.getOutputStream().println("fail");
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = { "/alipayResult1" })
	public String alipayResult1(HttpServletRequest request) throws UnsupportedEncodingException{
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		logger.info("alipay callback:"+JsonUtil.toJsonString(params));
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		//计算得出通知验证结果
		Payment payment = paymentService.getById(Long.parseLong(out_trade_no));
		boolean verify_result = alipay.verify(params);
		if(verify_result){//验证成功
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				payment.setTradeNo(trade_no);
				if(StringUtils.isEmpty(payment.getWay())) {
					payment.setWay("zhifubao");
				}
				ordersFacade.updateOrderToPay(payment);
			}
			logger.info("验证成功");
			if(payment.getOrderId() != null){
				return "redirect:/member/paySuccess?orderId=" + payment.getOrderId()+"&payType="+payment.getPayType();
			}else{
				return "redirect:/member/paySuccess?subOrderId=" + payment.getSubOrderId()+"&payType="+payment.getPayType();
			}
		}else{
			logger.info("验证失败");
			return "redirect:/index.html";
		}
		
		
	}

}
